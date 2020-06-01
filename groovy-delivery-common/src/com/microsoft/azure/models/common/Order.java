package com.microsoft.azure.models.common;

import java.io.Serializable;

public class Order implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	private Long id;
	private String orderString;
	private String address;
	private String state = "Created";
	
	public Order() {
		super();
	}
	
	public Order(String orderString, String address, String state) {
		super();
		this.setOrderString(orderString);
		this.setAddress(address);
		this.setState(state);
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public String getOrderString() {
		return orderString;
	}

	public void setOrderString(String orderString) {
		this.orderString = orderString;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
