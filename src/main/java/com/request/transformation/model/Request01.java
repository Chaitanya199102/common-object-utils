package com.request.transformation.model;

import java.util.List;

public class Request01 {

	private String name;
	private String id;
	private String address;
	private String anything;	
	private List<String> comments;
	private int count;
	private String anotherCountAsString;
	private String oneMoreCount;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAnything() {
		return anything;
	}
	public void setAnything(String anything) {
		this.anything = anything;
	}
	public List<String> getComments() {
		return comments;
	}
	public void setComments(List<String> comments) {
		this.comments = comments;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getAnotherCountAsString() {
		return anotherCountAsString;
	}
	public void setAnotherCountAsString(String anotherCountAsString) {
		this.anotherCountAsString = anotherCountAsString;
	}
	public String getOneMoreCount() {
		return oneMoreCount;
	}
	public void setOneMoreCount(String oneMoreCount) {
		this.oneMoreCount = oneMoreCount;
	}
	@Override
	public String toString() {
		return "Request01 [name=" + name + ", id=" + id + ", address=" + address + ", anything=" + anything
				+ ", comments=" + comments + ", count=" + count + ", anotherCountAsString=" + anotherCountAsString
				+ ", oneMoreCount=" + oneMoreCount + "]";
	}
}
