package com.elofturtle.asseteer.application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.elofturtle.asseteer.model.Asset;
import com.elofturtle.asseteer.model.Dependency;

public class Asseteer {
	private ArrayList<Asset> library;
	private ArrayList<ArrayList<Dependency>> reverse_lookup;
	
	enum MENU_STATE {
			IMPORT_FROM_FILE,
			LOAD_GLOBAL_STATE,
			SAVE_GLOBAL_STATE,
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
        MENU_STATE choice = menuOptionHelper(MENU_STATE.values(), "### MENU ###"); 
        System.out.println("You selected: \"" + choice + "\"");
        
        switch (choice) {
	        case ADD_ASSET:
	        	break;
			case EDIT_ASSET:
				break;
			case IMPORT_FROM_FILE:
				break;
			case LIST_ASSETS:
				break;
			case LOAD_GLOBAL_STATE:
				break;
			case QUIT:
				break;
			case REMOVE_ASSET:
				break;
			case SAVE_GLOBAL_STATE:
				break;
			case SEARCH_ASSET:
				break;
			default:
				System.out.println("Fel val :(");
				System.exit(1);
				break;
        }  	
    }
	
	private static String menuRepresentation(Enum<?> e) {
		// ADD_ASSET --> Add asset
		return e.name().length() == 1? e.name().toUpperCase() : e.name().substring(0, 1).toUpperCase() + e.name().substring(1).toLowerCase().replaceAll("_", " ");
	}
	
	public Asseteer() {
		library = new ArrayList<Asset>();
		reverse_lookup = new ArrayList<ArrayList<Dependency>>();
	}

    // gpt förslag E[] enumValues, hade helst velat skicka in typen istället, men orkar inte gräva just nu...
    private static <E extends Enum<E>> E menuOptionHelper(E[] enumValues, String menuName) {
        Scanner scanner = new Scanner(System.in);
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
                choice--; // Convert to zero-based index

                if (choice >= 0 && choice < enumValues.length) {
                    validInput = true;
                    E selectedEnum = enumValues[choice];
                    scanner.close();
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

	        scanner.close(); // Close the scanner once done
			return null;
	    }
	}
