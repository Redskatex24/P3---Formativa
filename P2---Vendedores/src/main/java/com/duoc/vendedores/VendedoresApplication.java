package com.duoc.vendedores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VendedoresApplication {
	public static void main(String[] args) {
		SpringApplication.run(VendedoresApplication.class, args);
	}
}
