package com.request.transformation.model;

public class Details {

	private String details;
	private int detailsCount;
	private AdditionalDetails additionalDetails;
	
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public int getDetailsCount() {
		return detailsCount;
	}
	public void setDetailsCount(int detailsCount) {
		this.detailsCount = detailsCount;
	}
	public AdditionalDetails getAdditionalDetails() {
		return additionalDetails;
	}
	public void setAdditionalDetails(AdditionalDetails additionalDetails) {
		this.additionalDetails = additionalDetails;
	}
	@Override
	public String toString() {
		return "Details [details=" + details + ", detailsCount=" + detailsCount + ", additionalDetails="
				+ additionalDetails + "]";
	}
}
