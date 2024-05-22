package com.elofturtle.asseteer.test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import com.elofturtle.asseteer.io.ProgramvaraFileReader;
import com.elofturtle.asseteer.model.Asset;

class TestProgramvaraFileReader {

	@Test
	void testReadFromFile() {
		var fileReader = new ProgramvaraFileReader();
		String path = "src/test/resources/programvara-reader.xml";
		ArrayList<Asset> programvaror;
		programvaror = fileReader.readFromFile(path);
		for(var p : programvaror) {
			System.out.println(p.toRepresentation());
		}
		assertAll("Test XML import of programvaror",
				() -> assertEquals("Putty : Programvara:open_source_putty_0.81\n\tDependencies\n\t\tabc123\n\t\tdef789\n", programvaror.get(0).toRepresentation()),
				() -> assertEquals("Edge : Programvara:microsoft_edge_32.9",programvaror.get(1).toRepresentation()),
				() -> assertEquals(programvaror.size() ,2)
				);
	}

}