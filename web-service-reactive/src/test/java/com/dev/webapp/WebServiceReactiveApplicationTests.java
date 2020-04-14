package com.dev.webapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.dev.webapp.controller.SaludoController;

@SpringBootTest
class WebServiceReactiveApplicationTests {

	WebTestClient testClient;
	
	private void initTestController() {
		testClient = WebTestClient.bindToController(new SaludoController())
								.build();
	}
	
	@Test
	void contextLoads() {
		
		this.initTestController();
		
		testClient.get()
				.uri("/api/saludo/saludo")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isNotEmpty()
				.jsonPath("$").isEqualTo("Saluti, sono il tuo primo web service REST");
	}
	
	@Test
	void testSaluti2() {
		this.initTestController();
		
		testClient.get()
				.uri("/api/saludo/Nicola")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isNotEmpty()
				.jsonPath("$").isEqualTo("Saluti Nicola, sono il tuo primo web service REST");
	}
}
