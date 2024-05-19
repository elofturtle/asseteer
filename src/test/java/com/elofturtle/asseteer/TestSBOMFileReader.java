package com.elofturtle.asseteer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.elofturtle.asseteer.io.SBOMFileReader;

class TestSBOMFileReader {

	@Test
	void testReadFromFile() {
		SBOMFileReader fr = new SBOMFileReader(); 
		String fname = "src/test/resources/sbom-reader.bom.xml";
		var foo = fr.readFromFile(fname);
		for(var sbom : foo) {
			System.out.println(sbom.toRepresentation());
		}
		 
		assertAll("Test reading example bom.xml file with SBOMs and examine object returned",
				 () -> assertEquals("scm.sfa.se/test/sbomReader : SBOM:pkg:scm.sfa.se/test/sbomReader@v1.0.0\n\tDependencies\n\t\tSBOM:pkg:scm.sfa.se/test/a@v0.0.0\n\t\tSBOM:pkg:scm.sfa.se/test/b@v0.0.1\n", foo.get(0).toRepresentation()),
				 () -> assertEquals("scm.sfa.se/test/a : SBOM:pkg:scm.sfa.se/test/a@v0.0.0", foo.get(1).toRepresentation()),
				 () -> assertEquals("scm.sfa.se/test/b : SBOM:pkg:scm.sfa.se/test/b@v0.0.1\n\tDependencies\n\t\tSBOM:pkg:scm.sfa.se/test/c@v0.0.2\n", foo.get(2).toRepresentation()),
				 () -> assertEquals("scm.sfa.se/test/c : SBOM:pkg:scm.sfa.se/test/c@v0.0.2", foo.get(3).toRepresentation()),
				 () -> assertEquals(4, foo.size())
			 );
	}

}
