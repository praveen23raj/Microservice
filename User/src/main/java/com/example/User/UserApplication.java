package com.example.User;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserApplication {

	public static void main(String[] args) {

		SpringApplication.run(UserApplication.class, args);

//		String username = System.getenv("DB_USERNAME");
//		String password = System.getenv("DB_PASSWORD");
//		String time = System.getenv("TIME");
//		String ISSUER = System.getenv("ISSUER");
//		String key = System.getenv("KEY");
//		System.out.println(username);
//		System.out.println(password);
//		System.out.println(time);
//		System.out.println(ISSUER);
//		System.out.println(key);
	}

}
