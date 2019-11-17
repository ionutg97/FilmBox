package io.javabrains.moviesplitMovieservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MovieSplitMovieServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieSplitMovieServiceApplication.class, args);
	}

}
