package com.microsoft.azure.models;

import java.io.Serializable;

public class Order implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5791765914874200387L;
	private Long id;
	private String orderString;
	private String address;
	private String state;
	
	public Order() {
		super();
	}
	
	public Order(String orderString, String address) {
		super();
		this.setOrderString(orderString);
		this.setAddress(address);
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
