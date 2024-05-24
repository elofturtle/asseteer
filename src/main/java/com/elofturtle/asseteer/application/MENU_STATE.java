package com.elofturtle.asseteer.application;

/**
 * Huvudmenyval
 */
enum MENU_STATE {
	ADD_ASSET,
	EDIT_ASSET,
	REMOVE_ASSET,
	LIST_ASSETS,
	SEARCH_ASSET,
	INVERTED_SEARCH,
	IMPORTERA_TILLGÅNGAR,
	HJÄLP,
	QUIT;

	/**
	 *Strängrepresentation
	 *@return sträng sträng
	 */
	@Override
	public String toString() {
		return MenuHelpers.menuRepresentation(this);
	}
}