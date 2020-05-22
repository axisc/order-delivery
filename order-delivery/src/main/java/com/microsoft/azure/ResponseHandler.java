package com.microsoft.azure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.microsoft.azure.models.common.Order;
import com.microsoft.azure.service.OrderService;

@Component
public class ResponseHandler {
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private OrderService orderService;
	
	@JmsListener(destination = JmsConfig.RESPONSE_QUEUE)
	public void onResponseQueueMessage(Order order) {
		orderService.updateStateOnOrder(order.getId(), order.getState());
	}
	
}
