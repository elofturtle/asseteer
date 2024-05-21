package com.elofturtle.asseteer;

import org.junit.jupiter.api.Test;

import com.elofturtle.asseteer.model.Asset;
import com.elofturtle.asseteer.model.Dependency;
import com.elofturtle.asseteer.model.SBOM;

import static org.junit.jupiter.api.Assertions.*;

// Kastade in min klass till GPT och bad om enhetstester, med minimal ändring så lyckades den generera nedan resultat.
// Testerna är bättre än mina handskrivna i vissa fall :)
// Det hade varit intressant att peka ut ett git-repo och se vad resultatet av ett mer övergripande kravdokument med användarfall blir.


class TestDependency {

    @Test
    void testDefaultConstructor() {
        Dependency dependency = new Dependency();
        assertEquals("", dependency.getId());
    }

    @Test
    void testConstructorWithId() {
        String id = "12345";
        Dependency dependency = new Dependency(id);
        assertEquals(id, dependency.getId());
    }

    @Test
    void testConstructorWithAsset() {
		Asset asset = new SBOM("github.com/ProtonMail/proton-bridge", "pkg:golang/github.com/ProtonMail/proton-bridge@v1.6.3");
        asset.setId("asset123");
        Dependency dependency = new Dependency(asset);
        // Det här är på grund av ett dåligt tidigt designbeslut, men jag kommer inte att ändra det inom ramen för kursen.
        assertEquals( asset.getClass().getSimpleName() + ":asset123", dependency.getId()); //Justerad pga hur Asset muterar ID.
    }

    @Test
    void testSetId() {
        Dependency dependency = new Dependency();
        dependency.setId("newId");
        assertEquals("newId", dependency.getId());
    }

    @Test
    void testSetIdWithNull() {
        Dependency dependency = new Dependency();
        dependency.setId(null);
        assertEquals("", dependency.getId());
    }

    @Test
    void testToString() {
        String id = "12345";
        Dependency dependency = new Dependency(id);
        assertEquals(id, dependency.toString());
    }

    @Test
    void testHashCode() {
        String id = "12345";
        Dependency dependency1 = new Dependency(id);
        Dependency dependency2 = new Dependency(id);
        assertEquals(dependency1.hashCode(), dependency2.hashCode());
    }

    @Test
    void testEquals() {
        String id = "12345";
        Dependency dependency1 = new Dependency(id);
        Dependency dependency2 = new Dependency(id);
        Dependency dependency3 = new Dependency("67890");
        assertEquals(dependency1, dependency1); //den här missade chatpgt för 100% testtäckning
        assertEquals(dependency1, dependency2);
        assertNotEquals(dependency1, dependency3);
    }

    @Test
    void testEqualsWithNull() {
        Dependency dependency = new Dependency("12345");
        assertNotEquals(dependency, null);
    }

    @Test
    void testEqualsWithDifferentClass() {
        Dependency dependency = new Dependency("12345");
        assertNotEquals(dependency, new Object());
    }

    @Test
    void testCompareTo() {
        Dependency dependency1 = new Dependency("12345");
        Dependency dependency2 = new Dependency("67890");
        Dependency dependency3 = new Dependency("12345");
        assertTrue(dependency1.compareTo(dependency2) < 0);
        assertTrue(dependency2.compareTo(dependency1) > 0);
        assertEquals(0, dependency1.compareTo(dependency3));
    }
}
