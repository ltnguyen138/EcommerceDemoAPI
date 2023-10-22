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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.UserDTO;
import com.ecommerce.dto.UserLoginDTO;
import com.ecommerce.entity.User;
import com.ecommerce.service.UserService;



@RestController
@RequestMapping(path = "api/v1/user")
public class UserRestController {
	
	@Autowired
	UserService userService;
	
	@PostMapping
	public ResponseEntity<?> saveUser(@RequestBody User userrq) {
		UserDTO userDTO = userService.saveUser(userrq);
		return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
	}
	
	@GetMapping
	public ResponseEntity<?> getUser(){
		List<User> listUser = userService.getAllUser();
		return ResponseEntity.ok(listUser);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") Integer id,@RequestBody User userrq ){
		User user = userService.updateUser(id, userrq);
		return ResponseEntity.ok(user);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id){
		userService.deleteUser(id);
		return ResponseEntity.ok("Xoa thanh cong user: "+id);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO){
		try {
			String token = userService.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
			return ResponseEntity.ok(token);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
