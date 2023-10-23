package com.ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.compoments.JwtTokenUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class Utility {

	
	
	public static String getPhoneNumberFromAuthenticated(HttpServletRequest request) {
		final String authHeader = request.getHeader("Authorization");
		
		
		final String token = authHeader.substring(7);
		JwtTokenUtils jwtTokenUtils = new JwtTokenUtils();
		String phoneNumber =  jwtTokenUtils.extractPhoneNumber(token);
		return phoneNumber;
	}
}
