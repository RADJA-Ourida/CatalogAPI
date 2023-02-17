package com.example.ecommerce2;

import com.example.ecommerce2.service.CatalogService;
import com.example.ecommerce2.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@SpringBootApplication
@RequestMapping("/")
public class Ecommerce2Application {
	//private ClassCmdLineRunner classCmdLineRunner;
	private final CatalogService catalogService;
	private OrderService orderService;

	public static void main(String[] args) {
		SpringApplication.run(Ecommerce2Application.class, args);

		System.out.println("Hello World");
	}
	//@Bean
	public Runer getRuner( ){
		return new Runer( catalogService, orderService);
	}

}
