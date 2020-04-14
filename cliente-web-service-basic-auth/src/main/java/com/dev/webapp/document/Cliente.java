package com.dev.webapp.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "clienti")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8779422077552149952L;
	
	@Id
	private String id;

	@Field(name = "codfid")
	private String codfid;
	
	@Field(name = "nominativo")
	private String nominativo;
	
	@Field(name = "indirizzo")
	private String indirizzo;
	
	@Field(name = "comune")
	private String comune;
	
	@Field(name = "cap")
	private String cap;
	
	@Field(name = "prov")
	private String prov;
	
	@Field(name = "telefono")
	private String telefono;
	
	@Field(name = "mail")
	private String mail;
	
	@Field(name = "attivo")
	private boolean attivo;
	
	@Field(name = "datacreazione")
	private Date datacreazione = new Date();
	
	@Field(name = "cards")
	private Card cards;
}