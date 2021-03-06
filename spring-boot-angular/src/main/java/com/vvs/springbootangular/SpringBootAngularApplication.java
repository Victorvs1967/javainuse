package com.vvs.springbootangular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBootAngularApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAngularApplication.class, args);
	}

}
