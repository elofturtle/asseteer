package com.elofturtle.asseteer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.elofturtle.asseteer.model.Asset;
import com.elofturtle.asseteer.model.SBOM;

class SBOMTest_addDependenciesMultipleTimes {

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

}
