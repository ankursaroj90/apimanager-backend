package com.ankur.ApiManager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                		.allowedOrigins("http://localhost:3000")
                		.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                		.allowedHeaders("*")
                		.allowCredentials(true);
                
                registry.addMapping("/apis/**")
		        		.allowedOrigins("http://localhost:3000")
		        		.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
		        		.allowedHeaders("*")
		        		.allowCredentials(true);
                
                registry.addMapping("/environments/**")
		        		.allowedOrigins("http://localhost:3000")
		        		.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
		        		.allowedHeaders("*")
		        		.allowCredentials(true);
                
                registry.addMapping("/schemas/**")
	        		.allowedOrigins("http://localhost:3000")
	        		.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	        		.allowedHeaders("*")
	        		.allowCredentials(true);
                
                registry.addMapping("https://ankursaroj90.github.io/apimanager/**")
						.allowedOrigins("https://ankursaroj90.github.io")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedHeaders("*")
						.allowCredentials(true);
            }
        };
    }
}