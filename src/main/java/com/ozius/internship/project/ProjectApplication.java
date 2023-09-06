package com.ozius.internship.project;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder()
				.sources(ProjectApplication.class)
				.profiles(SpringProfiles.PROD)
				.run(args);
	}

}

