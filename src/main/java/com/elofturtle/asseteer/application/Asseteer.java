package com.elofturtle.asseteer.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

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
import com.elofturtle.asseteer.io.XmlUtil;

public class Asseteer {
	private ArrayList<Asset> library;
	private Map<String, List<Dependency>> reverse_lookup;
	private String globalStateFile;
	public Scanner scanner;
	
	public Asseteer(String path) {
		library = new ArrayList<>();
		globalStateFile = path;
		scanner = new Scanner(System.in);
		reverse_lookup = new HashMap<>();
	}
	
	public void readState() {
		Path filePath = Paths.get(globalStateFile);

        try {
            byte[] fileBytes = Files.readAllBytes(filePath);
            String fileContent = new String(fileBytes, StandardCharsets.UTF_8);
            library = XmlUtil.deserialize(fileContent);
        } catch (IOException e) {
            // Handle IOException
            System.err.println("An error occurred while reading the file: " + e.getMessage());
            e.printStackTrace();
        } catch (SecurityException e) {
            // Handle SecurityException
            System.err.println("A security error occurred: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
		
	}
	
	public void saveState() {
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
			System.out.println("Malformed document");
			System.exit(1);
		} 
	}

	enum MENU_STATE {
		ADD_ASSET,
		EDIT_ASSET,
		REMOVE_ASSET,
		LIST_ASSETS,
		SEARCH_ASSET,
		QUIT;

		@Override
		public String toString() {
			return menuRepresentation(this);
	}
}

	public static void main(String[] args) {
		Asseteer a = new Asseteer(System.getProperty("user.home") + "/asseteer.xml");
		a.readState();
		
		Queue<String> opts = new LinkedList<>();
		for(var item : args) {
			opts.add(item);
		}
		while(!opts.isEmpty()) {
			String item = opts.remove();
			switch(item) {
			case "-f":
			case "--file":
				break;
			}
		}
		while(true) {
		        MENU_STATE choice = menuOptionHelper(MENU_STATE.values(), "### MENU ###", a.scanner); 
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
					case QUIT:
					default:
						System.out.println("Exiting...");
						System.exit(0);
						break;
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
			saveState();
		} else {
			System.out.println("Asset not found.");
		}
	}


	private void MenuOptionSearchAsset() {
		//TODO try out streams here instead!
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

			// Additional fields can be added here as needed

			System.out.println("Asset updated.");
			saveState();
		} else {
			System.out.println("Asset not found.");
		}
	}

	private void MenuOptionAddAsset() {
		System.out.println("Add Asset");
		enum ASSET_TYPES{
			PROGRAMVARA, 
			SBOM,
			CANCEL
		}
		var assetType = menuOptionHelper(ASSET_TYPES.values(), "Add asset of which type?", scanner);
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
			saveState();
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
			saveState();
			
			
			break;
			
		case CANCEL:
			return;
		}
	}

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
			
			// A :- B --> B :- A
			
			if(!reverse_lookup.containsKey(dependency.toString())) {
				reverse_lookup.put(dependency.toString(), new ArrayList<Dependency>());
			}
			reverse_lookup.get(dependency.toString()).add(new Dependency(asset));
			
			System.out.println("Would you like to add a dependency? (y/n)");
			addDependencyResponse = scanner.nextLine().trim().toLowerCase();
			addDependency = addDependencyResponse.equals("y") || addDependencyResponse.equals("yes");

		}
	}
	
	private static String menuRepresentation(Enum<?> e) {
		// ADD_ASSET --> Add asset
		return e.name().length() == 1? e.name().toUpperCase() : e.name().substring(0, 1).toUpperCase() + e.name().substring(1).toLowerCase().replaceAll("_", " ");
	}
	
	public Asseteer() {
		library = new ArrayList<Asset>();
		reverse_lookup = new HashMap<>();
		scanner = new Scanner(System.in);
	}

    // GPT:s förslag E[] enumValues, hade helst velat skicka in typen istället, men orkar inte gräva just nu...
	// Vore intressant att diskutera alternativ vid tillfälle.
    private static <E extends Enum<E>> E menuOptionHelper(E[] enumValues, String menuName, Scanner scanner) {
        boolean validInput = false;
        int choice = -1;

        while (!validInput) {
            System.out.println(menuName);
            for (int i = 0; i < enumValues.length; i++) {
                System.out.println("(" + (i + 1) + ")\t" + enumValues[i]);
            }

            try {
                System.out.print("Enter your choice (1 to " + enumValues.length + "): ");
                choice = scanner.nextInt();
                scanner.nextLine();
                choice--; // Convert to zero-based index

                if (choice >= 0 && choice < enumValues.length) {
                    validInput = true;
                    E selectedEnum = enumValues[choice];
                    return selectedEnum;
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and " + enumValues.length + ".");
                }
            } catch (InputMismatchException e) {
                if (scanner.hasNext()) {
                    if(scanner.nextLine().equalsIgnoreCase("q")) {
                    	System.out.println("User halted program");
                    	System.exit(0);
                    }
                    System.out.println("Invalid input. Please enter an integer.");
                }
            }
        }
		return null;
	    }
	}
