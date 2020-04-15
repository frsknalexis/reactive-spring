package com.dev.uws.webapp.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.dev.uws.webapp.document.Usuario;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository("usuarioRepository")
public interface UsuarioRepository extends ReactiveMongoRepository<Usuario, String> {

	Mono<Usuario> findByEmail(String email);
	
	@Query("{ 'estado': ?0 }")
	Flux<Usuario> findUsuariosByEstado(Boolean estado);
}
