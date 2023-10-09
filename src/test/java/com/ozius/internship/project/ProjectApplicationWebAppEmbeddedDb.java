package com.ozius.internship.project;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ProjectApplicationWebAppEmbeddedDb {

	public static void main(String[] args) {
		new SpringApplicationBuilder()
				.sources(ProjectApplicationWebAppEmbeddedDb.class)
				.profiles(
						SpringProfiles.DEV,
						SpringProfiles.EMBEDDED_DB_H2,
						SpringProfiles.TEST_DATA_SEED
				)
				.run(args);
	}

}

