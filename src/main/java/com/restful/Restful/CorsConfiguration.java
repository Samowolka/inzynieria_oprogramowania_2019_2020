package com.restful.Restful;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures CORS and in short: makes Javascript work with React.
 */
@Configuration
@EnableWebMvc
public class CorsConfiguration implements WebMvcConfigurer {
	
	/**
	 * Makes plain (vanilla) Javascript work with React in the same project.
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}
	
	/**
	 * CORS configuration. Thanks to that you can use methods such as GET, POST and DELETE.
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/*").allowedMethods("GET", "POST", "DELETE");
	}
}