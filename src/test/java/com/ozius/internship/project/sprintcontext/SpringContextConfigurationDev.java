package com.ozius.internship.project.sprintcontext;

import com.ozius.internship.project.SpringProfiles;
import com.ozius.internship.project.infra.images.service.LocalDiskImageHandlingService;
import com.ozius.internship.project.infra.images.service.ImageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(SpringProfiles.DEV)
public class SpringContextConfigurationDev {
    @Bean
    public ImageService imageService() {
        return new LocalDiskImageHandlingService();
    }
}
