package com.dev.webapp.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.dev.webapp.document.Cliente;
import com.dev.webapp.service.ClienteService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/clienti")
@Slf4j
public class ClienteController {

	@Autowired
	@Qualifier("clienteService")
	private ClienteService clienteService;
	
	@PostMapping(value = "/insert", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Mono<Cliente>> insertCliente(@RequestBody Cliente cliente) {
		Mono<Cliente> Monocliente = clienteService.save(cliente);
		return new ResponseEntity<Mono<Cliente>>(Monocliente, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Flux<Cliente>> findAll() {
		Flux<Cliente> fluxCliente = clienteService.findAll()
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Non e stato trovato alcun cliente!")));
		
		return new ResponseEntity<Flux<Cliente>>(fluxCliente, HttpStatus.OK);
	}
	
	@GetMapping(value = "/codice/{codfid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Mono<Cliente>> getClienteByCode(@PathVariable(value = "codfid") String codFid) {
		Mono<Cliente> monoCliente = clienteService.selByCodfid(codFid)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Non e stato trovato alcun cliente!")));
		
		return new ResponseEntity<Mono<Cliente>>(monoCliente, HttpStatus.OK);
	}
	
	@GetMapping(value = "/nominativo/{filtro}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Flux<Cliente>> findClienteByName(@PathVariable(value = "filtro") String filtro) {
		Flux<Cliente> fluxCliente = clienteService.selByNominativo(filtro)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Non e stato trovato alcun cliente!")));
		
		return new ResponseEntity<Flux<Cliente>>(fluxCliente, HttpStatus.OK);
	}
	
	@GetMapping(value = "/bollini/{filtro}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Flux<Cliente>> findClienteByBollini(@PathVariable(value = "filtro") Integer filtro) {
		Flux<Cliente> fluxCliente = clienteService.selByBollini(filtro)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Non e stato trovato alcun cliente!")));
		
		return new ResponseEntity<Flux<Cliente>>(fluxCliente, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/elimina/{id}")
	public ResponseEntity<Mono<Void>> deleteCliente(@PathVariable(value = "id") String id) {
		Mono<Void> monoDeleted = clienteService.delete(id);
		return new ResponseEntity<Mono<Void>>(monoDeleted, HttpStatus.OK);
	}
}