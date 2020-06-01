package com.microsoft.azure;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microsoft.azure.service.OrderService;
import com.microsoft.azure.service.OrderServiceImpl;
import com.microsoft.azure.servicebus.jms.ServiceBusJmsConnectionFactory;
import com.microsoft.azure.servicebus.jms.ServiceBusJmsConnectionFactorySettings;

@Configuration
public class JmsConfig {
	
	public static final String RESPONSE_QUEUE = "responseQueue";
	public static final String ORDER_REQUEST_TOPIC = "orderRequestTopic";
	
	private static final String SERVICEBUS_CONNSTRING="ADD SERVICE BUS CONNECTION STRING HERE";

	@Bean
	public Topic topic() {
		return new Topic() {
			
			@Override
			public String getTopicName() throws JMSException {
				return ORDER_REQUEST_TOPIC;
			}
		};
	}
	
	@Bean
	public Queue queue() {
		return new Queue() {
			
			@Override
			public String getQueueName() throws JMSException {
				return RESPONSE_QUEUE;
			}
		};
	}
	
	@Bean
	public ConnectionFactory factory() {
		ServiceBusJmsConnectionFactorySettings settings = new ServiceBusJmsConnectionFactorySettings();
		return new ServiceBusJmsConnectionFactory(SERVICEBUS_CONNSTRING, settings);
	}
	
	
	@Bean
	public OrderService orderService() {
		return new OrderServiceImpl();
	}
}
