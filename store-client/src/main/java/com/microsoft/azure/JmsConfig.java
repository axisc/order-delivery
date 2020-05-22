package com.microsoft.azure;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import com.microsoft.azure.service.StoreOrderService;
import com.microsoft.azure.service.StoreOrderServiceImpl;

@Configuration
public class JmsConfig {
	
	public static final String ORDER_REQUEST_TOPIC = "testTopic";
	
	@Bean
	public ConnectionFactory factory() {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(); 
		factory.setTrustAllPackages(true);
		return factory;
	}
	
	
	@Bean
	public StoreOrderService storeOrderService() {
		return new StoreOrderServiceImpl();
	}
	
}
