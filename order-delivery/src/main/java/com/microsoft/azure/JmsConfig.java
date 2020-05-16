package com.microsoft.azure;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.TemporaryQueue;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTempQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microsoft.azure.service.OrderService;
import com.microsoft.azure.service.OrderServiceImpl;

@Configuration
public class JmsConfig {
	
	private static final String RESPONSE_QUEUE = "responseQueue";

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
	public TemporaryQueue temporaryQueue() {
		return new TemporaryQueue() {
			
			@Override
			public String getQueueName() throws JMSException {
				return RESPONSE_QUEUE;
			}
			
			@Override
			public void delete() throws JMSException {
			}
		};
	}
	
	@Bean
	public ConnectionFactory factory() {
		return new ActiveMQConnectionFactory();
	}
	
	@Bean
	public OrderService orderService() {
		return new OrderServiceImpl();
	}
}
