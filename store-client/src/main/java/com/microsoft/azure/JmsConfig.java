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
		return new ActiveMQConnectionFactory();
	}
	
	@Bean
	public StoreOrderService storeOrderService() {
		return new StoreOrderServiceImpl();
	}
	
//	@Bean
//	public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
//	                                                DefaultJmsListenerContainerFactoryConfigurer configurer) {
//	    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//	    factory.setPubSubDomain(true);
//	    factory.setSubscriptionDurable(true);
//	    factory.setClientId("jmsDemo");
//	    // This provides all boot's default to this factory, including the message converter
//	    configurer.configure(factory, connectionFactory);
//	    // You could still override some of Boot's default if necessary.
//	    return factory;
//	}

}
