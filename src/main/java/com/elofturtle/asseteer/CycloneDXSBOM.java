package com.elofturtle.asseteer;

import org.cyclonedx.BomParserFactory;
import org.cyclonedx.exception.ParseException;
import org.cyclonedx.parsers.Parser;
import java.io.File;

public class CycloneDXSBOM {
	
 public static void main(String[] args) {
	 File file = new File("proton-bridge-v1.6.3.bom.xml");
	 try {
		 org.cyclonedx.parsers.Parser parser =  BomParserFactory.createParser(file);
		 org.cyclonedx.model.Bom bom = parser.parse(file);
		 for (var component : bom.getComponents() )
		 System.out.println(component.getName());

	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 } 
}
