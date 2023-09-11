package com.ozius.internship.project.springcontext;

import com.nimbusds.jose.jwk.RSAKey;
import com.ozius.internship.project.SpringProfiles;
import com.ozius.internship.project.infra.images.service.AwsS3ImageHandlingService;
import com.ozius.internship.project.infra.images.service.ImageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.security.*;

@Configuration
@Profile(SpringProfiles.PROD)
public class SpringContextConfigurationProd {

    @Bean
    public ImageService imageService() {
        return new AwsS3ImageHandlingService();
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
