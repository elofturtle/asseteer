package com.elofturtle.asseteer.io;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import com.elofturtle.asseteer.model.Asset;
import com.elofturtle.asseteer.model.SBOM;
import com.elofturtle.asseteer.model.Programvara;
import com.fasterxml.jackson.core.JsonProcessingException;

class XmlUtilTest {

	@Test
	void testSerialize() {
        try {
		            // Create SBOM instances
		            SBOM sbom1 = new SBOM("c", "pkg:ab/c@1");
		            SBOM sbom2 = new SBOM("d", "pkg:ab/d@3");

		            // Create Programvara instance
		            Programvara programvara = new Programvara("Putty", "Leif B Nilsson", "0.81.0", "Open Source");

		            // Add instances to the list
		            ArrayList<Asset> library = new ArrayList<>();
		            library.add(sbom1);
		            library.add(sbom2);
		            library.add(programvara);

		            // Serialize to XML
		            String xml = XmlUtil.serialize(library);
		            
		            String expectedXML = "<?xml version='1.0' encoding='UTF-8'?>\n" +
		                    "<ArrayList>\n" +
		                    "  <item>\n" +
		                    "    <assetType>SBOM</assetType>\n" +
		                    "    <name>c</name>\n" +
		                    "    <id>SBOM:pkg:ab/c@1</id>\n" +
		                    "    <important>false</important>\n" +
		                    "  </item>\n" +
		                    "  <item>\n" +
		                    "    <assetType>SBOM</assetType>\n" +
		                    "    <name>d</name>\n" +
		                    "    <id>SBOM:pkg:ab/d@3</id>\n" +
		                    "    <important>false</important>\n" +
		                    "  </item>\n" +
		                    "  <item>\n" +
		                    "    <assetType>Programvara</assetType>\n" +
		                    "    <name>Putty</name>\n" +
		                    "    <id>Programvara:open_source_putty_0.81.0</id>\n" +
		                    "    <important>true</important>\n" +
		                    "    <ägare>Leif B Nilsson</ägare>\n" +
		                    "    <version>0.81.0</version>\n" +
		                    "    <leverantör>Open Source</leverantör>\n" +
		                    "  </item>\n" +
		                    "</ArrayList>" +
		                    "";
		            System.out.println(xml);

		            // Compare the XML output with the expected XML
		            assertEquals(expectedXML.replaceAll("\\s+", ""), xml.replaceAll("\\s+", ""));
		        } catch (JsonProcessingException e) {
		            e.printStackTrace();
		        }
	}

    @Test
    public void testXmlDeserialization() throws JsonProcessingException {
        // XML string to be deserialized
        String xml = "<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<ArrayList>\n" +
                "  <item>\n" +
                "    <assetType>SBOM</assetType>\n" +
                "    <name>c</name>\n" +
                "    <id>SBOM:pkg:ab/c@1</id>\n" +
                "    <important>false</important>\n" +
                "  </item>\n" +
                "  <item>\n" +
                "    <assetType>SBOM</assetType>\n" +
                "    <name>d</name>\n" +
                "    <id>SBOM:pkg:ab/d@3</id>\n" +
                "    <important>false</important>\n" +
                "  </item>\n" +
                "  <item>\n" +
                "    <assetType>Programvara</assetType>\n" +
                "    <name>Putty</name>\n" +
                "    <id>Programvara:open_source_putty_0.81.0</id>\n" +
                "    <important>true</important>\n" +
                "    <ägare>Leif B Nilsson</ägare>\n" +
                "    <version>0.81.0</version>\n" +
                "    <leverantör>Open Source</leverantör>\n" +
                "  </item>\n" +
                "</ArrayList>";

        // Deserialize XML to ArrayList<Asset>
        ArrayList<Asset> assets = XmlUtil.deserialize(xml);

        // Print the deserialized objects using toRepresentation for detailed view
        assets.forEach(asset -> System.out.println(asset.toRepresentation()));

        // Assertions to verify deserialization
        assertEquals(3, assets.size());

        // Verify first SBOM asset
        assertTrue(assets.get(0) instanceof SBOM);
        assertEquals("c", assets.get(0).getName());
        assertEquals("SBOM:pkg:ab/c@1", assets.get(0).getId());
        assertFalse(assets.get(0).isImportant());

        // Verify second SBOM asset
        assertTrue(assets.get(1) instanceof SBOM);
        assertEquals("d", assets.get(1).getName());
        assertEquals("SBOM:pkg:ab/d@3", assets.get(1).getId());
        assertFalse(assets.get(1).isImportant());

        // Verify Programvara asset
        assertTrue(assets.get(2) instanceof Programvara);
        assertEquals("Putty", assets.get(2).getName());
        assertEquals("Programvara:open_source_putty_0.81.0", assets.get(2).getId());
        assertTrue(assets.get(2).isImportant());
        Programvara programvara = (Programvara) assets.get(2);
        assertEquals("Leif B Nilsson", programvara.getÄgare());
        assertEquals("0.81.0", programvara.getVersion());
        assertEquals("Open Source", programvara.getLeverantör());
    }
}
