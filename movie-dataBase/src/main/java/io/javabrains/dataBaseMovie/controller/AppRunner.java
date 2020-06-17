package io.javabrains.dataBaseMovie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    @Autowired
    private DataBaseController dataBaseController;

    @Override
    public void run(String... args) throws Exception {

        dataBaseController.asyncMethodRestTemplate();

    }
}