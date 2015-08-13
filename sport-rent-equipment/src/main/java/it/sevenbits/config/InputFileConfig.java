package it.sevenbits.config;

import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * Created by awemath on 8/12/15.
 */
@Configuration
public class InputFileConfig {

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("15MB");
        factory.setMaxRequestSize("15MB");
        return factory.createMultipartConfig();
    }

}
