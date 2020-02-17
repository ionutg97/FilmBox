package io.javabrains.dataBaseMovie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DataBaseMovieApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataBaseMovieApplication.class, args);
	}

}
