package com.example.webfluxsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class WebfluxSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxSecurityApplication.class, args);
	}

	@Service
	public static class CrmService {

		private final Map<Integer, Customer> db =
				new ConcurrentHashMap<>();

		private final AtomicInteger id = new AtomicInteger();

		@Secured("ROLE_USER")
		public Mono<Customer> getCustomerById(Integer id) {
			var customer = this.db.get(id);
			return Mono.just(customer);
		}

		@PreAuthorize("hasRole('ADMIN')")
		public Mono<Customer> insert(String name) {
			var newCustomer = new Customer(id.incrementAndGet(), name);
			this.db.put(newCustomer.id(), newCustomer);
			return Mono.just(newCustomer);
		}
	}

	public record Customer(Integer id, String name) {
	}

	@Controller
	static
	class SecureGraphqlController {

		private final CrmService crm;

		SecureGraphqlController(CrmService crm) {
			this.crm = crm;
		}

		@MutationMapping
		Mono<Customer> insert(@Argument String name) {
			return this.crm.insert(name);
		}

		@QueryMapping
		Mono<Customer> customerById(@Argument Integer id) {
			return this.crm.getCustomerById(id);
		}
	}
}

