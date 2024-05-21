package com.elofturtle.asseteer.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


/** 
 * Förenklad modell för beroenden från CycloneDX Security Bill of Material (SBOM). <p>
 * Tänkt att kunna läsa XML med metadata och dra ut det relevanta förutom traditionell inmatning.<p>
 */
@JacksonXmlRootElement(localName = "SBOM")
public class SBOM extends Asset {	
	
	/**
	 * Konstruktor som sätter namn och beräknar unikt id för objektet.
	 * @param name visningsnamn
	 * @param purl "pkg:{@literal<}groupid{@literal}>/{@literal<}artefact{@literal>}{@literal @}{@literal<}version{@literal>}"
	 */
	public SBOM(String name, String purl) {
		super();
		this.setName(name);
		this.setId(purl);
	}
	
	/**
	 * Standardkonstruktor.
	 * Användaren måste sätta relevanta värden på egen hand.
	 * 
	 */
	public SBOM() {
		super();
	}
}
