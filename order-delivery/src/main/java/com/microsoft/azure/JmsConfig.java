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

@Configuration
public class JmsConfig {
	
	public static final String RESPONSE_QUEUE = "responseQueue";

	@Bean
	public Topic topic() {
		return new Topic() {
			
			@Override
			public String getTopicName() throws JMSException {
				return "testTopic";
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
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(); 
		factory.setTrustAllPackages(true);
		return factory;
	}
	
	@Bean
	public OrderService orderService() {
		return new OrderServiceImpl();
	}
}
