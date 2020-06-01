package com.microsoft.azure;

import javax.jms.ConnectionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microsoft.azure.service.StoreOrderService;
import com.microsoft.azure.service.StoreOrderServiceImpl;
import com.microsoft.azure.servicebus.jms.ServiceBusJmsConnectionFactory;
import com.microsoft.azure.servicebus.jms.ServiceBusJmsConnectionFactorySettings;

@Configuration
public class JmsConfig {
	
	public static final String ORDER_REQUEST_TOPIC = "orderRequestTopic";
	
	private static final String SERVICEBUS_CONNSTRING="ADD SERVICE BUS CONNECTION STRING HERE";
	
	@Bean
	public ConnectionFactory factory() {
		ServiceBusJmsConnectionFactorySettings settings = new ServiceBusJmsConnectionFactorySettings();
		return new ServiceBusJmsConnectionFactory(SERVICEBUS_CONNSTRING, settings);
	}
	
	
	@Bean
	public StoreOrderService storeOrderService() {
		return new StoreOrderServiceImpl();
	}
	
}
