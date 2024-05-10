package com.elofturtle.asseteer.io;

import org.cyclonedx.BomParserFactory;
import org.cyclonedx.exception.ParseException;
import org.cyclonedx.model.Component;
import org.cyclonedx.parsers.Parser;

import java.io.File;
//TODO: mappa fil till objekt och returnera.
public class SBOMFileReader {
	
 public static void main(String[] args) {
	 File file = new File("proton-bridge-v1.6.3.bom.xml");
	 try {
		 org.cyclonedx.parsers.Parser parser =  BomParserFactory.createParser(file);
		 org.cyclonedx.model.Bom bom = parser.parse(file);
		 System.out.println(bom.getMetadata().getComponent().getName() + " : " + bom.getMetadata().getComponent().getType());
		 for (var component : bom.getComponents() ) {
			 System.out.println(component.getPurl());
			 if(component.getType() == Component.Type.LIBRARY) {
				 System.out.println(component.getName() + " : " + component.getType());
			 }else {
				 System.out.println(component.getName());
			 }
		 }
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	 
 } 
}
