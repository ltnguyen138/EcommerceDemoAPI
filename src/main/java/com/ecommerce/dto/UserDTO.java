package com.ecommerce.dto;

import java.util.Date;

import com.ecommerce.entity.User;

public class UserDTO {
	private Integer id;
	private String fullName;
	private String phoneNumber;
	private String address;

	private Date create_at;
	private Date update_at;
	private boolean enabled;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
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
	
	public void UserDTOMapping(User user) {
		if(user!=null) {
			id= user.getId();
			fullName = user.getFullName();
			address = user.getAddress();
			phoneNumber = user.getPhoneNumber();
			create_at = user.getCreate_at();
			update_at = user.getUpdate_at();
			enabled = user.isEnabled();
		}
		
	}
}
