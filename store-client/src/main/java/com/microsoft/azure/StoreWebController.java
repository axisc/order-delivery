package com.microsoft.azure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microsoft.azure.models.common.Order;
import com.microsoft.azure.service.StoreOrderService;

@Controller
public class StoreWebController {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private StoreOrderService storeOrderService;
	
	@GetMapping("/orderrequests")
	public String viewAllOrders(Model model) {
		model.addAttribute("orders", storeOrderService.findAllOrders());
		return "viewAllOrders";
	}
	
	@GetMapping("/accept/{id}")
	public String acceptOrder(@PathVariable long id, Model model) {
		Order order = storeOrderService.findById(id);
		
		if (order == null) {
			return "orderNotFound";
		}
		
		order.setState("accepted");
		storeOrderService.updateStateOnOrder(id, "accepted");
		
		jmsTemplate.convertAndSend(storeOrderService.findReplyToDestinationById(id), order);
		
		return viewAllOrders(model);
	}
	
	@GetMapping("complete/{id}")
	public String completeOrder(@PathVariable long id, Model model) {
		Order order = storeOrderService.findById(id);
		
		if (order == null) {
			return "orderNotFound";
		}
		
		order.setState("completed");
		storeOrderService.updateStateOnOrder(id, "completed");
		
		jmsTemplate.convertAndSend(storeOrderService.findReplyToDestinationById(id), order);
		
		return viewAllOrders(model);
	}
	
}
