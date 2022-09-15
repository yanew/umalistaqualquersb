package com.api.umalistaqualquersb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class UmalistaqualquersbApplication {

	@GetMapping("/")
	public String index() {
		return "Oi Yane";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(UmalistaqualquersbApplication.class, args);
	}

}
