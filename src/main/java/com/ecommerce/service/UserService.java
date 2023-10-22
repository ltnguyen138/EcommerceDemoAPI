package com.ecommerce.service;

import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.compoments.JwtTokenUtils;
import com.ecommerce.dto.UserDTO;
import com.ecommerce.entity.User;
import com.ecommerce.exception.DuplicateRecordException;
import com.ecommerce.exception.NotFoundException;
import com.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired 
	PasswordEncoder passwordEncoder;
	@Autowired
	JwtTokenUtils jwtTokenUtils;
	@Autowired
	AuthenticationManager authenticationManager;
	public List<User> getAllUser() {
		return  (List<User>) userRepository.findAll();
	}
	
	public UserDTO saveUser(User userrq) {
		for(User user : getAllUser()) {
			if(user.getPhoneNumber().equals(userrq.getPhoneNumber())){
				throw new DuplicateRecordException("So dien thoai da ton tai");
			}
		}
		String password = userrq.getPassword();
		String encodedPassword = passwordEncoder.encode(password);
		userrq.setPassword(encodedPassword);
		userrq.setCreate_at(new Date());
		userRepository.save(userrq);
		UserDTO dto = new UserDTO();
		dto.UserDTOMapping(userrq);
		return dto;
	}
	
	public User getUserById(Integer id) {
		return userRepository.findById(id).orElseThrow(()-> new NotFoundException("Khong tim thay user co id la "+id));
	}
	
	public User  updateUser(Integer id, User userrq) {
		User userDb = getUserById(id);
		
		for(User user : getAllUser()) {
			if(user.getPhoneNumber().equals(userrq.getPhoneNumber())
					&& !user.getPhoneNumber().equals(userDb.getPhoneNumber())){
				throw new DuplicateRecordException("So dien thoai da ton tai");
			}
		}
		userDb.setFullName(userrq.getFullName());
		userDb.setAddress(userrq.getAddress());
		userDb.setPhoneNumber(userrq.getPhoneNumber());
		userDb.setRoles(userrq.getRoles());
		userDb.setUpdate_at(new Date());
		if(userrq.getPassword()!=null) {
			String password = userrq.getPassword();
			String encodedPassword = passwordEncoder.encode(password);
			userDb.setPassword(encodedPassword);
		}
		return userRepository.save(userDb);
	}
	
	public void deleteUser(Integer id) {
		User userDb = getUserById(id);
		userRepository.delete(userDb);
	}
	
	public User getUserByPhoneNumber(String phoneNumber) {
		return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(()->new NotFoundException("Khong tim thay user"));
	}
	
	public String login( String phoneNumber,String password) {
		User user = getUserByPhoneNumber(phoneNumber);
		
		if(!passwordEncoder.matches(password, user.getPassword())) {
			throw new BadCredentialsException("So dien thoai hoac mat khau khong chinh xac");
		}
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				phoneNumber, password, user.getAuthorities());
		authenticationManager.authenticate(authenticationToken);
		return jwtTokenUtils.generateToken(user);
	}
}
