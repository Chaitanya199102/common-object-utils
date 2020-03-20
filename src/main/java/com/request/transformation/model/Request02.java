package com.request.transformation.model;

import java.util.List;

public class Request02 {

	private String name;
	private String identity;
	private String addressCorrespondense;
	private String something;
	private List<String> comments;
	private String[] commentsArray;
	private String countString;
	private int anotherCount;
	private long oneMoreCountAsLong;
	private Details details;
	private List<Description> description;
	private List<AdditionalDescription2> additionalDesc;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getAddressCorrespondense() {
		return addressCorrespondense;
	}
	public void setAddressCorrespondense(String addressCorrespondense) {
		this.addressCorrespondense = addressCorrespondense;
	}
	public String getSomething() {
		return something;
	}
	public void setSomething(String something) {
		this.something = something;
	}
	
	public List<String> getComments() {
		return comments;
	}
	public void setComments(List<String> comments) {
		this.comments = comments;
	}
	
	public String[] getCommentsArray() {
		return commentsArray;
	}
	public void setCommentsArray(String[] commentsArray) {
		this.commentsArray = commentsArray;
	}
	public String getCountString() {
		return countString;
	}
	public void setCountString(String countString) {
		this.countString = countString;
	}
	public int getAnotherCount() {
		return anotherCount;
	}
	public void setAnotherCount(int anotherCount) {
		this.anotherCount = anotherCount;
	}
	public long getOneMoreCountAsLong() {
		return oneMoreCountAsLong;
	}
	public void setOneMoreCountAsLong(long oneMoreCountAsLong) {
		this.oneMoreCountAsLong = oneMoreCountAsLong;
	}
	public Details getDetails() {
		return details;
	}
	public void setDetails(Details details) {
		this.details = details;
	}
	public List<Description> getDescription() {
		return description;
	}
	public void setDescription(List<Description> description) {
		this.description = description;
	}
	public List<AdditionalDescription2> getAdditionalDesc() {
		return additionalDesc;
	}
	public void setAdditionalDesc(List<AdditionalDescription2> additionalDesc) {
		this.additionalDesc = additionalDesc;
	}
}
