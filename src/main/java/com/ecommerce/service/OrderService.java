package com.ecommerce.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.dto.OrderDTO;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderDetail;
import com.ecommerce.entity.OrderStatus;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.NotFoundException;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	CartRepository cartRepository;
	
	public OrderDTO createOrder(User user,String fullName, String phoneNumber, String address, String note) {
		
		if(phoneNumber == null ) {
			throw new BadRequestException("Khach hang chua chon so dien thoai");
		}
		if(address == null) {
			throw new BadRequestException("Khach hang chua chon dia chi");
		}
		List<Cart> listCart = cartRepository.findByUser(user);
		
		Order order = new Order();
		float totalPrice =  calculateTotalPrice(listCart);
		float totalCost= cacalculateTotalCost(listCart);
		
		order.setAddress(address);
		order.setFullName(fullName);
		order.setNote(note);
		order.setPhoneNumber(phoneNumber);
		order.setTotalCost(totalCost);
		order.setTotalPrice(totalPrice);
		order.setOrderTime(new Date());
		order.setOrderStatus(OrderStatus.NEW);
		order.setUser(user);
		Set<OrderDetail> orderDetails = order.getOrderDetails();
		
		for(Cart cart : listCart) {
			OrderDetail detail = new OrderDetail();
			
			Product product = cart.getProduct();
			detail.setOrder(order);
			detail.setCost(product.getCost());
			detail.setPrice(product.getPrice());
			detail.setProduct(product);
			detail.setQuantity(cart.getQuantiny());
			
			orderDetails.add(detail);
		}
		orderRepository.save(order);
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.OrderDTOMapping(order);
		return orderDTO;
	}

	private float cacalculateTotalCost(List<Cart> listCart) {
		float totalCost = 0;
		for(Cart cart : listCart) {
			Product product = cart.getProduct();
			totalCost+= product.getCost()*cart.getQuantiny();
		}
		return totalCost;
	}

	private float calculateTotalPrice(List<Cart> listCart) {
		float totalPrice = 0;
		for(Cart cart : listCart) {
			Product product = cart.getProduct();
			totalPrice+= product.getPrice()*cart.getQuantiny();
		}
		return totalPrice;
	}
	
	public List<OrderDTO> getOrderByUser(User user) {
		List<Order> listOrders = orderRepository.findAllOrderByUId(user.getId());
		List<OrderDTO> orderDTOs = new ArrayList<>();
		for(Order order : listOrders) {
			OrderDTO orderDTO = new OrderDTO();
			orderDTO.OrderDTOMapping(order);
			orderDTOs.add(orderDTO);
		}
		return orderDTOs;
	}
	
	public Order getOrderById(Integer id) {
		return orderRepository.findById(id).orElseThrow(()-> new NotFoundException("Khong tim thay don hang"));
	}
	
	public OrderDTO getOrderDTOById(Integer id) {
		Order order = getOrderById(id);
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.OrderDTOMapping(order);
		return orderDTO;
	}
	
	
	public OrderDTO cancelOrder(User user, Integer orederId) {
		Order order = getOrderById(orederId);
		order.setOrderStatus(OrderStatus.CANCELLED);
		orderRepository.save(order);
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.OrderDTOMapping(order);
		return orderDTO; 
	}
}
