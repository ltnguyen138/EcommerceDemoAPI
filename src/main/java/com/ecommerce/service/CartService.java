package com.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.CartDTO;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.exception.NotFoundException;
import com.ecommerce.repository.CartRepository;

@Service
public class CartService {

	@Autowired
	CartRepository cartRepository;
	@Autowired
	ProductService productService;
	public Cart getCartByUserAndProduct(User user, Product product) {
		return cartRepository.findByUserAndProduct(user, product).orElse(null);
	}
	
	public CartDTO addProduct(User user, Integer productId, Integer quantity) {
		Integer updateQuantity = quantity;		
		Product product = productService.getProductById(productId);
		
		Cart cart = getCartByUserAndProduct(user, product);
		if(cart!=null) {
			updateQuantity += cart.getQuantiny();
			cart.setQuantiny(updateQuantity);
		}else {
			cart = new Cart();
			cart.setProduct(product);
			cart.setUser(user);
			cart.setQuantiny(updateQuantity);			
		}
		
		cartRepository.save(cart);
		
		CartDTO cartDTO = new CartDTO();
		cartDTO.CartDTOMapping(cart);
		return  cartDTO;
	}
	
	public List<CartDTO> getCartByUser(User user) {
		List<Cart> listCart = cartRepository.findByUser(user);
		List<CartDTO> listCartDTO= new  ArrayList<>();
		
		for(Cart cart : listCart) {
			CartDTO cartDTO = new CartDTO();
			cartDTO.CartDTOMapping(cart);
			listCartDTO.add(cartDTO);
		}
		return  listCartDTO;
	}
	
	public CartDTO updateProduct(User user, Integer productId, Integer quantity) {
		Product product = productService.getProductById(productId);
		Cart cart = getCartByUserAndProduct(user, product);
		if(cart==null) throw new NotFoundException("San pham chua them vao gio hang");
		cart.setQuantiny(quantity);
		cartRepository.save(cart);
		
		
		CartDTO cartDTO = new CartDTO();
		cartDTO.CartDTOMapping(cart);
		return cartDTO;
	}
	
	public void removeCart(User user, Integer productId) {
		Cart cart = getCartByUserAndProduct(user, new Product(productId));
		if(cart==null) throw new NotFoundException("San pham chua them vao gio hang");
		cartRepository.dedeleteByCustomerAndProduct(user.getId(), productId);
	}
	
	public void deleteCart(User user) {
		cartRepository.dedeleteByCustomer(user.getId());
	}
	
	public float getTotalPriceInCart(User user) {
		List<Cart> listCart = cartRepository.findByUser(user);
		float totalPrice =0;
		if(listCart.size()>0) {
			for(Cart cart : listCart) {
				totalPrice+=cart.getQuantiny()*cart.getProduct().getPrice();
			}
			return totalPrice;
		}else return 0;
	}
}
