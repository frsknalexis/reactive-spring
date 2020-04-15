package com.dev.uws.webapp.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.dev.uws.webapp.handler.UsuarioHandler;

@Configuration
public class UsuarioRouter {

	@Bean
	public RouterFunction<ServerResponse> usuarioRouters(UsuarioHandler handler) {
		return RouterFunctions.route(RequestPredicates.GET("/api/v1/usuario/all"), handler::findAllUsuarios)
				.andRoute(RequestPredicates.GET("/api/v1/usuario/{id}"), handler::findClienteById)
				.andRoute(RequestPredicates.POST("/api/v1/usuario"), handler::createUser)
				.andRoute(RequestPredicates.PUT("/api/v1/usuario/{id}"), handler::updateUsuario)
				.andRoute(RequestPredicates.DELETE("/api/v1/usuario/{id}"), handler::deleteUsuario);
	}
}
