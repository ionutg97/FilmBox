package io.javabrains.movieinfoservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    @Autowired
    private UserController userController;

    @Override
    public void run(String... args) throws Exception {

        userController.asyncMethodRestTemplate();

    }
}