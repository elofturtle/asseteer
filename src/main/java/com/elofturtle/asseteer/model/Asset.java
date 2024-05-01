package com.elofturtle.asseteer.model;

import java.util.ArrayList;

//"Allting" är en Asset, i framtiden kommer det att behövas ett metaobjekt på toppen eller nåt annat för att identiifera toppnivåapplikationerna. 
public abstract class Asset implements Comparable<Asset> {
	private String name;
	private ArrayList<Dependency> dependencies;
	
	public abstract String getUUID();
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Asset(){
		name = "";
		dependencies = null;
	}
	
	public Asset(String name, ArrayList<Dependency> dependencies) {
		this.name = name;
		this.dependencies = dependencies;
	}
	
	public Asset(String name) {
		this.name = name;
		this.dependencies = null;
	}
	
	@Override
	// Alla klasser får ansvaret att generera ett globalt unikt ID
	public int compareTo(Asset o) {
		return this.getUUID().compareTo(o.getUUID());
	}

}
