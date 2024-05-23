package com.elofturtle.asseteer.exception;

/**
 * This is a user error exception.
 * 
 * Please replace user.
 */
public class PEBKAC extends Exception {

	/**
	 * version
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Läggs till alla undantag
	 */
	private static String s = "Problem Exist Between Keyboard and Chair";
	
	/**
	 * exception
	 */
	public PEBKAC() {
		super(s);
	}

	/**
	 * exception
	 * @param message meddelande
	 */
	public PEBKAC(String message) {
		super(s + "\n" + message);
	}

	/**
	 * exception
	 * @param cause orsak
	 */
	public PEBKAC(Throwable cause) {
		super(cause);
	}

	/**
	 * exception
	 * @param message meddelande
	 * @param cause orsak
	 */
	public PEBKAC(String message, Throwable cause) {
		super(s + "\n" + message, cause);
	}

	/**
	 * exception
	 * @param message meddelande
	 * @param cause orsak
	 * @param enableSuppression undertryck
	 * @param writableStackTrace stacktrace
	 */
	public PEBKAC(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(s + "\n" + message, cause, enableSuppression, writableStackTrace);
	}

}
