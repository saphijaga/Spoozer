package de.saphijaga.spoozer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.swing.*;

/**
 * Created by samuel on 05.10.15.
 */
@SpringBootApplication
public class SpoozerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpoozerApplication.class, args);
    }
}
