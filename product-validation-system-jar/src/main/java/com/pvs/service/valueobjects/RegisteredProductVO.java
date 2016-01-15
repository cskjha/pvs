package com.pvs.service.valueobjects;

import java.util.List;

public class RegisteredProductVO {

	String productTemplateId,productTemplateName;
	int numberofRegisteredProduct;
	String iconImg;
	
	public String getIconImg() {
		return iconImg;
	}

	public void setIconImg(String iconImg) {
		this.iconImg = iconImg;
	}

	List<AppUserDetailsVO> appUserDetailsList;

	public List<AppUserDetailsVO> getAppUserDetailsList() {
		return appUserDetailsList;
	}

	public void setAppUserDetailsList(List<AppUserDetailsVO> appUserDetailsList) {
		this.appUserDetailsList = appUserDetailsList;
	}

	public int getNumberofRegisteredProduct() {
		return numberofRegisteredProduct;
	}

	public void setNumberofRegisteredProduct(int numberofRegisteredProduct) {
		this.numberofRegisteredProduct = numberofRegisteredProduct;
	}

	public String getProductTemplateId() {
		return productTemplateId;
	}

	public void setProductTemplateId(String productTemplateId) {
		this.productTemplateId = productTemplateId;
	}

	public String getProductTemplateName() {
		return productTemplateName;
	}

	public void setProductTemplateName(String productTemplateName) {
		this.productTemplateName = productTemplateName;
	}
}
