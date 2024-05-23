package com.elofturtle.asseteer.application;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.elofturtle.asseteer.exception.PEBKAC;

class MenuHelpers {
	/**
	 * Helper method transforming enum name to a nice string that can be used in menus.
	 * @param e enum
	 * @return ADD_ASSET --> Add asset
	 */
	protected static String menuRepresentation(Enum<?> e) {
		return e.name().length() == 1? e.name().toUpperCase() : e.name().substring(0, 1).toUpperCase() + e.name().substring(1).toLowerCase().replaceAll("_", " ");
	}

	// GPT:s förslag E[] enumValues, hade helst velat skicka in typen istället, men orkar inte gräva just nu...
	// Vore intressant att diskutera alternativ vid tillfälle.
	/**
	 * Use: {@code var foo = menuOptionHelper(MY_ENUM.values(), "My Menu", scanner)}
	 * <p>
	 * This returns an actual enum value that can be used in a switch statement.
	 * <p>
	 * <pre>
	 * My Menu
	 * 1 foo
	 * 2 bar
	 * 3 baz
	 * </pre>
	 * @param <E> specific enum type
	 * @param enumValues enum values
	 * @param menuName top display 
	 * @param scanner e.g. System.in
	 * @return the specific enum value chosen
	 * @throws PEBKAC 
	 */
	static <E extends Enum<E>> E menuOptionHelper(E[] enumValues, String menuName, Scanner scanner) throws PEBKAC {
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
	                throw new PEBKAC("That is not a correct choice.");
	            }
	        } catch (InputMismatchException e) {
	            if (scanner.hasNext()) {
	                if(scanner.nextLine().equalsIgnoreCase("q")) {
	                	System.out.println("User halted program");
	                	System.exit(0);
	                }
	                throw new PEBKAC("Invalid input. Please enter an integer.", e);
	            }
	        }
	    }
		return null;
	    }
	
}
