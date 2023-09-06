package com.ozius.internship.project;

import jakarta.persistence.EntityManager;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProjectApplicationWebAppEmbeddedDb {

	@Bean
	public TestDataSeed testDataSeedEmbedded() {
		return new TestDataSeed();
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder()
				.sources(ProjectApplicationWebAppEmbeddedDb.class)
				.profiles(
						SpringProfiles.DEV,
						SpringProfiles.EMBEDDED_DB_H2,
						SpringProfiles.TEST_DATA_SEED)
				.run(args);
	}

}

