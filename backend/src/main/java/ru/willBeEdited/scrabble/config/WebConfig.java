package ru.willBeEdited.scrabble.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.willBeEdited.scrabble.interceptor.MoveInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final MoveInterceptor moveInterceptor;

    public WebConfig(MoveInterceptor moveInterceptor) {
        this.moveInterceptor = moveInterceptor;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(moveInterceptor)
                .addPathPatterns("/api/*/game/move/**");
    }
}
