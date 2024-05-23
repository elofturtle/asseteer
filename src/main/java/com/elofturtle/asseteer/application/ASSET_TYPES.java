package com.elofturtle.asseteer.application;

enum ASSET_TYPES{
	PROGRAMVARA, 
	SBOM,
	CANCEL;
	
	/**
	 *Strängrepresentation
	 *@return sträng sträng
	 */
	@Override
	public String toString() {
		return MenuHelpers.menuRepresentation(this);
	}
}