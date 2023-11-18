package com.zayaanit.mm;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@SpringBootApplication
public class MmApplication {

	public static void main(String[] args) {
		SpringApplication.run(MmApplication.class, args);
	}

	@Bean
	OpenAPI customizeOpenAPI() {
		final String securitySchemeName = "bearerAuth";
		return new OpenAPI().addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
				.components(new Components().addSecuritySchemes(securitySchemeName, new SecurityScheme()
						.name(securitySchemeName).type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
	}

	@Bean
	GroupedOpenApi usersGroup(@Value("${springdoc.version}") String appVersion) {
		return GroupedOpenApi.builder().group("users").addOperationCustomizer((operation, handlerMethod) -> {
			operation.addSecurityItem(new SecurityRequirement().addList("basicScheme"));
			return operation;
		}).addOpenApiCustomizer(openApi -> openApi
				.info(new io.swagger.v3.oas.models.info.Info().title("Users API").version(appVersion)))
				.packagesToScan("com.zayaanit.mm.controller").build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
