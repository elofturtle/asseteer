package com.elofturtle.asseteer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.elofturtle.asseteer.model.Dependency;
import com.elofturtle.asseteer.model.Programvara;

//GPT verkar genomgående ha problem med versaler i enhetstesterna, som ofta måste korrigeras i efterhand.
class TestProgramvara_GPT {

    private Programvara programvara;

    @BeforeEach
    void setUp() {
        programvara = new Programvara("testProgramvara", "John Doe", "1.0.0", "TestCorp");
    }

    @Test
    void testConstructorWithName() {
        Programvara programvaraWithName = new Programvara("TestApp");
        assertEquals("TestApp", programvaraWithName.getName(), "Name should be set correctly");
        assertTrue(programvaraWithName.isImportant(), "Programvara should be marked as important by default");
    }

    @Test
    void testConstructorWithFullDetails() {
        assertEquals("testProgramvara", programvara.getName(), "Name should be set correctly");
        assertEquals("John Doe", programvara.getÄgare(), "Owner should be set correctly");
        assertEquals("1.0.0", programvara.getVersion(), "Version should be set correctly");
        assertEquals("TestCorp", programvara.getLeverantör(), "Supplier should be set correctly");
        assertTrue(programvara.isImportant(), "Programvara should be marked as important by default");
    }

    @Test
    void testUpdateIdOnNameChange() {
        programvara.setName("NewTestApp");
        assertEquals("Programvara:testcorp_newtestapp_1.0.0", programvara.getId(), "ID should be updated correctly on name change");
    }

    @Test
    void testUpdateIdOnOwnerChange() {
        programvara.setÄgare("Jane Doe");
        assertEquals("Programvara:testcorp_testprogramvara_1.0.0", programvara.getId(), "ID should not change when owner is updated");
    }

    @Test
    void testUpdateIdOnVersionChange() {
        programvara.setVersion("2.0.0");
        assertEquals("Programvara:testcorp_testprogramvara_2.0.0", programvara.getId(), "ID should be updated correctly on version change");
    }

    @Test
    void testUpdateIdOnSupplierChange() {
        programvara.setLeverantör("NewCorp");
        assertEquals("Programvara:newcorp_testprogramvara_1.0.0", programvara.getId(), "ID should be updated correctly on supplier change");
    }

    @Test
    void testSetDependencies() {
        Dependency dep1 = new Dependency("dep1");
        Dependency dep2 = new Dependency("dep2");
        ArrayList<Dependency> dependencies = new ArrayList<>();
        dependencies.add(dep1);
        dependencies.add(dep2);

        programvara.setDependencies(dependencies);
        assertEquals(2, programvara.getDependencies().size(), "Dependencies should be set correctly");
    }

    @Test
    void testAddDependency() {
        Dependency dep = new Dependency("newDep");
        programvara.addDependency(dep);
        assertEquals(1, programvara.getDependencies().size(), "Should add one dependency");
        assertEquals(dep, programvara.getDependencies().get(0), "Added dependency should match");
    }

    @Test
    void testRemoveDependency() {
        Dependency dep = new Dependency("newDep");
        programvara.addDependency(dep);
        programvara.removeDependency(dep);
        assertTrue(programvara.getDependencies().isEmpty(), "Should remove the dependency");
    }

    @Test
    void testToRepresentationWithDependencies() {
        Dependency dep1 = new Dependency("dep1");
        Dependency dep2 = new Dependency("dep2");
        programvara.addDependency(dep1);
        programvara.addDependency(dep2);

        String representation = programvara.toRepresentation();
        assertTrue(representation.contains("testProgramvara : Programvara:testcorp_testprogramvara_1.0.0"), "Representation should include programvara details");
        assertTrue(representation.contains("dep1"), "Representation should include dependency dep1");
        assertTrue(representation.contains("dep2"), "Representation should include dependency dep2");
    }

    @Test
    void testToRepresentationWithoutDependencies() {
        String representation = programvara.toRepresentation();
        assertEquals("testProgramvara : Programvara:testcorp_testprogramvara_1.0.0", representation.trim(), "Representation should be correct without dependencies");
    }

    @Test
    void testIsImportant() {
        programvara.setImportant(false);
        assertFalse(programvara.isImportant(), "Programvara should not be important");
    }

    @Test
    void testHashCodeAndEquals() {
        Programvara anotherProgramvara = new Programvara("testProgramvara", "John Doe", "1.0.0", "TestCorp");
        assertEquals(programvara, anotherProgramvara, "Programvara objects with the same properties should be equal");
        assertEquals(programvara.hashCode(), anotherProgramvara.hashCode(), "Hash codes should match for equal Programvara objects");
    }

    @Test
    void testCompareTo() {
        Programvara anotherProgramvara = new Programvara("anotherApp", "Jane Doe", "2.0.0", "AnotherCorp");
        assertTrue(programvara.compareTo(anotherProgramvara) > 0, "programvara should be greater than anotherProgramvara based on ID");
        assertTrue(anotherProgramvara.compareTo(programvara) < 0, "anotherProgramvara should be less than programvara based on ID");
    }
}
