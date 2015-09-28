package it.sevenbits;


/**
 * Created by awemath on 7/7/15.
 */

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer{
    static Logger LOG = Logger.getLogger(Application.class);
    public static void main(String args[]){
        SpringApplication app= new SpringApplication(Application.class);
        app.setShowBanner(false);
        app.run(args);

    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }

}
