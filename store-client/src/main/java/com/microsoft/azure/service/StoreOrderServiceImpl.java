package com.microsoft.azure.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.jms.Destination;

import com.microsoft.azure.models.common.Order;

public class StoreOrderServiceImpl implements StoreOrderService {
	
	private static final AtomicLong counter = new AtomicLong();
	
	private static List<Order> orders = new ArrayList<Order>();
	private static Map<Long, Destination> idToReplyToDestination = new HashMap<Long, Destination>();

	@Override
	public Order findById(long id) {
		for (Order order : orders) {
			if (order.getId() == id)
				return order;
		}
		return null;
	}

	@Override
	public void saveOrder(Order order, Destination destination) {
		order.setId(counter.incrementAndGet());
		orders.add(order);
		idToReplyToDestination.put(order.getId(), destination);
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

	@Override
	public Destination findReplyToDestinationById(long id) {
		return idToReplyToDestination.get(new Long(id));
	}

}
