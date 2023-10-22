package com.ecommerce.dto;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderDetail;
import com.ecommerce.entity.Product;

public class OrderDetailDTO {

	private Integer id;
	private int quantity;
	private float cost;
	private float price;
	private Product product;
	private Integer orderId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	public void OederDetailMapping(OrderDetail orderDetail) {
		id = orderDetail.getId();
		quantity= orderDetail.getQuantity();
		cost = orderDetail.getCost();
		price = orderDetail.getPrice();
		product = new Product();
		product = orderDetail.getProduct();
		orderId = orderDetail.getOrder().getId();
	}
}
