package com.elofturtle.asseteer.model;

import java.util.Objects;

/**
 * Används för att binda samman Assets via deras uuid (det är en tunn wrapper för en String).
 * Troligtvis vill du inte använda denna självständigt, det är en intern del av applikationen.
 * */
public class Dependency implements Comparable<Dependency> {
	private String id;
	
	/**
	 * Den tomma konstruktorn behövs för deserialisering.
	 * @hidden
	 */
	public Dependency() {
		id = "";
	}
	
	/**
	 * Håller en referens till det unika id:t för en Asset.
	 * @param id id
	 */
	public Dependency(String id) {
		this.id = id;
	}

	
	/**
	 * Håller en referens till en Asset via dess id.
	 * @param a asset
	 */
	public Dependency(Asset a) {
		this.id = a.getId();
	}
	
	

	/**
	 * Håller referens till en Asset.
	 * @return referens till en Asset.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sätt id manuellt.
	 * @param id id
	 */
	public void setId(String id) {
		if(id == null) {
			this.id = "";
		}else {
			this.id = id;
		}
	}

	/**
	 *Skriver ut det unika id:t som refereras.
	 *@return id id
	 */
	@Override
	public String toString() {
		return this.id;
	}
	
	/**
	 * id-attributet används för unikhet. 
	 * @return objektets hash
	 * @hidden
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	/**
	 * Bedömer likhet genom att jämföra id:t.
	 * @return likhetstest
	 */
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

	/**
	 *Jämför två Asset-id:n och använder det för att bedöma invärtes ordning.
	 *@return jämförelse heltal
	 */
	@Override
	public int compareTo(Dependency o) {
		return this.id.compareTo(o.toString());
	}
	
}
