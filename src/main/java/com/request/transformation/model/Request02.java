package com.request.transformation.model;

import java.util.List;

public class Request02 {

	private String name;
	private String identity;
	private String addressCorrespondense;
	private String something;
	private List<String> comments;
	
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
	
	@Override
	public String toString() {
		return "Request02 [name=" + name + ", identity=" + identity + ", addressCorrespondense=" + addressCorrespondense
				+ ", something=" + something + ", comments=" + comments + "]";
	}
	
}
