package com.elofturtle.asseteer.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

import com.elofturtle.asseteer.model.Asset;
import com.elofturtle.asseteer.model.Dependency;
import com.elofturtle.asseteer.model.SBOM;

public class Asseteer {
	private ArrayList<Asset> library;
	private Map<String, List<Dependency>> reverse_lookup;
	public String globalStateFile;
	public Scanner scanner;
	
	enum MENU_STATE {
			IMPORT_FROM_FILE,
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
		Asseteer a = new Asseteer();
		a.globalStateFile = System.getenv("HOME") + "/asseteer.xml";
		
		Queue<String> opts = new LinkedList<>();
		for(var item : args) {
			opts.add(item);
		}
		while(!opts.isEmpty()) {
			String item = opts.remove();
			switch(item) {
			case "-f":
			case "--file":
				a.globalStateFile = opts.remove();
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
					case IMPORT_FROM_FILE:
						a.MenuOptionImportFromFile();
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
	
	private void MenuOptionImportFromFile() {
		
		
	}

	private void MenuOptionListAssets() {
		for(var item : library) {
			System.out.println(item.toRepresentation());
		}
		
	}

	private void MenuOptionRemoveAsset() {
		// TODO Auto-generated method stub
		
	}

	private void saveGlobalState() {
		// TODO Auto-generated method stub
		
	}

	private void MenuOptionSearchAsset() {
		// TODO Auto-generated method stub
		
	}

	private void MenuOptionEditAsset() {
		// TODO Auto-generated method stub
		
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
			
			System.out.println("Would you like to add a dependency? (true/false)");
			boolean addDependency = scanner.nextBoolean();
			scanner.nextLine();
			
			while(addDependency) {
				System.out.println("Enter id of dependency:");
				String newdep = scanner.nextLine();
				System.out.println("New Dep: '" + newdep + "'");
				Dependency dependency = new Dependency(newdep);
				sbom.addDependency(dependency);
				
				// A :- B --> B :- A
				if(!reverse_lookup.containsKey(dependency.toString())) {
					reverse_lookup.put(dependency.toString(), new ArrayList<Dependency>());
				}
				reverse_lookup.get(dependency.toString()).add(new Dependency(sbom));
				
				System.out.println("Would you like to add another dependency? (true/false)");
				addDependency = scanner.nextBoolean();
				scanner.nextLine();
			}
			
			library.add(sbom);
			
			
			break;
			
		case CANCEL:
			return;
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
