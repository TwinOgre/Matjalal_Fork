package com.proj.Matjalal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MatjalalApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatjalalApplication.class, args);
	}

}
