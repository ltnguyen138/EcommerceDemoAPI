package com.ecommerce.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;







@Entity
@Table(name = "users")
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "full_name", length = 128, nullable = false)
	private String fullName;
	
	@Column(name = "phone_number", length = 10, nullable = false)
	private String phoneNumber;
	
	@Column(name = "address", length = 128, nullable = true)
	private String address;
	
	@Column(name = "password", length = 64, nullable = true)
	private String password;
	
	@Column(name = "create_at", nullable = true)
	private Date create_at;
	
	@Column(name = "update_at", nullable = true)
	private Date update_at;
	
	private boolean enabled;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns=@JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="role_id")
			)
	private Set<Role> roles = new HashSet<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Token> token = new HashSet<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Token> socialAccounts = new HashSet<>();

	

	public User(Integer id) {
		this.id = id;
	}



	public User(String firstName, String phoneNumber, String address, String password, Date create_at, Date update_at,
			boolean enabled, Set<Role> roles, Set<Token> token, Set<Token> socialAccounts) {
		this.fullName = firstName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.password = password;
		this.create_at = create_at;
		this.update_at = update_at;
		this.enabled = enabled;
		this.roles = roles;
		this.token = token;
		this.socialAccounts = socialAccounts;
	}
	
	
	
	public User(String firstName, String phoneNumber, String address, String password, boolean enabled,
			Set<Role> roles) {
		this.fullName = firstName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.password = password;
		this.enabled = enabled;
		this.roles = roles;
	}



	public Set<Role> getRoles() {
		return roles;
	}



	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}



	public User() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String firstName) {
		this.fullName = firstName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreate_at() {
		return create_at;
	}

	public void setCreate_at(Date create_at) {
		this.create_at = create_at;
	}

	public Date getUpdate_at() {
		return update_at;
	}

	public void setUpdate_at(Date update_at) {
		this.update_at = update_at;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Token> getToken() {
		return token;
	}

	public void setToken(Set<Token> token) {
		this.token = token;
	}

	public Set<Token> getSocialAccounts() {
		return socialAccounts;
	}

	public void setSocialAccounts(Set<Token> socialAccounts) {
		this.socialAccounts = socialAccounts;
	}



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authories = new ArrayList<>();
		for (Role role : roles) {
			authories.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authories;
	}



	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return phoneNumber;
	}



	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}



	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}



	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
}
	