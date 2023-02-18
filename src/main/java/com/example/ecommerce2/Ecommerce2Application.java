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


		//------------------------------------------------------------------------------------------------------


		List<String> names = Arrays.asList("Ali","Ali","Eve","Alice", "Bob", "Charlie", "David", "Eve","Ali","Ayline","Aymen");
		System.out.println("names ::::"+names);

		List<String> namesStartingWithA = names.stream()
				.filter(s -> s.startsWith("A"))
				.distinct()
				.sorted()
				.collect(Collectors.toList());
		System.out.println("namesStartingWithA :::"+namesStartingWithA);

	    long countNamesStartingWithA = names.stream()
				.filter(s -> s.startsWith("A"))
				.count();
		System.out.println("number namesStartingWithA :::"+countNamesStartingWithA);


		List<String> filteredNames = names.stream()
				.filter(name -> name.length() <= 4)
				.collect(Collectors.toList());
		System.out.println("filteredNames.length() <= 4  ::: "+filteredNames);

		Map<String,Integer> mapNames = names.stream()
				.collect(Collectors.toMap(s -> s,s -> 1,Integer::sum));
		System.out.println("mapNames  :: "+mapNames);


		String joinedNamesInOneString = names.stream()
				.collect(Collectors.joining("/"));
		System.out.println("joinedNamesInOneString ::: "+joinedNamesInOneString);

		/*Map<String, Integer> mapNamesLength = names.stream()
				.collect(Collectors.toMap(s->s,s->s.length()));
		System.out.println("mapNamesLength  :::"+mapNamesLength);

		 */


		List<Product> productList = Arrays.asList(new Product(1,10.0,"prod1"),
				new Product(2,20.0,"prod2"),new Product(1,10.0,"prod1"));
		System.out.println("productList  ::"+productList);

		Map<Integer,Integer> productMap = productList.stream()
				.collect(Collectors.toMap(Product::getId, p->1,Integer::sum));
		System.out.println("Transformation productList into  productMap  ::: "+productMap);

		List<String> productNames = productList.stream()
				.map(p -> p.getName())//it's a transformation function
				.collect(Collectors.toList());
		System.out.println("productNames ::: "+productNames);

		List<String> productNamesX = productList.stream()
				.map(p -> p.getName()+"X")///it's a transformation function
				.collect(Collectors.toList());
		System.out.println("productNamesX ::: "+productNamesX);

		Set<Product> productSet = productList.stream()
				.collect(Collectors.toSet());
		System.out.println("productSet ::::"+productSet);


        List<Integer> integerList = Arrays.asList(1,1,1);
		Integer sumintegerList = integerList.stream()
				.reduce(0, (a, b) -> a + b);
		System.out.println("sumintegerList   :: "+sumintegerList);

		boolean anyMatch = productList.stream()
				.anyMatch(product ->  product.getPrice() > 10 );
		System.out.println("anyMatch :: "+anyMatch);


		boolean allMatch = productList.stream()
				.allMatch(product -> product.getPrice() >= 10);
		System.out.println("allMatch :: "+allMatch);

		boolean noneMatch = productList.stream()
				.noneMatch(product -> product.getPrice() > 10);
		System.out.println("noneMatch :: "+noneMatch);

		boolean anyMatch2 = productList.stream()
				.anyMatch(p-> p.isPriceMoreTen(p));
		System.out.println("anyMatch2 :: "+anyMatch2);


	}
	@Bean
	public Runer getRuner( ){
		return new Runer( catalogService, orderService);
	}

}
