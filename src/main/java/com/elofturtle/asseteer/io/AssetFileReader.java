package com.elofturtle.asseteer.io;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import com.elofturtle.asseteer.exception.*;

import org.jline.builtins.Completers.FileNameCompleter;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import com.elofturtle.asseteer.model.Asset;

/**
 * Subclasses of Asset reading from a standard format may choose to implement an importer class.
 */
public abstract class AssetFileReader {
	
	/**
	 * Standardkonstruktor
	 */
	public AssetFileReader() {
		
	};
	
	/**
	 * Deserialiserar externa format för import.
	 * @param filePath filsökväg
	 * @return en lista med Assets
	 * @throws WeirdFileException filfel
	 */
	public abstract ArrayList<Asset> readFromFile(String filePath) throws WeirdFileException;
	
	/**
	 * Helper class that assists the user with autocompletion when creating a file path.
	 * @return A path to a file.
	 */
	protected static String filePathHelper() {
		try {
	        Terminal terminal = TerminalBuilder.terminal();
	        LineReader lineReader = LineReaderBuilder.builder()
	                .terminal(terminal)
	                .completer(new FileNameCompleter())
	                .build();
	        String filePath = lineReader.readLine("Enter file path: ");
	        if(Files.notExists(Paths.get(filePath))) {
	        	throw new PEBKAC("That thing you entered isn't a file-path");
	        };
	        return filePath;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
}
