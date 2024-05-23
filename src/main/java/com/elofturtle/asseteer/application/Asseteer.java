package com.elofturtle.asseteer.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileOutputStream;
import com.elofturtle.asseteer.model.Asset;
import com.elofturtle.asseteer.model.Dependency;
import com.elofturtle.asseteer.model.Programvara;
import com.elofturtle.asseteer.model.SBOM;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.elofturtle.asseteer.exception.PEBKAC;
import com.elofturtle.asseteer.exception.WeirdFileException;
import com.elofturtle.asseteer.io.XmlUtil;




/**
 * Huvudprogrammet
 */
public class Asseteer {
	/**
	 * Ett bibliotek med Assets
	 */
	private ArrayList<Asset> library;
	/**
	 * Tabell för omvänd sökning
	 */
	private Map<String, Set<Dependency>> reverse_lookup;
	/**
	 * Här sparas programmets bibliotek mellan körningar
	 */
	private String globalStateFile;
	/**
	 * Skanner
	 */
	public Scanner scanner;
	
	/**
	 * Standardkonstruktor
	 * @param path sökväg till programkatalog
	 */
	public Asseteer(String path) {
		library = new ArrayList<>();
		globalStateFile = path;
		scanner = new Scanner(System.in);
		reverse_lookup = new HashMap<>();
		
	}
	
	private void updateReverseLookup() {
	    library.forEach(asset -> {
	        asset.getDependencies().forEach(dependency -> {
	            reverse_lookup.computeIfAbsent(dependency.toString(), k -> new HashSet<>()).add(new Dependency(asset));
	        });
	    });
	}
	
	/**
	 * Läs in programkatalogen, t.ex. vid uppstart.
	 * @throws WeirdFileException filundantag
	 */
	public void readState() throws WeirdFileException {
		Path filePath = Paths.get(globalStateFile);

        try {
            byte[] fileBytes = Files.readAllBytes(filePath);
            String fileContent = new String(fileBytes, StandardCharsets.UTF_8);
            library = XmlUtil.deserialize(fileContent);
            updateReverseLookup();
            
            
        } catch (IOException e) {
            // Handle IOException
            System.err.println("An error occurred while reading the file: " + e.getMessage());
            e.printStackTrace();
            throw new WeirdFileException("Unable to load application state", e);
        } catch (SecurityException e) {
            // Handle SecurityException
            System.err.println("A security error occurred: " + e.getMessage());
            System.err.println("Program will exit!");
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            System.err.println("An unexpected error occurred: " + e.getMessage());
            System.err.println("Program will exit!");
            e.printStackTrace();
            System.exit(1);
        }
	}
	
	/**
	 * Returns a string representation of the reverse lookup map.
	 * If a search term is provided, only keys containing the search term will be included.
	 *
	 * @param searchTerm The term to filter keys by. If null or empty, all keys will be included.
	 * @return A string representation of the reverse lookup map, or an empty string if no values are found.
	 */
	public String getReverseLookupAsString(String searchTerm) {
	    StringBuilder output = new StringBuilder();
	    reverse_lookup.forEach((key, dependencies) -> {
	        if (searchTerm == null || key.contains(searchTerm)) {
	            output.append("Key: ").append(key).append("\n");
	            dependencies.forEach(dependency -> output.append("\tDependency: ").append(dependency).append("\n"));
	        }
	    });
	    return output.length() > 0 ? output.toString() : "";
	}

	/**
	 * Returns a string representation of the reverse lookup map.
	 * All keys and their corresponding dependencies will be included.
	 *
	 * @return A string representation of the reverse lookup map, or an empty string if no values are found.
	 */
	public String getReverseLookupAsString() {
	    return getReverseLookupAsString(null);
	}


	
	/**
	 * Spara programkatalogen, t.ex. när den har ändrats
	 * @throws WeirdFileException filundantag
	 */
	public void saveState() throws WeirdFileException {
		try {
			String s = XmlUtil.serialize(library);
			System.out.println(s);
			try (FileOutputStream fos = new FileOutputStream(globalStateFile);
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"))) {
				writer.write(s);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.exit(1);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			System.err.println("Malformed document");
			throw new WeirdFileException("Because of an issue, saving application state was not possible.", e);
		} 
	}

	/**
	 * Huvudprogram
	 * @param args indata
	 * @throws PEBKAC användarfelsfel
	 */
	public static void main(String[] args) throws PEBKAC {
		Asseteer a = new Asseteer(System.getProperty("user.home") + "/asseteer.xml");
		try {
			a.readState();
		} catch (WeirdFileException e) {
			e.printStackTrace();
			System.err.println("Corrupt file, start from scratch");
			a.library = new ArrayList<>();
		}
		
		while(true) {
		        MENU_STATE choice = MenuHelpers.menuOptionHelper(MENU_STATE.values(), "### MAIN MENU ###", a.scanner); 
		        System.out.println("You selected: \"" + choice + "\"");
		        
		        switch (choice) {
			        case ADD_ASSET:
			        	a.MenuOptionAddAsset();
			        	break;
					case EDIT_ASSET:
						a.MenuOptionEditAsset();
						break;
					case LIST_ASSETS:
						a.MenuOptionListAssets();
						break;
					case REMOVE_ASSET:
						a.MenuOptionRemoveAsset();
						break;
					case SEARCH_ASSET:
						a.MenuOptionSearchAsset();
						break;
					case IMPORTERA_TILLGÅNGAR:
						System.out.println("Inte implementerat meny ännu");
						break;
					case INVERTED_SEARCH:
						System.out.println("Search term: ");
						String searchString = a.scanner.nextLine();
						String s = a.getReverseLookupAsString(searchString);
						if(s.equals(""))
							System.out.println("No results found for the search term: " + searchString);
						else
							System.out.println(s);
						/*
						 * var matchingEntries = a.reverse_lookup.entrySet().stream() .filter(entry ->
						 * entry.getKey().contains(searchString)) .toList();
						 * 
						 * if (matchingEntries.isEmpty()) {
						 * System.out.println("No results found for the search term: " + searchString);
						 * } else { matchingEntries.forEach(entry -> { System.out.println("Key: " +
						 * entry.getKey()); entry.getValue().forEach(dependency ->
						 * System.out.println("  Dependency: " + dependency.toString())); }); }
						 */
						break;
					case QUIT:
						System.out.println("Exiting...");
						System.exit(0);
						break;
					default:
						throw new PEBKAC("Inmatningsfel");
		        }  	
		}
    }

	private void MenuOptionListAssets() {
		for(var item : library) {
			System.out.println(item.toRepresentation());
		}
		
	}

	private void MenuOptionRemoveAsset() {
		System.out.println("Enter the name of the asset to remove:");
		String name = scanner.nextLine();
		Asset assetToRemove = null;
		for (Asset asset : library) {
			if (asset.getName().equals(name)) {
				assetToRemove = asset;
				break;
			}
		}
		if (assetToRemove != null) {
			library.remove(assetToRemove);
			System.out.println("Asset removed.");
			try {
				saveState();
			} catch (WeirdFileException e) {
				e.printStackTrace();
				System.err.println("Program will halt");
				System.exit(1);
			}
		} else {
			System.out.println("Asset not found.");
		}
	}


	private void MenuOptionSearchAsset() {
		System.out.println("Enter a part of the asset to search:");
		String s = scanner.nextLine();
		boolean foundAnything = false;
		for (Asset asset : library) {
			if (asset.getId().contains(s)) {
				foundAnything = true;
				System.out.println(asset.toRepresentation());
				continue;
			}
		}
		if(!foundAnything) {
			System.out.println("Asset not found.");
		}
	}

	private void MenuOptionEditAsset() {
		System.out.println("Enter the name of the asset to edit:");
		String name = scanner.nextLine();
		Asset assetToEdit = null;
		for (Asset asset : library) {
			if (asset.getName().equals(name)) {
				assetToEdit = asset;
				break;
			}
		}
		if (assetToEdit != null) {
			System.out.println("Found Asset\n" + assetToEdit.toRepresentation());
			
			System.out.println("Enter new name (or press Enter to keep current name):");
			String newName = scanner.nextLine();
			if (!newName.isEmpty()) {
				assetToEdit.setName(newName);
			}

			if (assetToEdit instanceof SBOM) {
	            SBOM sbom = (SBOM) assetToEdit;

	            System.out.println("Enter new package name (or press Enter to keep current package name):");
	            String newPackageName = scanner.nextLine();

	            System.out.println("Enter new version (or press Enter to keep current version):");
	            String newVersion = scanner.nextLine();

	            if (!newPackageName.isEmpty() || !newName.isEmpty() || !newVersion.isEmpty()) {
	                String packageName = !newPackageName.isEmpty() ? newPackageName : sbom.getId().split(":")[1].split("/")[0];
	                String nameToUse = !newName.isEmpty() ? newName : sbom.getName();
	                String versionToUse = !newVersion.isEmpty() ? newVersion : sbom.getId().split("@")[1];

	                sbom.setId("pkg:" + packageName + "/" + nameToUse + "@" + versionToUse);
	                
	                System.out.println("Is this a primary application? (y/n)");
	                String importantResponse = scanner.nextLine().trim().toLowerCase();
	                boolean important = importantResponse.equals("y") || importantResponse.equals("yes");
	                sbom.setImportant(important);
	            }
			}
	            
			else if (assetToEdit instanceof Programvara) {
				Programvara programvara = (Programvara) assetToEdit;
				System.out.println("Enter new owner (or press Enter to keep current owner):");
				String newOwner = scanner.nextLine();
				if (!newOwner.isEmpty()) {
					programvara.setÄgare(newOwner);
				}

				System.out.println("Enter new version (or press Enter to keep current version):");
				String programvaraNewVersion = scanner.nextLine();
				if (!programvaraNewVersion.isEmpty()) {
					programvara.setVersion(programvaraNewVersion);
				}

				System.out.println("Enter new supplier (or press Enter to keep current supplier):");
				String newSupplier = scanner.nextLine();
				if (!newSupplier.isEmpty()) {
					programvara.setLeverantör(newSupplier);
				}
			}

			System.out.println("Asset updated.");
			try {
				saveState();
			} catch (WeirdFileException e) {
				e.printStackTrace();
				System.exit(1);
			}
		} else {
			System.out.println("Asset not found.");
		}
	}

	private void MenuOptionAddAsset() throws PEBKAC {
		System.out.println("Add Asset");

		var assetType = MenuHelpers.menuOptionHelper(ASSET_TYPES.values(), "Add asset of which type?", scanner);
		switch(assetType) {
		case PROGRAMVARA:
			Programvara programvara = new Programvara();
			
			System.out.println("Name:");
			String programName = scanner.nextLine();
			programvara.setName(programName);
			
			System.out.println("Owner:");
			String programOwner = scanner.nextLine();
			programvara.setÄgare(programOwner);
			
			System.out.println("Version:");
			String programVersion = scanner.nextLine();
			programvara.setVersion(programVersion);
			
			System.out.println("Supplier:");
			String programSupplier = scanner.nextLine();
			programvara.setLeverantör(programSupplier);
			
			addDependencyMenuAction(programvara);
						
			library.add(programvara);
			try {
				saveState();
			} catch (WeirdFileException e) {
				e.printStackTrace();
				System.exit(1);
			}
			break;
		case SBOM:
			SBOM sbom = new SBOM();			
			System.out.println("Package:");
			String packageName = scanner.nextLine();
			
			System.out.println("Name:");
			
			String name = scanner.nextLine();
			
			System.out.println("Version:");
			String version = scanner.nextLine();
			
			System.out.println("Is this a primary application?");
			boolean important = scanner.nextBoolean();
			
			sbom.setName(name);
			sbom.setImportant(important);
			sbom.setId("pkg:" + packageName + "/" + name + "@" + version);
			
			addDependencyMenuAction(sbom);
			
			library.add(sbom);
			try {
				saveState();
			} catch (WeirdFileException e) {
				e.printStackTrace();
				System.exit(1);
			}
			break;
			
		case CANCEL:
			return;
		}
	}

	/**
	 * Helper method
	 * @param asset asset to add
	 */
	private void addDependencyMenuAction(Asset asset) {
		System.out.println("Would you like to add a dependency? (y/n)");
		String addDependencyResponse = scanner.nextLine().trim().toLowerCase();
		boolean addDependency = addDependencyResponse.equals("y") || addDependencyResponse.equals("yes");
		
		while(addDependency) {
			System.out.println("Enter id of dependency:");
			String newdep = scanner.nextLine();
			System.out.println("New Dep: '" + newdep + "'");
			Dependency dependency = new Dependency(newdep);
			asset.addDependency(dependency);
			
			System.out.println("Would you like to add a dependency? (y/n)");
			addDependencyResponse = scanner.nextLine().trim().toLowerCase();
			addDependency = addDependencyResponse.equals("y") || addDependencyResponse.equals("yes");
		}
		
		updateReverseLookup();
	}
	
	
	
	/**
	 * Standardkonstruktor
	 */
	public Asseteer() {
		library = new ArrayList<Asset>();
		reverse_lookup = new HashMap<>();
		scanner = new Scanner(System.in);
	}

	}
