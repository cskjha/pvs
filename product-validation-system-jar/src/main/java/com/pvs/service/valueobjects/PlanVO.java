package com.pvs.service.valueobjects;

public class PlanVO {
	String planId;
	String planName;
	String allowedRecordCount;
	String allowedScanCount;
	String planState;
	String planPrice;
	String currencyCode;
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getAllowedRecordCount() {
		return allowedRecordCount;
	}
	public void setAllowedRecordCount(String allowedRecordCount) {
		this.allowedRecordCount = allowedRecordCount;
	}
	public String getAllowedScanCount() {
		return allowedScanCount;
	}
	public void setAllowedScanCount(String allowedScanCount) {
		this.allowedScanCount = allowedScanCount;
	}
	public String getPlanState() {
		return planState;
	}
	public void setPlanState(String planState) {
		this.planState = planState;
	}
	public String getPlanPrice() {
		return planPrice;
	}
	public void setPlanPrice(String planPrice) {
		this.planPrice = planPrice;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}	
}
