package com.dev.uws.webapp.handler;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;

import com.dev.uws.webapp.document.Usuario;
import com.dev.uws.webapp.service.UsuarioService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UsuarioHandler {

	@Autowired
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;
	
	public Mono<ServerResponse> findAllUsuarios(ServerRequest request) {
		Flux<Usuario> usuariosFlux = usuarioService.findAll();
		return ServerResponse.ok()
							.contentType(MediaType.APPLICATION_JSON)
							.body(usuariosFlux, Usuario.class);
	}
	
	public Mono<ServerResponse> findClienteById(ServerRequest request) {
		String id = request.pathVariable("id");
		return usuarioService.findById(id)
							.flatMap((usuario) -> {
								return ServerResponse.ok()
													.contentType(MediaType.APPLICATION_JSON)
													.body(BodyInserters.fromValue(usuario));
							})
							.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> createUser(ServerRequest request) {
		Mono<Usuario> monoUsuario = request.bodyToMono(Usuario.class);
		return monoUsuario
				.flatMap((usuario) -> {
					return usuarioService.saveUsuario(usuario);
				})
				.flatMap((usuarioResponse) -> {
					return ServerResponse.created(URI.create("/api/v1/usuario/".concat(usuarioResponse.getId())))
							.contentType(MediaType.APPLICATION_JSON)
							.body(BodyInserters.fromValue(usuarioResponse));
				});
	}
	
	public Mono<ServerResponse> updateUsuario(ServerRequest request) {
		Mono<Usuario> monoUsuario = request.bodyToMono(Usuario.class);
		String id = request.pathVariable("id");
		
		Mono<Usuario> usuarioDB = usuarioService.findById(id);
		
		return usuarioDB
				.zipWith(monoUsuario, (uDB, uR) -> {
					uDB.setNombre(uR.getNombre());
					uDB.setEmail(uR.getEmail());
					uDB.setPassword(uR.getPassword());
					uDB.setEstado(uR.getEstado());
					uDB.setRoles(uR.getRoles());
					return uDB;
				})
				.flatMap((usuario) -> {
					return usuarioService.saveUsuario(usuario);
				})
				.flatMap((usuarioResponse) -> {
					return ServerResponse.created(URI.create("/api/v1/usuario/".concat(usuarioResponse.getId())))
								.contentType(MediaType.APPLICATION_JSON)
								.body(BodyInserters.fromValue(usuarioResponse));
				})
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> deleteUsuario(ServerRequest request) {
		String id = request.pathVariable("id");
		Mono<Usuario> monoUsuario = usuarioService.findById(id);
		return monoUsuario
				.flatMap((usuario) -> {
					return usuarioService.deleteUsuarioById(usuario.getId())
							.then(ServerResponse.noContent().build());
				})
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> findUsuariosByEstado(ServerRequest request) {
		String estado = request.pathVariable("estado");
		Flux<Usuario> usuarioFlux = usuarioService.findByEstado(Boolean.valueOf(estado));
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(usuarioFlux, Usuario.class);
	}
}
