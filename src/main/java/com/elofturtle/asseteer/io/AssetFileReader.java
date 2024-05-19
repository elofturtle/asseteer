package com.elofturtle.asseteer.io;

import java.util.ArrayList;

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
	
	public abstract ArrayList<Asset> readFromFile(String filePath);
	
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
	        return filePath;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
}
