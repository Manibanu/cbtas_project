package org.example1.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // This applies CORS rules to EVERY controller in the project at once,
    // instead of adding @CrossOrigin to each controller one by one
    // (which is what only SubmitTestController currently has).
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:5173",   // Vite's default React dev server port
                        "http://localhost:3000"    // Create React App's default port, in case you use that instead
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}