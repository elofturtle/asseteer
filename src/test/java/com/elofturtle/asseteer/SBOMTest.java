package com.elofturtle.asseteer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.elofturtle.asseteer.model.Asset;
import com.elofturtle.asseteer.model.Dependency;
import com.elofturtle.asseteer.model.SBOM;

class SBOMTest {

	@Test
	void testAddDependencyDependency() {
		Asset asset1 = new SBOM("github.com/ProtonMail/proton-bridge", "pkg:golang/github.com/ProtonMail/proton-bridge@v1.6.3");
		Asset asset2 = new SBOM("github.com/0xAX/notificator", "pkg:golang/github.com/0xAX/notificator@v0.0.0-20191016112426-3962a5ea8da1");
		asset1.addDependency(asset2);
		asset1.addDependency(asset2);
		asset1.addDependency(asset2);
		asset1.addDependency(asset2);
		assertEquals(asset1.getDependencies().size(), 1);
	}
	
	@Test
	void testRemoveExistingDependency() {
		Asset asset1 = new SBOM("github.com/ProtonMail/proton-bridge", "pkg:golang/github.com/ProtonMail/proton-bridge@v1.6.3");
		Asset asset2 = new SBOM("github.com/0xAX/notificator", "pkg:golang/github.com/0xAX/notificator@v0.0.0-20191016112426-3962a5ea8da1");
		asset1.addDependency(asset2);
		System.out.println("Removing " + asset2);
		System.out.println("Before:");
		System.out.println(asset1.toRepresentation());
		asset1.removeDependency(asset2);
		asset1.removeDependency(asset2);
		System.out.println("");
		System.out.println("After:");
		System.out.println(asset1.toRepresentation());
		assertEquals(asset1.getDependencies().size(), 0);
	}
	
	@Test
	void assignDependencyList() {
		Asset asset1 = new SBOM("github.com/ProtonMail/proton-bridge", "pkg:golang/github.com/ProtonMail/proton-bridge@v1.6.3");
		Asset asset2 = new SBOM("github.com/0xAX/notificator", "pkg:golang/github.com/0xAX/notificator@v0.0.0-20191016112426-3962a5ea8da1");
		Asset asset3 = new SBOM("github.com/0xAX/notificator", "pkg:golang/github.com/0xAX/notificator@v0.0.0-20191016112426-3962a5ea8da1");

		ArrayList<Dependency> dependencies = new ArrayList<Dependency>();
		dependencies.add(asset2.toDependency());
		dependencies.add(asset3.toDependency());
		asset1.setDependencies(dependencies);
		assertEquals(asset1.getDependencies(), dependencies);
	}
	
	@Test
	void sortDependencies() {
		Dependency a = new Dependency("a");
		Dependency c = new Dependency("c");
		Dependency b = new Dependency("b");
		
		ArrayList<Dependency> l = new ArrayList<Dependency>();
		l.add(a);
		l.add(c);
		l.add(b);
				
		Asset asset = new SBOM();
		asset.setId("foo");
		asset.setName("foo");
		
		System.out.println(asset.toRepresentation());
		
		
		
		System.out.println(asset.toRepresentation());
	}

}
