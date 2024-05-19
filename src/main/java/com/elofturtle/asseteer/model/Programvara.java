package com.elofturtle.asseteer.model;

import java.util.ArrayList;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Programvara")
public class Programvara extends Asset {
	private String ägare;
	private String version;
	private String leverantör;

	public Programvara() {
		super();
		updateId();
		setImportant(true);
	}
	
	@Override
	public void setName(String name) {
		super.setName(name);
		updateId();
	}

	public Programvara(String name, ArrayList<Dependency> dependencies) {
		super(name, dependencies);
		setImportant(true);
		updateId();
	}
	
	public Programvara(String namn, String ägare, String version, String leverantör) {
		super();
		setName(namn);
		setÄgare(ägare);
		setVersion(version);
		setLeverantör(leverantör);
		setImportant(true);
		updateId();
	}
	
	public Programvara(String namn, String ägare, String version, String leverantör, ArrayList<Dependency> beroenden) {
		this(namn, ägare, version, leverantör);
		setImportant(true);
		this.setDependencies(beroenden);
	}

	public Programvara(String name) {
		super(name);
		setImportant(true);
		updateId();
	}
	
	private void updateId() {
		String foo = getLeverantör() + " " + getName() + " " + getVersion();
		setId(foo.replaceAll("\\s+", "_").toLowerCase());
	}

	public String getÄgare() {
		return ägare;
	}

	public void setÄgare(String ägare) {
		this.ägare = ägare;
		updateId();
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
		updateId();
	}

	public String getLeverantör() {
		return leverantör;
	}

	public void setLeverantör(String leverantör) {
		this.leverantör = leverantör;
		updateId();
	}

}
