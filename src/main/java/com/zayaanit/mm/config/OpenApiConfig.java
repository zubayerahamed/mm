package com.zayaanit.mm.config;

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
@OpenAPIDefinition(
	info = @Info(
		contact = @Contact(
			name = "Zubayer Ahamed", 
			email = "contact@zubayerahamed.com", 
			url = "https://zayaanit.com"
		), 
		description = "OpenApi documentation for Spring Security", 
		title = "OpenApi specification - Zubayer", 
		version = "1.0", 
		license = @License(
			name = "Licence name", 
			url = "https://some-url.com"
		), 
		termsOfService = "Terms of service"
	), 
	servers = {
		@Server(description = "Local ENV", url = "http://localhost:2021"),
		@Server(description = "PROD ENV", url = "https://aliboucoding.com/course") 
	}, 
	security = {
		@SecurityRequirement(name = "bearerAuth") 
	}
)
@SecurityScheme(
	name = "bearerAuth", 
	description = "JWT auth description", 
	scheme = "bearer", 
	type = SecuritySchemeType.HTTP, 
	bearerFormat = "JWT", 
	in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
