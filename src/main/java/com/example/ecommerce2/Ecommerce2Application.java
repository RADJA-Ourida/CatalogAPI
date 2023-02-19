package com.example.ecommerce2;

import com.example.ecommerce2.model.Product;
import com.example.ecommerce2.service.CatalogService;
import com.example.ecommerce2.service.OrderService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
	@Bean
	public Runer getRuner( ){
		return new Runer( catalogService, orderService);
	}

}
