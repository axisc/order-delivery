package com.microsoft.azure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsMessageHeaderAccessor;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.microsoft.azure.models.Order;
import com.microsoft.azure.service.StoreOrderService;

@Component
public class OrderHandler {
	
	@Autowired
	private StoreOrderService orderService;

	@JmsListener(destination = JmsConfig.ORDER_REQUEST_TOPIC, subscription = "orderSubscriber" )
	public void receiveOrderRequests(@Payload Order storeOrder, JmsMessageHeaderAccessor jmsHeaderAccessor) {
//		storeOrder.setReplyTo(jmsHeaderAccessor.getReplyTo());
		orderService.saveOrder(storeOrder);
	}
}
