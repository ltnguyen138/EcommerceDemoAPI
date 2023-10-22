package com.ecommerce.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name = "social_accounts")
public class SocialAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "provider", length = 20, nullable = false)
	private String provider;
	
	@Column(name = "provider_id", length = 50, nullable = false)
	private String providerId;
	
	@Column(name = "email", length = 150, nullable = false)
	private String email;
	
	@Column(name = "name", length = 150, nullable = false)
	private String name;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public SocialAccount(String provider, String providerId, String email, String name, User user) {
		this.provider = provider;
		this.providerId = providerId;
		this.email = email;
		this.name = name;
		this.user = user;
	}

	public SocialAccount() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
		
}
