package com.microsoft.azure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsMessageHeaderAccessor;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.microsoft.azure.models.common.Order;
import com.microsoft.azure.service.StoreOrderService;

@Component
public class OrderHandler {
	
	@Autowired
	private StoreOrderService storeOrderService;

	@JmsListener(destination = JmsConfig.ORDER_REQUEST_TOPIC, subscription = "orderSubscriber", selector="address='Seattle'" )
	public void receiveOrderRequests(@Payload Order order, JmsMessageHeaderAccessor jmsHeaderAccessor) {
		storeOrderService.saveOrder(order, jmsHeaderAccessor.getReplyTo());
	}
}
