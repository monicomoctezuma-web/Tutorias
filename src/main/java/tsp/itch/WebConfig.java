package tsp.itch;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Fotos de usuarios
        registry.addResourceHandler("/fotos/usuarios/**")
                .addResourceLocations("file:C:/tutorias/usuario/");

        // Fotos de tutorados
        registry.addResourceHandler("/fotos/tutorados/**")
                .addResourceLocations("file:C:/tutorias/tutorado/");
        
     // Fotos de tutores
        registry.addResourceHandler("/fotos/tutores/**")
                .addResourceLocations("file:C:/tutorias/tutor/");
    }
}