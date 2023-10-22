package com.ecommerce.compoments;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ecommerce.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {
	
	private final long expiration= 2592000;
	
    private String secretKey = 
            "MIICXgIBAAKBgQCtrKVnwse4anfX+JzM7imShXZUC+QBXQ11A5bOWwHFkXc4nTfE==";
           
	
	public String generateToken(User user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("phoneNumber", user.getPhoneNumber());
		try {
			String token = Jwts.builder()
					.setClaims(claims)
					.setSubject(user.getPhoneNumber())
					.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
					.signWith(getSignInKey(), SignatureAlgorithm.HS256)
					.compact();
			return token;
		}catch (Exception e) {
			System.out.print("Khong tao duoc jwt token vi: "+e.getMessage());
			return null;
		}
		
	}
	 
	private Date getDate() {
		 Date currentTime = new Date();
		 
		 System.out.print(currentTime);
		 Date newTime = new Date(currentTime.getTime() + expiration * 1000L);
		 return newTime;
	}
	private Key getSignInKey() {
		
		byte[] bytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(bytes);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public <T> T extractClaims(String token, Function<Claims, T> claimsResoler) {
		final Claims claims = this.extractAllClaims(token);
		return claimsResoler.apply(claims);
		
	}
	
	public boolean isTokenExpired(String token) {
	        Date expirationDate = this.extractClaims(token, Claims::getExpiration);
	        return expirationDate.before(new Date());
	}
	public String extractPhoneNumber(String token) {
	       return extractClaims(token, Claims::getSubject);
	}
	 
	public boolean validateToken(String token, UserDetails userDetails) {
        String phoneNumber = extractPhoneNumber(token);
        return (phoneNumber.equals(userDetails.getUsername()))
                && !isTokenExpired(token);
    }
	 
}
