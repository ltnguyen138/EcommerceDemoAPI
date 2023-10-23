package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Utility;
import com.ecommerce.compoments.JwtTokenUtils;
import com.ecommerce.dto.CartDTO;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.User;
import com.ecommerce.service.CartService;
import com.ecommerce.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "api/v1/carts")
public class CartRestController {
	
	@Autowired
	CartService cartService;
	@Autowired
	UserService userService;
	
	
	@GetMapping	
	public ResponseEntity<?> viewCart(HttpServletRequest request){
		
		User user = getAuthenticatedUser(request);
		List<CartDTO> listCart = cartService.getCartByUser(user);
		return ResponseEntity.ok(listCart);
	}
	
	@PostMapping("/add/{productId}/{quantity}")
	public ResponseEntity<?> addProduct(HttpServletRequest request,
			@PathVariable("productId") Integer productId, @PathVariable("quantity") Integer quantity){
		
		User user = getAuthenticatedUser(request);
		CartDTO cartDTO = cartService.addProduct(user, productId, quantity);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(cartDTO);
	}
	
	@PutMapping("/update/{productId}/{quantity}")
	public ResponseEntity<?> updateProduct(HttpServletRequest request,
			@PathVariable("productId") Integer productId, @PathVariable("quantity") Integer quantity){
		
		User user = getAuthenticatedUser(request);
		CartDTO cartDTO = cartService.updateProduct(user, productId, quantity);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(cartDTO);
	}
	
	@DeleteMapping("/remove/{productId}")
	public ResponseEntity<?> removeProduct(HttpServletRequest request,
			@PathVariable("productId") Integer productId){
		
		User user = getAuthenticatedUser(request);
		cartService.removeCart(user, productId);
		
		return ResponseEntity.ok("Xoa thanh cong");
	}
	
	@GetMapping("/total_price")
	public ResponseEntity<?> totalPriceInCart(HttpServletRequest request){
		
		User user = getAuthenticatedUser(request);
		float totalPrice = cartService.getTotalPriceInCart(user);
		
		return ResponseEntity.ok(totalPrice);
	}
	
	private User getAuthenticatedUser(HttpServletRequest request  ) {
		
		String phoneNumber = Utility.getPhoneNumberFromAuthenticated(request);
		User user = userService.getUserByPhoneNumber(phoneNumber);
		return user;
		
	}
}
