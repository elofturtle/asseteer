package com.elofturtle.asseteer.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

//Förenklad modell för beroenden från CycloneDX SBOM
//Tänkt att kunna läsa XML med metadata och dra ut det relevanta förutom traditionell inmatning.
@JacksonXmlRootElement(localName = "SBOM")
public class SBOM extends Asset {	
	
	public SBOM(String name, String purl) {
		super();
		this.setName(name);
		this.setId(purl);
	}
	
	public SBOM() {
		super();
	}
}
