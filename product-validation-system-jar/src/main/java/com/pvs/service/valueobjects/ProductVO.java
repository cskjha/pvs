package com.pvs.service.valueobjects;

import java.util.List;

public class ProductVO {

	String productId;
	String productName;
	String productTemplateId;
	String companyId;
	String responseCode;
	String fullproductId;
	String productType;
	
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getFullproductId() {
		return fullproductId;
	}
	public void setFullproductId(String fullproductId) {
		this.fullproductId = fullproductId;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	List<String> fieldName;
	List<String> fieldValue;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductTemplateId() {
		return productTemplateId;
	}
	public void setProductTemplateId(String productTemplateId) {
		this.productTemplateId = productTemplateId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public List<String> getFieldName() {
		return fieldName;
	}
	public void setFieldName(List<String> fieldName) {
		this.fieldName = fieldName;
	}
	public List<String> getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(List<String> fieldValue) {
		this.fieldValue = fieldValue;
	}
	
}
