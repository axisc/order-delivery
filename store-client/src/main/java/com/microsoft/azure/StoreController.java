package com.microsoft.azure;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.azure.models.common.Order;
import com.microsoft.azure.service.StoreOrderService;

@RestController
@RequestMapping("/api/")
public class StoreController {
	
	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private StoreOrderService storeOrderService;
	
	@GetMapping(value= "/requests")
	public ResponseEntity<List<Order>> listAllOrders() {
		List<Order> orders = storeOrderService.findAllOrders();
		if(orders.isEmpty()) {
			return new ResponseEntity<List<Order>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
	}
	
	@GetMapping(value = "/accept/{id}")
	public ResponseEntity<Order> acceptOrder(@PathVariable("id") long id) {
		
		Order order = storeOrderService.findById(id);
		if (order == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
		order.setState("accepted");
		storeOrderService.updateStateOnOrder(id, "accepted");
		
		jmsTemplate.convertAndSend(storeOrderService.findReplyToDestinationById(id), order);
		
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}
	
	@GetMapping(value = "/complete/{id}")
	public ResponseEntity<Order> completeOrder(@PathVariable("id") long id) {
		
		Order order = storeOrderService.findById(id);
		if (order == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
		order.setState("completed");
		storeOrderService.updateStateOnOrder(id, "completed");
		
		jmsTemplate.convertAndSend(storeOrderService.findReplyToDestinationById(id), order);
		
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}
}
