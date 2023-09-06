package com.ozius.internship.project;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProjectApplicationWebAppLocalMssql {

	public static void main(String[] args) {
		new SpringApplicationBuilder()
				.sources(ProjectApplicationWebAppLocalMssql.class)
				.profiles(
						SpringProfiles.DEV,
						SpringProfiles.LOCAL_MSSQL,
						SpringProfiles.TEST_DATA_SEED)
				.run(args);
	}

}

