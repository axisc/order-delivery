package com.microsoft.azure.service;

import java.util.List;

import javax.jms.Destination;

import com.microsoft.azure.models.common.Order;

public interface StoreOrderService {
	
	Order findById(long id);
	
	void saveOrder(Order order, Destination destination);
	
	void updateStateOnOrder(long id, String state);
	
	boolean doesOrderExist(Order ordercheck);
	
	List<Order> findAllOrders();
	
	Destination findReplyToDestinationById(long id);

}
