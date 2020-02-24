package io.javabrains.moviecommentaryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MovieCommentaryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieCommentaryServiceApplication.class, args);
    }

}
