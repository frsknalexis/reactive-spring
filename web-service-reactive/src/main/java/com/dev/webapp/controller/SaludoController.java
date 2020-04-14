package com.dev.webapp.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/saludo")
@CrossOrigin(origins = "*")
public class SaludoController {

	@GetMapping("/saludo")
	public String getSaluti() {
		return ("Saluti, sono il tuo primo web service REST");
	}
	
	@GetMapping(value = "/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getSaluti2(@PathVariable(value = "nome") String nome) {
		return String.format("Saluti %s, sono il tuo primo web service REST", nome);
	}
}
