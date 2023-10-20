package com.ozius.internship.project.springcontext;

import com.amazonaws.services.s3.AmazonS3;
import com.nimbusds.jose.jwk.RSAKey;
import com.ozius.internship.project.SpringProfiles;
import com.ozius.internship.project.infra.images.service.AwsS3ImageHandlingService;
import com.ozius.internship.project.infra.images.service.ImageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.security.KeyStore;
import java.security.KeyStoreException;

@Configuration
@Profile(SpringProfiles.PROD)
public class SpringContextConfigurationProd {

    private final AmazonS3 amazonS3;

    public SpringContextConfigurationProd(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Bean
    public ImageService imageService() {
        return new AwsS3ImageHandlingService(amazonS3);
    }

    @Bean //TODO implement valid keystore for prod profile
    public KeyStore keyStore() throws KeyStoreException {
        throw new KeyStoreException("can not continue, please implement valid keystore for prod env");
    }

    @Bean
    public RSAKey rsaKey(KeyStore keyStore) throws KeyStoreException {
        throw new KeyStoreException("can not continue, please implement valid keystore for prod env");
    }
}
