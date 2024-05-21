package com.elofturtle.asseteer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.elofturtle.asseteer.model.Asset;
import com.elofturtle.asseteer.model.Dependency;
import com.elofturtle.asseteer.model.Programvara;
import com.elofturtle.asseteer.model.SBOM;

class TestSBOM {

	@Test
	void testAddDependencyDependency() {
		Asset asset1 = new SBOM("github.com/ProtonMail/proton-bridge", "pkg:golang/github.com/ProtonMail/proton-bridge@v1.6.3");
		Asset asset2 = new SBOM("github.com/0xAX/notificator", "pkg:golang/github.com/0xAX/notificator@v0.0.0-20191016112426-3962a5ea8da1");
		asset1.addDependency(asset2);
		asset1.addDependency(asset2);
		asset1.addDependency(asset2);
		asset1.addDependency(asset2);
		assertEquals(1, asset1.getDependencies().size());
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
		assertEquals(0, asset1.getDependencies().size());
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
		assertEquals(dependencies, asset1.getDependencies());
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
		asset.setDependencies(l);
		
		Asset asset2 = new SBOM();
		asset2.setId("bar");
		asset2.setName("bar");
		
		Programvara asset3 = new Programvara();
		asset3.setLeverantör("osninja");
		asset3.setVersion("1.2.3");
		asset3.setÄgare("Jonatan");
		asset3.setName("baz");
		
		
		ArrayList<Asset> assets = new ArrayList<>();
		assets.add(asset);
		assets.add((Asset) asset3);
		assets.add(asset2);
		assets.sort(null);
		
		
		for(var item : assets ) {
			if(item.getDependencies() != null) {
				item.getDependencies().sort(null);
			}
			System.out.println(item.toRepresentation());
		}
		
		assertAll("Can I order my SBOMs (is compareTo working as intended)?",
				() -> assertEquals("a", asset.getDependencies().get(0).toString()),
				() -> assertEquals("b", asset.getDependencies().get(1).toString()),
				() -> assertEquals("c", asset.getDependencies().get(2).toString()),
				() -> assertEquals("baz", assets.get(0).toString()),
				() -> assertEquals("bar", assets.get(1).toString()),
				() -> assertEquals("foo", assets.get(2).toString())
				);
		}
}
