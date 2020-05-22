package com.microsoft.azure.service;

import java.util.List;

import com.microsoft.azure.models.common.Order;

public interface OrderService {
	
	Order findById(long id);
	
	void saveOrder(Order order);
	
	void updateStateOnOrder(long id, String state);
	
	boolean doesOrderExist(Order ordercheck);
	
	List<Order> findAllOrders();

}
