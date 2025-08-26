package fr.diginamic.tp10_1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

    @SpringBootApplication
    @ComponentScan(basePackages = {"fr.diginamic.tp10_1", "fr.diginamic.tp9"})
    public class Tp10_1Application {
        public static void main(String[] args) {
            SpringApplication app = new SpringApplication(Tp10_1Application.class);
            app.setWebApplicationType(WebApplicationType.NONE); // pas de Tomcat
            app.run(args);
        }
    }


