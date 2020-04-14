package com.dev.webapp;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Date;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.dev.webapp.document.Card;
import com.dev.webapp.document.Cliente;
import com.dev.webapp.service.ClienteService;

import reactor.core.publisher.Mono;

@WebFluxTest
//@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
//@AutoConfigureWebTestClient
//@DirtiesContext
public class ClienteControllerTest {

	@Autowired
	private WebTestClient webTestClient;
	
	@MockBean(name = "clienteService")
	private ClienteService clienteService;
	
	private Cliente createClienti() {
		Cliente cliente = new Cliente();
		cliente.setCodfid("65000000");
		cliente.setNominativo("Nicola La Rocca");
		cliente.setIndirizzo("P.zza giovanni XXIII");
		cliente.setComune("Alghero");
		cliente.setCap("07041");
		cliente.setProv("SS");
		cliente.setTelefono("0031259856");
		cliente.setMail("test@tiscali.it");
		cliente.setAttivo(true);
		cliente.setDatacreazione(new Date());
		
		Card card = new Card();
		card.setBollini(500);
		card.setUltimaspesa("2020-01-15");
		cliente.setCards(card);
		return cliente;
	}
	
	@Test
	@Order(1)
	public void testInsertCli() throws Exception {
		Cliente cliente = this.createClienti();
		
		Mono<Cliente> monoCliente = Mono.just(cliente);
		
		when(clienteService.save(cliente))
		.thenReturn(monoCliente);
		
		webTestClient.post()
					.uri("/api/clienti/insert")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.body(monoCliente, Cliente.class)
					.exchange()
					.expectStatus().isCreated()
					.expectHeader().contentType(MediaType.APPLICATION_JSON)
					.expectBody(Cliente.class);
	}
	
	@Test
	@Order(2)
	public void testClienteByCode() {
		Cliente cliente = this.createClienti();
		
		Mono<Cliente> monoCliente = Mono.just(cliente);
		
		when(clienteService.selByCodfid("65000000"))
			.thenReturn(monoCliente);
		
		webTestClient.get()
					.uri("/api/clienti/codice/{codfid}", Collections.singletonMap("codfid", "65000000"))
					.accept(MediaType.APPLICATION_JSON)
					.exchange()
					.expectStatus().isOk()
					.expectBody(Cliente.class)
					.consumeWith((response) -> {
						Cliente clienteResponse = response.getResponseBody();
						assertTrue(clienteResponse.getCodfid().equals("65000000"));
						assertTrue(clienteResponse.getNominativo().equals(cliente.getNominativo()));
					});
	}
}
