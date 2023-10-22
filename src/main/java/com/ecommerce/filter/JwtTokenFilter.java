package com.ecommerce.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecommerce.compoments.JwtTokenUtils;
import com.ecommerce.entity.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter  extends OncePerRequestFilter{

	@Autowired 
	UserDetailsService userDetailsService;
	@Autowired
	JwtTokenUtils jwtTokenUtils;
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {

		if(isBypassToken(request)==true) {
			filterChain.doFilter(request, response);
			return;
		}
		try {
			final String authHeader = request.getHeader("Authorization");
			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
			}
			
			final String token = authHeader.substring(7);
			final String phoneNumber = jwtTokenUtils.extractPhoneNumber(token);
			if(phoneNumber!=null &&
					SecurityContextHolder.getContext().getAuthentication()==null) {
				User userDetails= (User) userDetailsService.loadUserByUsername(phoneNumber);
				if(jwtTokenUtils.validateToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken authenticationToken=
							new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
					
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}
			filterChain.doFilter(request, response);
			
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
		}
		
				
	}
	
	private boolean	 isBypassToken(HttpServletRequest request) {

		final List<Pair<String, String>> bypassTokens = Arrays.asList(
				Pair.of("api/v1/products", "GET"),
				Pair.of("api/v1/category", "GET"),
				Pair.of("api/v1/user", "POST"),
				Pair.of("api/v1/user/login", "POST")
				);
		
		for(Pair<String, String> bypassToken : bypassTokens ) {
			if(request.getServletPath().contains(bypassToken.getFirst())&&
					request.getMethod().equals(bypassToken.getSecond())) {
				return true;
			}
			
		}
		return false;
	}

}
