package com.microsoft.azure.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.microsoft.azure.models.Order;

public class OrderServiceImpl implements OrderService {
	
	private static final AtomicLong counter = new AtomicLong();
	
	private static List<Order> orders = new ArrayList<Order>();

	@Override
	public Order findById(long id) {
		for (Order order : orders) {
			if (order.getId() == id)
				return order;
		}
		return null;
	}

	@Override
	public void saveOrder(Order order) {
		order.setId(counter.incrementAndGet());
		orders.add(order);
	}

	@Override
	public boolean doesOrderExist(Order ordercheck) {
		for (Order order: orders) {
			if (order.getId() == ordercheck.getId())
				return true;
		}
		return false;
	}

	@Override
	public void updateStateOnOrder(long id, String state) {
		for (Order order: orders) {
			if (order.getId() == id)
				order.setState(state);
		}
	}

	@Override
	public List<Order> findAllOrders() {
		return orders;
	}

}
