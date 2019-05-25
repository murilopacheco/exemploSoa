package com.ExemploSoa.exemploSoa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"model"})
public class ExemploSoaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExemploSoaApplication.class, args);
	}

}
