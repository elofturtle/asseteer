package com.elofturtle.asseteer.model;

import java.util.ArrayList;
import java.util.Objects;

//Används för att binda samman Assets via uuid 
public class Dependency implements Comparable<Dependency> {
	private String id;
	
	public Dependency(String id) {
		this.id = id;
	}

	public Dependency(Asset a) {
		this.id = a.getId();
	}
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return this.id;
	}
	
	@Override
	public int hashCode() {
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
		Dependency other = (Dependency) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int compareTo(Dependency o) {
		return this.id.compareTo(o.toString());
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Dependency a = new Dependency("a");
		Dependency b = new Dependency("b");
		
		ArrayList<Dependency> l = new ArrayList<Dependency>();
		l.add(a);
				
		
		System.out.println(a.compareTo(a));
		System.out.println(a.compareTo(b));
		System.out.println(l.contains(b));
	}

}
