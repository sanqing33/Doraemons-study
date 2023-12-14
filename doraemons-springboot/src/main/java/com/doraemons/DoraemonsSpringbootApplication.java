package com.doraemons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class DoraemonsSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoraemonsSpringbootApplication.class, args);
    }

    @GetMapping("/")
    public String Doraemons(){
        return "Hello Doraemon!";
    }

}
