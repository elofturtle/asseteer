package com.elofturtle.asseteer.io;

import org.cyclonedx.parsers.BomParserFactory;
import org.cyclonedx.exception.ParseException;
import com.elofturtle.asseteer.model.Asset;
import com.elofturtle.asseteer.model.Dependency;
import com.elofturtle.asseteer.model.SBOM;
import java.io.File;
import java.util.ArrayList;

/**
 * Subclasses of Asset reading from a standard format may choose to implement an importer class.
 * This is a parser for the CycloneDX format.
 */
public class SBOMFileReader extends AssetFileReader { 
/**
 * Converts selected parts of a cycloneDX SBOM XML file into SBOM:s.
 * @param fp Path to the XML file.
 * @return A list of SBOM Assets.
 */
@Override
public ArrayList<Asset> readFromFile(String fp) {	
	/**
	 * Path to an XML file.
	 */
	File file = new File(fp);
	
	/**
	 * The SBOM list is returned at the end.
	 */
	ArrayList<Asset> sboms = new ArrayList<Asset>();
	
	/**
	 * Before being added to the SBOm list, construct it using this temporary holder.
	 */
	SBOM sbom;
	
	/**
	 * A dummy variable to get the correct id from the SBOM constructor 
	 * (instead of hard-coding it for the Dependency):
	 * Xml-bom-ref --> Asset-bom-ref --> Dependency-id.
	 */
	SBOM dummy;
	
	
	if(!file.exists()) {
		System.out.println("File not found");
		System.exit(1);
		return null;
	}
	
	 try {
		 org.cyclonedx.parsers.Parser parser =  BomParserFactory.createParser(file);
		 org.cyclonedx.model.Bom bom = parser.parse(file);
		 var foo = bom.getDependencies();
		 
		 //Metadata for top level component requires special handling
		 sbom = new SBOM();
		 sbom.setName(bom.getMetadata().getComponent().getName());
		 String rawId = bom.getMetadata().getComponent().getPurl();
		 sbom.setId(rawId);
		 sbom.setImportant(true);
		 ArrayList<Dependency> dependencies = new ArrayList<Dependency>();
		 // The XML has a separate listing of dependencies, so we map each entry into a list and add that.
		 for(org.cyclonedx.model.Dependency d : foo) {
			 if(rawId.equals(d.getRef())) {
				 for(var dd : d.getDependencies()) {
					 //	"SBOM:pkg:abcd@1234, because of differing id naming strategies, 
					 //			create dummy instance that gets correct id from constructor."
					 dummy = new SBOM();
					 dummy.setId(dd.getRef());
					 dependencies.add(new Dependency(dummy));
				 }
			 }
		 }
		 sbom.setDependencies(dependencies);
		 sboms.add(sbom);
		 
		 // Iterate over every component and add them as well
		 for(var component : bom.getComponents()) {
			 sbom = new SBOM();
			 rawId = component.getPurl();
			 sbom.setId(rawId);
			 sbom.setName(component.getName());
			 dependencies = new ArrayList<Dependency>();
			 for(org.cyclonedx.model.Dependency d : foo) {
				 if(rawId.equals(d.getRef())){
					 if(d.getDependencies() == null)
						 continue;
					 for(var dd : d.getDependencies()) {
						 dummy = new SBOM();
						 dummy.setId(dd.getRef());
						 dependencies.add(new Dependency(dummy));
					 }
				 }
			 }
			 sbom.setDependencies(dependencies);
			 sboms.add(sbom);
		 }
	} catch (ParseException e) {
		e.printStackTrace();
	}
	return sboms;
} 
}
