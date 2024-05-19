package com.elofturtle.asseteer.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.elofturtle.asseteer.model.Asset;
import com.elofturtle.asseteer.model.Dependency;
import com.elofturtle.asseteer.model.Programvara;
import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;

public class ProgramvaraFileReader extends AssetFileReader {

	@Override
	public ArrayList<Asset> readFromFile(String filePath) {
		ArrayList<Asset> programvaror = new ArrayList<>();
        XML xml;
		try {
			xml = new XMLDocument(new File(filePath));
	        List<XML> nodes = xml.nodes("//programvara");
	        for (XML node : nodes) {
	            Programvara programvara = new Programvara();
	            
	            programvara.setName(node.xpath("namn/text()").get(0));
	            programvara.setLeverantör(node.xpath("leverantör/text()").get(0));
	            programvara.setÄgare(node.xpath("ägare/text()").get(0));
	            programvara.setVersion(node.xpath("version/text()").get(0));
	            
	            List<String> dependencies = node.xpath("beroenden/beroende/text()");
	            for (String dep : dependencies) {
	                Dependency dependency = new Dependency(dep);
	                programvara.addDependency(dependency);
	            }	            
	            
	            programvaror.add(programvara);
	        }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}

        return programvaror;
	}
}
