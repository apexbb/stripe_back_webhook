package com.strip_springboot.Springboot_stripe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringbootStripeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootStripeApplication.class, args);
	}
}
