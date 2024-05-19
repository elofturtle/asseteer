package com.elofturtle.asseteer.model;

import java.util.ArrayList;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import javax.annotation.Generated;
import java.util.Objects;

//"Allting" är en Asset, i framtiden kommer det att behövas ett metaobjekt på toppen eller nåt annat för att identiifera toppnivåapplikationerna. 
@JacksonXmlRootElement(localName = "Asset")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "assetType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Programvara.class, name = "Programvara"),
    @JsonSubTypes.Type(value = SBOM.class, name = "SBOM")
    })
public abstract class Asset implements Comparable<Asset> {
	private String assetType;
	private String name;
	private String id;
	private ArrayList<Dependency> dependencies;
	private boolean important;
	
	@JsonSetter("id")
    public void setIdFromDeserialization(String id) {
        this.id = id;
    }
	
	public boolean isImportant() {
		return important;
	}

	public void setImportant(boolean important) {
		this.important = important;
	}

	public String getName() {
		return name;
	}
	
	@Generated("none")
	@Override
	public int hashCode() { //hash och equals tydligen viktiga om man vill ha saker i listor, compareTo() inte tillräckligt.
		return Objects.hash(id);
	}

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = this.getClass().getSimpleName() + ":" + id;
	}

	public ArrayList<Dependency> getDependencies() {
		// Möjliggör manipulering av internt state, borde kanske returnera en kopia.
		return dependencies;
	}

	public void setDependencies(ArrayList<Dependency> dependencies) {
		// Möjliggör manipulering av internt state, borde kanske kopiera istället.
		this.dependencies = dependencies;
	}
	
	public void addDependency(Dependency d) {
		if(dependencies == null) {
			dependencies = new ArrayList<Dependency>();
		}
		if(!dependencies.contains(d)) {
			dependencies.add(d);
		}
	}
	
	public void addDependency(Asset a) {
		addDependency(new Dependency(a));
	}
	
	public void removeDependency(Dependency d) {
		dependencies.remove(d);
		
	}
	
	public void removeDependency(Asset a) {
		dependencies.remove(new Dependency(a));
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Asset(){
		name = null;
		dependencies = null;
		important = false;
		setAssetType();
	}
	
	public Dependency toDependency() {
		return new Dependency(this);
	}
	
	public Asset(String name, ArrayList<Dependency> dependencies) {
		this.name = name;
		this.dependencies = dependencies;
		important = false;
	}
	
	public Asset(String name) {
		this.name = name;
		this.dependencies = null;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
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
	
	@Override
	// Alla klasser får ansvaret att generera ett globalt unikt ID
	public int compareTo(Asset o) {
		return this.id.compareTo(o.id);
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType() {
		assetType = this.getClass().getSimpleName();
	}

}
