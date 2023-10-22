package com.ecommerce.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderDetail;
import com.ecommerce.entity.OrderStatus;

public class OrderDTO {
	private Integer id;
	private String fullName;
	private String phoneNumber;
	private String address;
	private String note;
	private Date orderTime ;
	private OrderStatus orderStatus;
	private float totalCost;
	private float totalPrice;
	private UserDTO userDTO;
	private Set<OrderDetailDTO> orderDetailDTOs;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public float getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public UserDTO getUserDTO() {
		return userDTO;
	}
	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	
	
	
	public Set<OrderDetailDTO> getOrderDetailDTOs() {
		return orderDetailDTOs;
	}
	public void setOrderDetailDTOs(Set<OrderDetailDTO> orderDetailDTOs) {
		this.orderDetailDTOs = orderDetailDTOs;
	}
	public void OrderDTOMapping(Order order) {
		id = order.getId();
		fullName = order.getFullName();
		phoneNumber = order.getPhoneNumber();
		address=order.getAddress();
		orderTime = order.getOrderTime();
		orderStatus = order.getOrderStatus();
		totalCost = order.getTotalCost();
		totalPrice = order.getTotalPrice();
		userDTO = new UserDTO();
		orderDetailDTOs = new HashSet<>();
		userDTO.UserDTOMapping(order.getUser());
		for(OrderDetail orderDetail : order.getOrderDetails()) {
			OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
			orderDetailDTO.OederDetailMapping(orderDetail);
			
			orderDetailDTOs.add(orderDetailDTO);
		}
		
	}
}
