package com.pvs.enums;

public enum ProductTypes {
	FOOD_PRODUCTS("FP"), NON_FOOD_PRODUCTS("NFP");

	private String productType;

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	ProductTypes(String productType) {
		this.productType = productType;
	}
}
