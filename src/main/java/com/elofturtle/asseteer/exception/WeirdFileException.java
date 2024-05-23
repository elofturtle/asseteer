package com.elofturtle.asseteer.exception;

/**
 * Formateringsfel eller annat som gör att filbehandling misslyckas.
 */
public class WeirdFileException extends Exception {
	/**
	 * version
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * standardtillägg till alla undantag
	 */
	private static String s = "Något konstigt har hänt med någon fil";
	/**
	 * exception
	 */
	public WeirdFileException() {
		super(s);
	}

	/**
	 * exception
	 * @param message meddelande
	 */
	/**
	 * exception
	 * @param message meddelande
	 */
	public WeirdFileException(String message) {
		super(s + "\n" + message);
	}

	/**
	 * exception
	 * @param cause orsak
	 */
	public WeirdFileException(Throwable cause) {
		super(cause);
	}

	/**
	 * exception
	 * @param message meddelande
	 * @param cause orsak
	 */
	public WeirdFileException(String message, Throwable cause) {
		super(s + "\n" + message, cause);
	}

	/**
	 * exception
	 * @param message meddelande
	 * @param cause orsak
	 * @param enableSuppression undertryck
	 * @param writableStackTrace stacktrace
	 */
	public WeirdFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(s + "\n" + message, cause, enableSuppression, writableStackTrace);
	}

}
