package com.elofturtle.asseteer.model;

import java.util.ArrayList;

//Förenklad modell för beroenden från CycloneDX SBOM
//Tänkt att kunna läsa XML med metadata och dra ut det relevanta förutom traditionell inmatning.
public class SBOM extends Asset {	
	public SBOM(String name, String purl) {
		super();
		this.setName(name);
		this.setId(purl);
	}
	
	public SBOM() {
		super();
	}
	

	public static void main(String[] args) {
		Asset asset1 = new SBOM("github.com/ProtonMail/proton-bridge", "pkg:golang/github.com/ProtonMail/proton-bridge@v1.6.3");
		Asset asset2 = new SBOM("github.com/0xAX/notificator", "pkg:golang/github.com/0xAX/notificator@v0.0.0-20191016112426-3962a5ea8da1");
		Asset asset3 = new SBOM();
		asset3.setId("pkg:golang/github.com/AndreasBriese/bbloom@v0.0.0-20190306092124-e2d15f34fcf9");
		asset3.setName("github.com/AndreasBriese/bbloom");
		
		ArrayList<Asset> l = new ArrayList<Asset>();
		l.add(asset1);

		asset1.addDependency(asset2);
		asset1.addDependency(asset2);
		asset1.addDependency(asset3);
		asset1.removeDependency(asset3);
		
		for(Asset a : l) {
			System.out.println(a.toRepresentation());
		}
	
		System.out.println(asset1.getDependencies().size());
	}

}
