package com.dev.webapp.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.dev.webapp.document.Cliente;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository("clienteRepository")
public interface ClienteRepository extends ReactiveMongoRepository<Cliente, String> {

	Mono<Cliente> findByCodfid(String codfid);
	
	Flux<Cliente> findByNominativoLike(String description);
	
	@Query("{ 'cards.bollini' : {$gt: ?0} }")
	Flux<Cliente> selectByBollini(Integer bollini);
}
