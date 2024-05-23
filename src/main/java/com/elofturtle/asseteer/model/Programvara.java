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
	 * @param name namn
	 * @param dependencies beroenden
	 */
	public Programvara(String name, ArrayList<Dependency> dependencies) {
		super(name, dependencies);
		setImportant(true);
		updateId();
	}
	
	/**
	 * Sätt samtliga parametrar utom beroenden.
	 * @param namn namn
	 * @param ägare ägare
	 * @param version version
	 * @param leverantör leverntör
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
	 * @param namn namn
	 * @param ägare ägare
	 * @param version version
	 * @param leverantör leverantör
	 * @param beroenden beroenden
	 */
	public Programvara(String namn, String ägare, String version, String leverantör, ArrayList<Dependency> beroenden) {
		this(namn, ägare, version, leverantör);
		setImportant(true);
		this.setDependencies(beroenden);
	}

	/**
	 * Sätt bara namn.
	 * @param name namn
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
	 * Hämta ägare
	 * @return Förvaltningsansvarig förvaltningsansvarig
	 */
	public String getÄgare() {
		return ägare;
	}

	/**
	 * Spara förvaltningsansvarig
	 * @param ägare Förvaltningsansvarig
	 */
	public void setÄgare(String ägare) {
		this.ägare = ägare;
		updateId();
	}

	/**
	 * Hämta version
	 * @return version version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Spara version
	 * @param version version
	 */
	public void setVersion(String version) {
		this.version = version;
		updateId();
	}

	/**
	 * Hämta leverantör
	 * @return Leverantör
	 */
	public String getLeverantör() {
		return leverantör;
	}

	/**
	 * Spara leverantör
	 * @param leverantör Leverantör
	 */
	public void setLeverantör(String leverantör) {
		this.leverantör = leverantör;
		updateId();
	}

}
