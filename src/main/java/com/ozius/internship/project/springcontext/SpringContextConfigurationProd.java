package com.ozius.internship.project.springcontext;

import com.ozius.internship.project.SpringProfiles;
import com.ozius.internship.project.infra.images.service.AwsS3Service;
import com.ozius.internship.project.infra.images.service.ImageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(SpringProfiles.PROD)
public class SpringContextConfigurationProd {

    @Bean
    public ImageService imageService() {
        return new AwsS3Service();
    }
}
