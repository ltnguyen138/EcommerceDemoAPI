package com.ecommerce.dto;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.Product;

public class CartDTO {
	private Integer id;
	private UserDTO user;
	private Product product;
	private int quantity;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void CartDTOMapping(Cart cart) {
		id = cart.getId();
		user = new UserDTO();
		user.UserDTOMapping(cart.getUser());
		product = cart.getProduct();
		quantity = cart.getQuantiny();
	}
}
