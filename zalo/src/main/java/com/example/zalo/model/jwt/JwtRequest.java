package com.example.zalo.model.jwt;

import java.io.Serializable;

public class JwtRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private String phoneNumber;
	private String password;

	/**
	 * Need default constructor for JSON Parsing
	 */
	public JwtRequest() {
		// nop
	}

	public JwtRequest(String phoneNumber, String password) {
		this.setPhoneNumber(phoneNumber);
		this.setPassword(password);
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}