package com.request.transformation.model;

import java.util.List;

public class Request01 {

	private String name;
	private String id;
	private String address;
	private String anything;	
	private List<String> comments;
	
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
	@Override
	public String toString() {
		return "Request01 [name=" + name + ", id=" + id + ", address=" + address + ", anything=" + anything
				+ ", comments=" + comments + "]";
	}
}
