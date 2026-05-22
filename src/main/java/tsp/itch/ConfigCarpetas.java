package tsp.itch;

import java.io.File;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.tomcat.servlet.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;

@Configuration
public class ConfigCarpetas {

    @Value("${app.upload.base}")
    private String dirBase;

    @Value("${app.upload.dir.usuario}")
    private String dirUsuario;

    @Value("${app.upload.dir.tutorado}")
    private String dirTutorado;
    
    @Value("${app.upload.dir.tutor}")
    private String dirTutor;

    @PostConstruct
    public void init() {
        crearCarpeta(dirBase);
        crearCarpeta(dirUsuario);
        crearCarpeta(dirTutorado);
        crearCarpeta(dirTutor);
    }

    private void crearCarpeta(String ruta) {
        File carpeta = new File(ruta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
            System.out.println("Carpeta creada: " + ruta);
        } else {
            System.out.println("Carpeta ya existe: " + ruta);
        }
    }

    @Bean
    public TomcatServletWebServerFactory tomcatFactory() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected void customizeConnector(Connector connector) {
                super.customizeConnector(connector);
                connector.setProperty("maxParameterCount", "1000");
                connector.setProperty("maxParts", "1000");
            }
        };
    }
}