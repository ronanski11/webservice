package com.ronanski11.demo.webservice.model;

import Validation.EmailConstraint;
import Validation.PersonConstraint;
import Validation.PhoneConstraint;
import Validation.UsernameConstraint;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserInfo {
	@Id
	@UsernameConstraint
	String username;
	@EmailConstraint
	String email;
	@PhoneConstraint
	String phoneNumber;
	@PersonConstraint
	String firstname;
	@PersonConstraint
	String lastname;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
}
