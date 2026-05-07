package secureforge.config; // Ajustado a la nueva estructura

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import secureauth.config.ApplicationConfig; // Asegúrate que esta interfaz/clase exista en este paquete
import secureforge.service.EmailService; // Importación actualizada

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    private final Environment env;

    public AppConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public ApplicationConfig applicationConfig() {
        return new ApplicationConfig() {
            private final Map<String, String> properties = new HashMap<>();
            {
                properties.put("mail.smtp.host", env.getProperty("mail.smtp.host"));
                properties.put("mail.smtp.port", env.getProperty("mail.smtp.port"));
                properties.put("mail.smtp.auth", env.getProperty("mail.smtp.auth"));
                properties.put("mail.smtp.starttls.enable", env.getProperty("mail.smtp.starttls.enable"));
                properties.put("mail.smtp.ssl.trust", env.getProperty("mail.smtp.ssl.trust"));
                properties.put("mail.from.address", env.getProperty("mail.from.address"));
                properties.put("mail.from.password", env.getProperty("mail.from.password"));
                properties.put("mail.admin.address", env.getProperty("mail.admin.address"));
            }
            @Override
            public String get(String key) {
                return properties.get(key);
            }
        };
    }

    @Bean
    public EmailService emailService(ApplicationConfig applicationConfig) {
        return new EmailService(applicationConfig);
    }
}