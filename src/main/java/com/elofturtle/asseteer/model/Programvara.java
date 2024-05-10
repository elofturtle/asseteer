package com.elofturtle.asseteer.model;

import java.util.ArrayList;

public class Programvara extends Asset {
	private String ägare;
	private String version;
	private String leverantör;

	public Programvara() {
		super();
		updateId();
	}

	public Programvara(String name, ArrayList<Dependency> dependencies) {
		super(name, dependencies);
		updateId();
	}
	
	public Programvara(String namn, String ägare, String version, String leverantör) {
		super();
		setName(namn);
		setÄgare(ägare);
		setVersion(version);
		setLeverantör(leverantör);
		updateId();
	}
	
	public Programvara(String namn, String ägare, String version, String leverantör, ArrayList<Dependency> beroenden) {
		this(namn, ägare, version, leverantör);
		this.setDependencies(beroenden);
	}

	public Programvara(String name) {
		super(name);
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
