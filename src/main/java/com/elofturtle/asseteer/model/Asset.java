package com.elofturtle.asseteer.model;

import java.util.ArrayList;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import javax.annotation.Generated;
import java.util.Objects;

/**
 * "Allting" är en Asset, i framtiden kommer det att behövas ett 
 * metaobjekt på toppen eller nåt annat för att identififera toppnivåapplikationerna. 
 */
@JacksonXmlRootElement(localName = "Asset")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "assetType")
/*
 * TODO: kan JsonSubTypes deklareras nere i respektive subklass, så att subklasser från tredje part skulle kunna läggas till dynamiskt?
 * */
@JsonSubTypes({
    @JsonSubTypes.Type(value = Programvara.class, name = "Programvara"),
    @JsonSubTypes.Type(value = SBOM.class, name = "SBOM")
    })
public abstract class Asset implements Comparable<Asset> {
	/**
	 * En eftergift för JacksonXML.
	 * Ville egentligen ha assetType som ett attribut när jag gjorde 
	 * export till fil, men det gick dåligt upprepade gånger.
	 * 
	 * I en senare version borde jag anstränga mig lite för att få till det på ett bättre sätt.
	 * 
	 * GPT hade svårt att skilja på Jackson JSON respektive XML och gav ofta felaktiga exempel.
	 */
	private String assetType; 
	
	/**
	 * What's in a name... 
	 * Den delen av en asset som är översiktlig, t.ex. applikationsnamn eller artefakt.
	 * Kommer att variera beroende för subklasser vad som är ett rimligt värde.
	 */
	private String name;
	/**
	 * Subklasser ansvarar för att sätta id till ett "bra, unikt, värde".
	 * Avsikten är att det ska motsvara {@link <a href="https://github.com/package-url/purl-spec">purl</<a>}
	 */
	private String id;
	/**
	 * Referenser till beroenden.
	 */
	private ArrayList<Dependency> dependencies;
	/**
	 * Håller reda på primära Assets, dvs 
	 * vilka tillgångar i ett stort bibliotek har interna förvaltare
	 * t.ex. egenutvecklade applikationer.
	 */
	private boolean important;
	
	/**
	 * @param id
	 * Eftersom vi i dagsläget muterar id vid skapande så får vi problem när det läses
	 * in från fil. Därför har vi en separat metod som bara ska användas vid {@link com.elofturtle.asseteer.io.XmlUtil deserialisering}. 
	 * @deprecated kommer troligtvis att deprekeras i version 2.
	 */
	@JsonSetter("id")
    public void setIdFromDeserialization(String id) {
        this.id = id;
    }
	
	/**
	 * @return  Är detta en toppnivåapplikation med en intern förvaltare?
	 */
	public boolean isImportant() {
		return important;
	}

	/**
	 * @param important
	 * Om satt så betecknar det att detta är en Asset som är viktig, och troligtvis har en intern förvaltare/intressent.
	 */
	public void setImportant(boolean important) {
		this.important = important;
	}

	/**
	 * @return namn
	 * Namn är en läsvänlig egenskap.
	 * Det kan finnas dubletter, t.ex. två versioner av samma applikation.
	 * @see #toRepresentation() 
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @hidden
	 */
	@Generated("none")
	@Override
	public int hashCode() { //hash och equals tydligen viktiga om man vill ha saker i listor, compareTo() inte tillräckligt.
		return Objects.hash(id);
	}

	/**
	 *@hidden
	 */
	@Generated("none")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Asset other = (Asset) obj;
		return Objects.equals(id, other.id);
	}

	/**
	 * @return id (purl)
	 */
	public String getId() {
		return id;
	}

	
	/**
	 * @param id
	 * Varje asset identifieras av ett id, t.ex. purl.
	 * För närvarande innehåller den klassnamnet för unikhet mellan subklasse, vilket
	 * konkurrerar med {@link #assetType} så det måste refaktoreras vid tillfälle.
	 */
	public void setId(String id) {
		this.id = this.getClass().getSimpleName() + ":" + id;
	}

	/**
	 * @return en referens till kända beroenden.
	 */
	public ArrayList<Dependency> getDependencies() {
		return dependencies;
	}

	/**
	 * @param dependencies
	 * Koppla en extern lista med beroenden till en Asset.
	 * Adderar inte till existerande beroenden!
	 */
	public void setDependencies(ArrayList<Dependency> dependencies) {
		// Möjliggör manipulering av internt state, borde kanske kopiera istället.
		this.dependencies = dependencies;
	}
	
	
	/**
	 * @param d
	 * Addera en {@link com.elofturtle.asseteer.model.Dependency Dependency} till Asset.
	 */
	public void addDependency(Dependency d) {
		if(dependencies == null) {
			dependencies = new ArrayList<Dependency>();
		}
		if(!dependencies.contains(d)) {
			dependencies.add(d);
		}
	}
	
	/**
	 * @param a
	 * Addera en {@link com.elofturtle.asseteer.model.Asset Asset} som beroende till denna Asset.
	 */
	public void addDependency(Asset a) {
		addDependency(new Dependency(a));
	}
	
	/**
	 * @param d
	 * Avlägsna ett beroende.
	 */
	public void removeDependency(Dependency d) {
		dependencies.remove(d);
		
	}
	
	/**
	 * @param a
	 * Avlägsna ett beroende.
	 */
	public void removeDependency(Asset a) {
		dependencies.remove(new Dependency(a));
	}

	/**
	 * @param name
	 * Sätt visningsnamn på Asset.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Behövs för {@link com.elofturtle.asseteer.io.XmlUtil#deserialize(String) deserialisering}.
	 * Kräver att alla attribut sätts manuellt, förutom {@link #assetType}.
	 */
	public Asset(){
		name = null;
		dependencies = null;
		important = false;
		setAssetType();
	}
	
	/**
	 * @return {@link com.elofturtle.asseteer.model.Dependency Dependency} som refererar till {@link #id} för objektet.
	 * <br/>	  
	 * Eftersom det används mycket i länkar, men vi egentligen arbetar med Assets, så är det bekvämt att kunna returnera referensen från objektet istället för att konvertera det manuellt.
	 */
	public Dependency toDependency() {
		return new Dependency(this);
	}
	
	/**
	 * Sätter de viktigaste (minsta rekommenderade) parametrarna.
	 * @param name Objektets {@link #name namn}
	 * @param dependencies Lista med {@link #dependencies beroenden}.
	 * 
	 */
	public Asset(String name, ArrayList<Dependency> dependencies) {
		this.name = name;
		this.dependencies = dependencies;
		important = false;
	}
	
	/**
	 * Grundläggande konstruktor. <p>
	 * Andra parametrar måste sättas via setters för att objektet ska vara användbart.
	 * @param name Objektets {@link #name}.
	 */
	public Asset(String name) {
		this.name = name;
		this.dependencies = null;
	}
	
	/**
	 *{@inheritDoc}
	 *<p>
	 *Returnerar {@link #name namn} på ojektet.
	 */
	@Override
	public String toString() {
		return this.name;
	}
	
	/**
	 * En mer detaljerad (beskrivande) sträng som beskriver en {@link Asset}.
	 * @return Om objektet saknar beroenden: 
	 * <pre>
	 * namn : id
	 * </pre>
	 * 
	 * Om objektet har n beroenden:
	 * <pre>
	 * namn: id
	 *    Dependencies:
	 *       dependency1
	 *       ...
	 *       dependency n
	 *</pre>
	 */
	public String toRepresentation() {
		StringBuilder str = new StringBuilder();
		str.append(name + " : " + id);
		if(dependencies != null && dependencies.size() != 0) {
			str.append("\n\tDependencies\n");
			for (var d : dependencies) {
				str.append("\t\t" + d + "\n");
			}
		}
		return str.toString();
	}
	
	/**
	 * {@inheritDoc}
	 * Alla klasser får ansvaret att generera ett globalt unikt ID som kan användas vid jämförelser.
	 * <p>Borde integrera klasstyp här på sikt.
	 */
	@Override
	public int compareTo(Asset o) {
		return this.id.compareTo(o.id);
	}

	/**
	 * Behövs för {@link com.elofturtle.asseteer.io.XmlUtil XmlUtil} och bör inte ändras.
	 * @return {@link #assetType} 
	 */
	public String getAssetType() {
		return assetType;
	}

	/**
	 * Behövs för {@link com.elofturtle.asseteer.io.XmlUtil XmlUtil}.
	 */
	public void setAssetType() {
		assetType = this.getClass().getSimpleName();
	}

}
