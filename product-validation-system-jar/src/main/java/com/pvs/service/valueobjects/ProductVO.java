package com.pvs.service.valueobjects;

import java.util.List;

public class ProductVO {

	String productId;
	String productName;
	String productTemplateId;
	String companyId;
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
