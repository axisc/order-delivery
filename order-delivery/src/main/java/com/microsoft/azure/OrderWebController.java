package com.microsoft.azure;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.microsoft.azure.models.common.Order;
import com.microsoft.azure.service.OrderService;

@Controller
public class OrderWebController {
	
	@Autowired
	private Topic topic;
	
	@Autowired
	private Queue queue;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private OrderService orderService;

	// ----------- Order form -----------------//
	@GetMapping("/orderadd")
	public String orderForm(Model model) {
		model.addAttribute("order", new Order());
		return "orderform";
	}
	
	// ---------- Create Order ----------------//
	@PostMapping("/orderadd")
	public String orderSubmit(@ModelAttribute Order order, Model model) {
		
		orderService.saveOrder(order);
		jmsTemplate.convertAndSend(topic, order, new MessagePostProcessor() {
			
			@Override
			public Message postProcessMessage(Message message) throws JMSException {
				message.setStringProperty("address", order.getAddress());
				message.setJMSReplyTo(queue);
				return message;
			}
		});
		return viewAllOrders(model);
	}
	
	// ------------- View order -------------------//
	@GetMapping("/orderview/{id}")
	public String orderView(@PathVariable long id, Model model) {
		Order order = orderService.findById(id);
		if (order == null) {
			return "orderNotFound";
		}
		
		model.addAttribute("order", order);
		return "orderView";
	}
	
	// ------------- View all orders ---------------//
	@GetMapping("/orderview")
	public String viewAllOrders(Model model) {
		model.addAttribute("orders", orderService.findAllOrders());
		return "viewAllOrders";
	}
}
