package com.elofturtle.asseteer.model;

import java.util.ArrayList;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * En {@link com.elofturtle.asseteer.model.Asset Asset} som installeras på klient.
 * <p>
 * Har en ägare, version, och leverantör.
 */
@JacksonXmlRootElement(localName = "Programvara")
public class Programvara extends Asset {
	/**
	 * Livscykelansvarig inom din organisation
	 */
	private String ägare;
	/**
	 * Version tillåter godtycklig standard.
	 * <p>
	 * En specifik utgåva av en programvara.
	 */
	private String version;
	/**
	 * Företaget som leverar programvaran. 
	 */
	private String leverantör;

	/**
	 * Standardkonstruktor, sätter inga parametrar.
	 */
	public Programvara() {
		super();
		updateId();
		setImportant(true);
	}
	
	/**
	 * Sätt namn
	 */
	@Override
	public void setName(String name) {
		super.setName(name);
		updateId();
	}

	/**
	 * Sätt grundläggande parametrar.
	 * @param name
	 * @param dependencies
	 */
	public Programvara(String name, ArrayList<Dependency> dependencies) {
		super(name, dependencies);
		setImportant(true);
		updateId();
	}
	
	/**
	 * Sätt samtliga parametrar utom beroenden.
	 * @param namn
	 * @param ägare
	 * @param version
	 * @param leverantör
	 */
	public Programvara(String namn, String ägare, String version, String leverantör) {
		super();
		setName(namn);
		setÄgare(ägare);
		setVersion(version);
		setLeverantör(leverantör);
		setImportant(true);
		updateId();
	}
	
	/**
	 * Sätter samtliga parametrar.
	 * @param namn
	 * @param ägare
	 * @param version
	 * @param leverantör
	 * @param beroenden
	 */
	public Programvara(String namn, String ägare, String version, String leverantör, ArrayList<Dependency> beroenden) {
		this(namn, ägare, version, leverantör);
		setImportant(true);
		this.setDependencies(beroenden);
	}

	/**
	 * Sätt bara namn.
	 * @param name
	 */
	public Programvara(String name) {
		super(name);
		setImportant(true);
		updateId();
	}
	
	/**
	 * Eftersom id beror på övriga parametrar så måste det räknas om i setters och konstruktorer.
	 */
	private void updateId() {
		String foo = getLeverantör() + " " + getName() + " " + getVersion();
		setId(foo.replaceAll("\\s+", "_").toLowerCase());
	}

	/**
	 * @return Förvaltningsansvarig
	 */
	public String getÄgare() {
		return ägare;
	}

	/**
	 * @param ägare Förvaltningsansvarig
	 */
	public void setÄgare(String ägare) {
		this.ägare = ägare;
		updateId();
	}

	/**
	 * @return version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version version
	 */
	public void setVersion(String version) {
		this.version = version;
		updateId();
	}

	/**
	 * @return Leverantör
	 */
	public String getLeverantör() {
		return leverantör;
	}

	/**
	 * @param leverantör Leverantör
	 */
	public void setLeverantör(String leverantör) {
		this.leverantör = leverantör;
		updateId();
	}

}
