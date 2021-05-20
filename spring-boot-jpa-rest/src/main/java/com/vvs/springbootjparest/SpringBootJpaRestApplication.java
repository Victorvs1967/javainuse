package com.vvs.springbootjparest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBootJpaRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaRestApplication.class, args);
	}

}
