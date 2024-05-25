package com.backend.floristeria.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
 
	@Override
	public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/**")
	        .allowedOrigins("http://localhost:4200") // Permite las solicitudes desde esta URL
	        .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos permitidos
	        .allowedHeaders("*"); // Cabeceras permitidas
	}
}
