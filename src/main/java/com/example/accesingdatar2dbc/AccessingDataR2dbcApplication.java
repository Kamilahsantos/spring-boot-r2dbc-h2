package com.example.accesingdatar2dbc;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.Arrays;

@SpringBootApplication
public class AccessingDataR2dbcApplication {

	private static final Logger log = LoggerFactory.getLogger(AccessingDataR2dbcApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AccessingDataR2dbcApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository repository) {

		return (args) -> {
			repository.saveAll(Arrays.asList(new Customer("Kamila", "Santos"),
					new Customer("Kamila", "Fatima"),
					new Customer("Kamila", "Oliveira"),
					new Customer("David", "Palmer"),
					new Customer("Michelle", "Dessler")))
					.blockLast(Duration.ofSeconds(10));

			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			repository.findAll().doOnNext(customer -> {
				log.info(customer.toString());
			}).blockLast(Duration.ofSeconds(10));

			log.info("");

			repository.findById(1L).doOnNext(customer -> {
				log.info("Customer found with findById(1L):");
				log.info("--------------------------------");
				log.info(customer.toString());
				log.info("");
			}).block(Duration.ofSeconds(10));


			log.info("Customer found with findByLastName('Santos'):");
			log.info("--------------------------------------------");
			repository.findByLastName("Santos").doOnNext(bauer -> {
				log.info(bauer.toString());
			}).blockLast(Duration.ofSeconds(10));;
			log.info("");
		};
	}

}