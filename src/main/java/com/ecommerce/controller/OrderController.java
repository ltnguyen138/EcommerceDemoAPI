package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.OrderDTO;
import com.ecommerce.entity.User;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.UserService;

@RestController
@RequestMapping(path = "api/v1/orders")
public class OrderController {

	@Autowired
	OrderService orderService;
	@Autowired
	UserService userService;
	
	@PostMapping
	public ResponseEntity<?> createOrder(@RequestParam("userId") Integer userId,
			@RequestParam(name = "fullName",required = false) String fullName,
			@RequestParam(name = "phoneNumber",required = false) String phoneNumber,
			@RequestParam(name = "address",required = false) String address,
			@RequestParam(name = "note",required = false) String note){
		User user = userService.getUserById(userId);
		OrderDTO orderDTO = orderService.createOrder(user,fullName,phoneNumber,address,note);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
	}
	
	@GetMapping
	public ResponseEntity<?> createOrder(@RequestParam("userId") Integer userId){
		User user = userService.getUserById(userId);
		List<OrderDTO> orderDTOs = orderService.getOrderByUser(user);
		return ResponseEntity.ok(orderDTOs);
	}
	
	@GetMapping("/cancelled/{orderId}")
	public ResponseEntity<?>  cancelOrder(@RequestParam("userId") Integer userId,
			@PathVariable("orderId") Integer orderId){
		User user = userService.getUserById(userId);
		OrderDTO orderDTO = orderService.cancelOrder(user, orderId);
		return ResponseEntity.ok(orderDTO);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<?>  getOrderById(@RequestParam("userId") Integer userId,
			@PathVariable("orderId") Integer orderId){
		
		User user = userService.getUserById(userId);
		OrderDTO orderDTO = orderService.getOrderDTOById(orderId);
		return ResponseEntity.ok(orderDTO);
	}
}
