package com.zayaanit.mm.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * @author Zubayer Ahamed
 * @since Nov 18, 2023
 */
@Configuration
@OpenAPIDefinition(
	info = @Info(
		title = "User API", 
		version = "1.0.0", 
		contact = @Contact(
			name = "Zubayer", 
			email = "zubayerahamed1990@gmail.com", 
			url = "https://www.zayaanit.com"), 
		license = @License(
			name = "Apache 2.0", 
			url = "https://www.apache.org/licenses/LICENSE-2.0"), 
		termsOfService = "", 
		description = ""
	), 
	servers = @Server(
		url = "http://localhost:2021", 
		description = "Local Env"
	),
	security = {
		@SecurityRequirement(
			name = "bearerAuth"
		)
	}
)
@SecurityScheme(
	name = "Bearer Authentication",
	description = "JWT auth description",
	scheme = "bearer",
	type = SecuritySchemeType.HTTP,
	bearerFormat = "JWT",
	in = SecuritySchemeIn.HEADER
)
public class OpenAPI30Configuration {

//	@Bean
//	public OpenAPI customizeOpenAPI() {
//		final String securitySchemeName = "bearerAuth";
//		return new OpenAPI().addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
//				.components(new Components().addSecuritySchemes(securitySchemeName, new SecurityScheme()
//						.name(securitySchemeName).type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
//	}

}
