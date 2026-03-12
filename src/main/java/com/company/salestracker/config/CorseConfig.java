package com.company.salestracker.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class CorseConfig {
	  @Bean
	    public CorsFilter corsFilter() {

	        CorsConfiguration config = new CorsConfiguration();

	        config.setAllowCredentials(true);

	        // Allow any localhost port
	        config.setAllowedOriginPatterns(List.of("http://localhost:*"));

	        config.setAllowedHeaders(List.of("*"));

	        config.setAllowedMethods(
	                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
	        );

	        UrlBasedCorsConfigurationSource source =
	                new UrlBasedCorsConfigurationSource();

	        source.registerCorsConfiguration("/**", config);

	        return new CorsFilter(source);
	    }

}
