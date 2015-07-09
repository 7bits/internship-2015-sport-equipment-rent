package it.sevenbits;


/**
 * Created by awemath on 7/7/15.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String args[]){
        SpringApplication app= new SpringApplication(Application.class);
        app.setShowBanner(false);
        app.run(args);

    }

}
