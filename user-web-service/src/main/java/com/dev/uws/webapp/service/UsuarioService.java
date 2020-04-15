package com.dev.uws.webapp.service;

import com.dev.uws.webapp.document.Usuario;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UsuarioService {

	Flux<Usuario> findAll();
	
	Flux<Usuario> findByEstado(Boolean estado);
	
	Mono<Usuario> findById(String id);
	
	Mono<Usuario> findByEmail(String email);
	
	Mono<Usuario> saveUsuario(Usuario usuario);
	
	Mono<Void> deleteUsuarioById(String id);
}
