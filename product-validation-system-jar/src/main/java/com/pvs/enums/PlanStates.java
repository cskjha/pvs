package com.pvs.enums;

public enum PlanStates {
	ACTIVE("active"), PASSIVE("passive");

	private String planState;

	public String getPlanState() {
		return planState;
	}

	public void setPlanState(String planState) {
		this.planState = planState;
	}
	PlanStates(String planState) {
		this.planState = planState;
	}
	
}
