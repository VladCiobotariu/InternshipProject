package com.ozius.internship.project.springcontext;

import com.amazonaws.services.s3.AmazonS3;
import com.nimbusds.jose.jwk.RSAKey;
import com.ozius.internship.project.SpringProfiles;
import com.ozius.internship.project.infra.images.service.AwsS3ImageHandlingService;
import com.ozius.internship.project.infra.images.service.ImageService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration
@Profile(SpringProfiles.PROD)
public class SpringContextConfigurationProd {

    private final AmazonS3 amazonS3;

    public SpringContextConfigurationProd(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Value("${keystore.filename}")
    private String KEY_STORE;
    @Value("${keystore.type}")
    private String KEY_STORE_TYPE;
    @Value("${keystore.password}")
    private String KEY_STORE_PASSWORD;
    @Value("${keystore.alias}")
    private String KEY_STORE_ALIAS;
    @Value("${frontend.url}")
    private String FRONT_END_URL;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@Nullable CorsRegistry registry) {
                if (registry == null) throw new AssertionError();
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .allowedOrigins(FRONT_END_URL);
            }
        };
    }

    @Bean
    public ImageService imageService() {
        return new AwsS3ImageHandlingService(amazonS3);
    }

    @Bean //TODO implement better through vault
    public KeyStore keyStore() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE);
        keyStore.load(new ClassPathResource(KEY_STORE).getInputStream(), KEY_STORE_PASSWORD.toCharArray());

        return keyStore;
    }

    @Bean
    public RSAKey rsaKey(KeyStore keyStore) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {
        return new RSAKey.Builder((RSAPublicKey) keyStore.getCertificate(KEY_STORE_ALIAS).getPublicKey())
                .privateKey((PrivateKey) keyStore.getKey(KEY_STORE_ALIAS, KEY_STORE_PASSWORD.toCharArray()))
                .keyID(UUID.randomUUID().toString())
                .build();
    }
}
