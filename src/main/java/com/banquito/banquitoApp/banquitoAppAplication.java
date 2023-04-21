package com.banquito.banquitoApp;

import com.banquito.banquitoApp.utils.db.testDBInit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.ws.rs.ApplicationPath;

@SpringBootApplication
@ApplicationPath("/api/v1")
public class banquitoAppAplication {
    public static void main(String[] args) throws ClassNotFoundException{
        testDBInit.init();
        SpringApplication.run(banquitoAppAplication.class, args);
    }
}
