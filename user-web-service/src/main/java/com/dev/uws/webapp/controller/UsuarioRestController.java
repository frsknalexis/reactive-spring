package com.dev.uws.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.uws.webapp.document.Usuario;
import com.dev.uws.webapp.service.UsuarioService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioRestController {

	@Autowired
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;
	
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<Flux<Usuario>>> findAll() {
		Flux<Usuario> usuarioFlux = usuarioService.findAll();
		return Mono.just(ResponseEntity.ok()
										.contentType(MediaType.APPLICATION_JSON)
										.body(usuarioFlux));
	}
	
	@PostMapping(value = "/insert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<Usuario>> insertCliente(@RequestBody Usuario usuario) {
		Mono<Usuario> monoUsuario = Mono.just(usuario);
		return monoUsuario
				.flatMap((usuarioRequest) -> {
					return usuarioService.saveUsuario(usuarioRequest);
				})
				.map((usuarioResponse) -> {
					return new ResponseEntity<Usuario>(usuarioResponse, HttpStatus.CREATED);
				});
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<Usuario>> getOneById(@PathVariable(value = "id") String id) {
		Mono<Usuario> monoUsuario = usuarioService.findById(id);
		return monoUsuario
				.map((usuario) -> {
					return ResponseEntity.ok()
										.contentType(MediaType.APPLICATION_JSON)
										.body(usuario);
				})
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	@PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, 
				produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<Usuario>> updateUsuario(@PathVariable(value = "id") String id, @RequestBody Usuario usuario) {
		Mono<Usuario> monoUsuarioRequest = Mono.just(usuario);
		Mono<Usuario> monoUsuarioDB = usuarioService.findById(id);
		return monoUsuarioDB.zipWith(monoUsuarioRequest)
							.map((tupla) -> {
								Usuario usuarioDB = tupla.getT1();
								Usuario usuarioR = tupla.getT2();
								
								usuarioDB.setNombre(usuarioR.getNombre());
								usuarioDB.setEmail(usuarioR.getEmail());
								usuarioDB.setPassword(usuarioR.getPassword());
								usuarioDB.setEstado(usuarioR.getEstado());
								usuarioDB.setRoles(usuarioR.getRoles());
								return usuarioDB;
							})
							.flatMap((u) -> {
								return usuarioService.saveUsuario(u);
							})
							.map((usuarioResponse) -> {
								return new ResponseEntity<Usuario>(usuarioResponse, HttpStatus.CREATED);
							})
							.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public Mono<ResponseEntity<Void>> deleteUsuario(@PathVariable(value = "id") String id) {
		Mono<Usuario> monoUsuario = usuarioService.findById(id);
		return monoUsuario
				.flatMap((usuario) -> {
					return usuarioService.deleteUsuarioById(usuario.getId())
							.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
				})
				.switchIfEmpty(Mono.just(new ResponseEntity<Void>(HttpStatus.NOT_FOUND)));
	}
	
	@GetMapping(value = "/estado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<Flux<Usuario>>> findUsuarioByEstado(@PathVariable(value = "estado") Boolean estado) {
		Flux<Usuario> usuarioFlux = usuarioService.findByEstado(estado);
		return Mono.just(ResponseEntity.ok()
									.contentType(MediaType.APPLICATION_JSON)
									.body(usuarioFlux));
	}
}