package com.ronanski11.demo.webservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class UserCredentials {
	@Id
	@NotEmpty(message = "User's name cannot be empty.")
	String username;
	@NotEmpty(message = "Password cannot be empty.")
	String password;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}