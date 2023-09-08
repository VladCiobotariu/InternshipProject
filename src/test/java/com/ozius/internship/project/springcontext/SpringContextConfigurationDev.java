package com.ozius.internship.project.springcontext;

import com.nimbusds.jose.jwk.RSAKey;
import com.ozius.internship.project.SpringProfiles;
import com.ozius.internship.project.infra.images.service.LocalDiskImageHandlingService;
import com.ozius.internship.project.infra.images.service.ImageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration
@Profile(SpringProfiles.DEV)
public class SpringContextConfigurationDev {

    private static final String KEY_STORE = "keystore.p12";
    private static final String KEY_STORE_TYPE = "PKCS12";
    private static final String KEY_STORE_PASSWORD = "somepass";
    private static final String KEY_STORE_ALIAS = "somealias";

    @Bean
    public ImageService imageService() {
        return new LocalDiskImageHandlingService();
    }

    @Bean
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
