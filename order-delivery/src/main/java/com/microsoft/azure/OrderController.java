package com.microsoft.azure;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TemporaryQueue;
import javax.jms.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.microsoft.azure.models.Order;
import com.microsoft.azure.service.OrderService;

@RestController
@RequestMapping("/api/")
public class OrderController {
	
	@Autowired
	private Topic topic;
	
	@Autowired
	private TemporaryQueue temporaryQueue;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private OrderService orderService;
	
	 // -------------------Retrieve All Orders ---------------------------------------------
	 
    @GetMapping(value = "/order")
    public ResponseEntity<List<Order>> listAllUsers() {
        List<Order> order = orderService.findAllOrders();
        if (order.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Order>>(order, HttpStatus.OK);
    }
 
    // -------------------Retrieve Single Order ------------------------------------------
 
    @GetMapping("/order/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        Order order = orderService.findById(id);
        if (order == null) {
            return new ResponseEntity(new CustomErrorType("User with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }
 
    // -------------------Create a Order -------------------------------------------
 
	
	@PostMapping("/order")
	public ResponseEntity<?> createOrder(@RequestBody Order order, UriComponentsBuilder ucBuilder) {
		
		orderService.saveOrder(order);
		
		jmsTemplate.convertAndSend(topic, order, new MessagePostProcessor() {
			
			@Override
			public Message postProcessMessage(Message message) throws JMSException {
				message.setStringProperty("address", order.getAddress());
				message.setJMSReplyTo(temporaryQueue);
				return null;
			}
		});
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/order/{id}").buildAndExpand(order.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

}