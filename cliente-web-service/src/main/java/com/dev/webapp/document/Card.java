package com.dev.webapp.document;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3495201384685739284L;

	@Field(name = "bollini")
	private Integer bollini;
	
	@Field(name = "ultimaspesa")
	private String ultimaspesa;
}
