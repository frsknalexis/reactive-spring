package com.dev.uws.webapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.dev.uws.webapp.document.Usuario;
import com.dev.uws.webapp.repository.UsuarioRepository;
import com.dev.uws.webapp.service.UsuarioService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	@Qualifier("usuarioRepository")
	private UsuarioRepository usuarioRepository;
	
	@Override
	public Flux<Usuario> findAll() {
		Flux<Usuario> usuarioFlux = usuarioRepository.findAll();
		return usuarioFlux;
	}

	@Override
	public Flux<Usuario> findByEstado(Boolean estado) {
		Flux<Usuario> usuarioFlux = usuarioRepository.findUsuariosByEstado(estado);
		return usuarioFlux;
	}

	@Override
	public Mono<Usuario> findById(String id) {
		Mono<Usuario> monoUsuario = usuarioRepository.findById(id);
		return monoUsuario;
	}

	@Override
	public Mono<Usuario> findByEmail(String email) {
		Mono<Usuario> monoUsuario = usuarioRepository.findByEmail(email);
		return monoUsuario;
	}

	@Override
	public Mono<Usuario> saveUsuario(Usuario usuario) {
		Mono<Usuario> monoUsuario = usuarioRepository.save(usuario);
		return monoUsuario;
	}

	@Override
	public Mono<Void> deleteUsuarioById(String id) {
		Mono<Usuario> monoUsuario = Mono.empty();
		if (!id.isEmpty() && id != null) {
			monoUsuario = usuarioRepository.findById(id);
		};
		return monoUsuario.flatMap((usuario) -> {
			return usuarioRepository.delete(usuario);
		});
	}
}