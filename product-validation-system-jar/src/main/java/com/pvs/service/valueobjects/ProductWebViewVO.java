package com.pvs.service.valueobjects;

import java.util.ArrayList;

public class ProductWebViewVO {
	String productName,manufacturerName,manufacturedOn,image,productRating;
	public String getProductRating() {
		return productRating;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setProductRating(String productRating) {
		this.productRating = productRating;
	}

	ArrayList<ProductValueVO> dynamicValues;

	public ArrayList<ProductValueVO> getDynamicValues() {
		return dynamicValues;
	}

	public void setDynamicValues(ArrayList<ProductValueVO> dynamicValues) {
		this.dynamicValues = dynamicValues;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public String getManufacturedOn() {
		return manufacturedOn;
	}

	public void setManufacturedOn(String manufacturedOn) {
		this.manufacturedOn = manufacturedOn;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
}
