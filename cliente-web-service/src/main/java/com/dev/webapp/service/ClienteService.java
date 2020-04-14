package com.dev.webapp.service;

import com.dev.webapp.document.Cliente;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClienteService {

	Flux<Cliente> findAll();
	
	Mono<Cliente> selByCodfid(String codFid);
	
	Flux<Cliente> selByNominativo(String description);
	
	Flux<Cliente> selByBollini(Integer bollini);
	
	Mono<Cliente> save(Cliente cliente);
	
	Mono<Void> delete(String id);
}
