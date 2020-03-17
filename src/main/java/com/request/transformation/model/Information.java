package com.request.transformation.model;

public class Information {

	private String information;
	private int informationCount;
	private String consentInfo;
	private String consentControllerInfo;
	
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public int getInformationCount() {
		return informationCount;
	}
	public void setInformationCount(int informationCount) {
		this.informationCount = informationCount;
	}
	public String getConsentInfo() {
		return consentInfo;
	}
	public void setConsentInfo(String consentInfo) {
		this.consentInfo = consentInfo;
	}
	public String getConsentControllerInfo() {
		return consentControllerInfo;
	}
	public void setConsentControllerInfo(String consentControllerInfo) {
		this.consentControllerInfo = consentControllerInfo;
	}
	
	@Override
	public String toString() {
		return "Information [information=" + information + ", informationCount=" + informationCount + ", consentInfo="
				+ consentInfo + ", consentControllerInfo=" + consentControllerInfo + "]";
	}
}
