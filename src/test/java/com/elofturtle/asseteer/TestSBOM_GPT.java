package com.elofturtle.asseteer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.elofturtle.asseteer.model.Dependency;
import com.elofturtle.asseteer.model.SBOM;

class TestSBOM_GPT {

    private SBOM sbom1;
    private SBOM sbom2;
    private SBOM sbom3;

    @BeforeEach
    void setUp() {
        sbom1 = new SBOM("testSBOM1", "pkg:example/testSBOM1@1.0.0");
        sbom2 = new SBOM("testSBOM2", "pkg:example/testSBOM2@1.0.0");
        sbom3 = new SBOM("testSBOM3", "pkg:example/testSBOM3@1.0.0");
    }

    @Test
    void testAddDependencyToSBOM() {
        sbom1.addDependency(sbom2);
        assertEquals(1, sbom1.getDependencies().size(), "Should have one dependency");
        assertEquals(sbom2.toDependency(), sbom1.getDependencies().get(0), "Dependency should match sbom2");
    }

    @Test
    void testRemoveDependencyFromSBOM() {
        sbom1.addDependency(sbom2);
        sbom1.removeDependency(sbom2);
        assertTrue(sbom1.getDependencies().isEmpty(), "Should remove the dependency");
    }

    @Test
    void testAddDuplicateDependencyToSBOM() {
        sbom1.addDependency(sbom2);
        sbom1.addDependency(sbom2);
        assertEquals(1, sbom1.getDependencies().size(), "Should not add duplicate dependencies");
    }

    @Test
    void testSetDependenciesInSBOM() {
        ArrayList<Dependency> dependencies = new ArrayList<>();
        dependencies.add(sbom2.toDependency());
        dependencies.add(sbom3.toDependency());

        sbom1.setDependencies(dependencies);
        assertEquals(2, sbom1.getDependencies().size(), "Should set dependencies correctly");
    }

    @Test
    void testSBOMToRepresentationWithDependencies() {
        sbom1.addDependency(sbom2);
        sbom1.addDependency(sbom3);
        String representation = sbom1.toRepresentation();
        assertTrue(representation.contains("testSBOM1 : SBOM:pkg:example/testSBOM1@1.0.0"), "Representation should include sbom1 details");
        assertTrue(representation.contains("testSBOM2"), "Representation should include dependency sbom2");
        assertTrue(representation.contains("testSBOM3"), "Representation should include dependency sbom3");
    }

    @Test
    void testSBOMToRepresentationWithoutDependencies() {
        String representation = sbom1.toRepresentation();
        assertEquals("testSBOM1 : SBOM:pkg:example/testSBOM1@1.0.0", representation.trim(), "Representation should be correct without dependencies");
    }

    @Test
    void testSBOMIsImportant() {
        sbom1.setImportant(true);
        assertTrue(sbom1.isImportant(), "SBOM should be marked as important");
    }

    @Test
    void testSBOMCompareTo() {
        SBOM anotherSBOM = new SBOM("anotherSBOM", "pkg:example/anotherSBOM@1.0.0");
        assertTrue(sbom1.compareTo(anotherSBOM) > 0, "sbom1 should be greater than anotherSBOM based on ID");
        assertTrue(anotherSBOM.compareTo(sbom1) < 0, "anotherSBOM should be less than sbom1 based on ID");
    }

    @Test
    void testSBOMEqualsAndHashCode() {
        SBOM sameAsSBOM1 = new SBOM("testSBOM1", "pkg:example/testSBOM1@1.0.0");
        assertEquals(sbom1, sameAsSBOM1, "SBOMs with the same ID should be equal");
        assertEquals(sbom1.hashCode(), sameAsSBOM1.hashCode(), "Hash codes should match for equal SBOMs");
    }

    @Test
    void testSBOMAddDependency() {
        sbom1.addDependency(sbom2);
        assertEquals(1, sbom1.getDependencies().size(), "SBOM should have one dependency");
        assertEquals(sbom2.toDependency(), sbom1.getDependencies().get(0), "Dependency should match sbom2");
    }

    @Test
    void testSBOMRemoveDependency() {
        sbom1.addDependency(sbom2);
        sbom1.removeDependency(sbom2);
        assertTrue(sbom1.getDependencies().isEmpty(), "SBOM should remove the dependency");
    }

    @Test
    void testSBOMSetDependencies() {
        ArrayList<Dependency> dependencies = new ArrayList<>();
        dependencies.add(sbom2.toDependency());
        dependencies.add(sbom3.toDependency());

        sbom1.setDependencies(dependencies);
        assertEquals(2, sbom1.getDependencies().size(), "SBOM should set dependencies correctly");
    }

    @Test
    void testSBOMRepresentationWithDependencies() {
        sbom1.addDependency(sbom2);
        sbom1.addDependency(sbom3);
        String representation = sbom1.toRepresentation();
        assertTrue(representation.contains("testSBOM1 : SBOM:pkg:example/testSBOM1@1.0.0"), "Representation should include SBOM details");
        assertTrue(representation.contains("testSBOM2"), "Representation should include dependency sbom2");
        assertTrue(representation.contains("testSBOM3"), "Representation should include dependency sbom3");
    }

    @Test
    void testSBOMRepresentationWithoutDependencies() {
        String representation = sbom1.toRepresentation();
        assertEquals("testSBOM1 : SBOM:pkg:example/testSBOM1@1.0.0", representation.trim(), "Representation should be correct without dependencies");
    }
}
