package com.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Configuring CORS (Cross-Origin Resource Sharing)
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // Allow all origins (can be restricted to specific domains)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(false);
    }

//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/**").permitAll() // 允许访问 /login
//                .anyRequest().authenticated();
//    }
    // Configure interceptors (Optional: for logging, security, etc.)
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // Example of adding an interceptor, customize as needed
//        // registry.addInterceptor(new YourCustomInterceptor())
//        //         .addPathPatterns("/api/**")
//        //         .excludePathPatterns("/api/login", "/api/register");
//    }
}
