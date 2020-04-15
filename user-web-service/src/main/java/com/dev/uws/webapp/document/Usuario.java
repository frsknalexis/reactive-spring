package com.dev.uws.webapp.document;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4076448095270664467L;

	@Id
	private String id;
	
	@Field(name = "nombre")
	private String nombre;
	
	@Field(name = "email")
	private String email;
	
	@Field(name = "password")
	private String password;
	
	@Field(name = "estado")
	private Boolean estado;
	
	@Field(name = "roles")
	private List<String> roles;
}
