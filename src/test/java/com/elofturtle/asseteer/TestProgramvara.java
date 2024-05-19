package com.elofturtle.asseteer;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import com.elofturtle.asseteer.model.Dependency;
import com.elofturtle.asseteer.model.Programvara;

class TestProgramvara {
	
	private static Programvara ProgramvaraFactory() {
		var programvara1 = new Programvara();
		programvara1.setImportant(true);
		programvara1.setLeverantör("Open Source");
		programvara1.setName("Putty");
		programvara1.setVersion("0.81.0");
		programvara1.setÄgare("Leif B Nilsson");
		
		var d1 = new Dependency("pkg:golang/github.com/ProtonMail/proton-bridge@v1.6.3");
		programvara1.addDependency(d1);
		
		Programvara programvara2 = new Programvara();
		programvara2.setLeverantör("osninja");
		programvara2.setVersion("1.2.3");
		programvara2.setÄgare("Jonatan");
		programvara2.setName("baz");
		programvara1.addDependency(programvara2.toDependency());
		
		return programvara1;
	}

	@Test
	void testProgramvaraNamnBeroenden() {
		ArrayList<Dependency> deplist = new ArrayList<>();
		
		Programvara programvara2 = new Programvara();
		programvara2.setLeverantör("osninja");
		programvara2.setVersion("1.2.3");
		programvara2.setÄgare("Jonatan");
		programvara2.setName("baz");		
		
		deplist.add(programvara2.toDependency());
		deplist.add(new Dependency("pkg:golang/github.com/ProtonMail/proton-bridge@v1.6.3"));
		
		var p = new Programvara("Putty", deplist);
		
		assertAll("Konstruktor som tar namn och beroenden",
			() -> assertEquals("Putty", p.getName()),
			() -> assertEquals(2, p.getDependencies().size()),
			() -> assertEquals(true, p.isImportant())
		);
	}

	@Test
	void testProgramvaraNamnÄgareVersionLeveranör() {
		var p = new Programvara("Putty", "Leif B Nilsson", "0.81.0", "Open Source");
		
		assertAll("Konstruktor som tar samtliga element utom beroenden",
			() -> assertEquals("Putty", p.getName()),
			() -> assertEquals("Leif B Nilsson", p.getÄgare()),
			() -> assertEquals("0.81.0", p.getVersion()),
			() -> assertEquals("Open Source", p.getLeverantör()),
			() -> assertEquals(true, p.isImportant())
		);
	}

	@Test
	void testProgramvaraNamnÄgareVersionLeveranörBeroenden() {
		ArrayList<Dependency> deplist = new ArrayList<>();
		
		Programvara programvara2 = new Programvara();
		programvara2.setLeverantör("osninja");
		programvara2.setVersion("1.2.3");
		programvara2.setÄgare("Jonatan");
		programvara2.setName("baz");		
		
		deplist.add(programvara2.toDependency());
		deplist.add(new Dependency("pkg:golang/github.com/ProtonMail/proton-bridge@v1.6.3"));
		
		var p = new Programvara("Putty", "Leif B Nilsson", "0.81.0", "Open Source",deplist);
		
		assertAll("Konstruktor som tar samtliga element",
			() -> assertEquals("Putty", p.getName()),
			() -> assertEquals("Leif B Nilsson", p.getÄgare()),
			() -> assertEquals("0.81.0", p.getVersion()),
			() -> assertEquals("Open Source", p.getLeverantör()),
			() -> assertEquals(2, p.getDependencies().size()),
			() -> assertEquals(true, p.isImportant())
		);
	}

	@Test
	void testProgramvaraName() {
		var p = new Programvara("Putty");
		assertAll("Konstruktor med bara namn",
			() -> assertEquals("Putty", p.getName()),
			() -> assertEquals(true, p.isImportant())
		);
	}

	@Test
	void testGetÄgare() {
		var p = ProgramvaraFactory();
		assertEquals("Leif B Nilsson", p.getÄgare());
	}

	@Test
	void testSetÄgare() {
		var p = ProgramvaraFactory();
		p.setÄgare("Leif Andersson");
		assertEquals("Leif Andersson", p.getÄgare());
	}

	@Test
	void testGetVersion() {
		var p = ProgramvaraFactory();
		assertEquals("0.81.0", p.getVersion());
	}

	@Test
	void testSetVersion() {
		var p = ProgramvaraFactory();
		p.setVersion("2.3.1");
		assertEquals("2.3.1",p.getVersion());
	}

	@Test
	void testGetLeverantör() {
		var p = ProgramvaraFactory();
		assertEquals("Open Source", p.getLeverantör());
	}

	@Test
	void testSetLeverantör() {
		var p = ProgramvaraFactory();
		p.setLeverantör("Microsoft");
		assertEquals("Microsoft", p.getLeverantör());
	}

	@Test
	void testGetName() {
		var p = ProgramvaraFactory();
		assertEquals("Putty",p.getName());
	}
	
	@Test
	void testSetName() {
		var p = ProgramvaraFactory();
		p.setName("Yttup");
		assertEquals("Yttup", p.getName());
	}
}
