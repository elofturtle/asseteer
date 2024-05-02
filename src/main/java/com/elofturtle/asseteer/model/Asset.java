package com.elofturtle.asseteer.model;

import java.util.ArrayList;
import java.util.Objects;

//"Allting" är en Asset, i framtiden kommer det att behövas ett metaobjekt på toppen eller nåt annat för att identiifera toppnivåapplikationerna. 
public abstract class Asset implements Comparable<Asset> {
	private String name;
	private String id;
	private ArrayList<Dependency> dependencies;
	
	//public abstract String getUUID();
	
	public String getName() {
		return name;
	}
	
	@Override
	public int hashCode() { //hash och equals tydligen viktiga om man vill ha saker i listor, compareTo() inte tillräckligt.
		return Objects.hash(id);
	}

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
		return dependencies;
	}

	public void setDependencies(ArrayList<Dependency> dependencies) {
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
		//this.id = getUUID();
	}
	
	public Asset(){
		name = null;
		dependencies = null;
		//id = getUUID();
	}
	
	public Asset(String name, ArrayList<Dependency> dependencies) {
		this.name = name;
		this.dependencies = dependencies;
	}
	
	public Asset(String name) {
		this.name = name;
		this.dependencies = null;
		//this.id = getUUID();
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public String toRepresentation() {
		StringBuilder str = new StringBuilder();
		str.append(name + " : " + id);
		if(dependencies != null) {
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

}
