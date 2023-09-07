package com.ozius.internship.project;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ProjectApplicationWebAppLocalMssql {

	public static void main(String[] args) {
		new SpringApplicationBuilder()
				.sources(ProjectApplicationWebAppLocalMssql.class)
				.profiles(
						SpringProfiles.DEV,
						SpringProfiles.LOCAL_MS_SQL
				)
				.run(args);
	}

}

