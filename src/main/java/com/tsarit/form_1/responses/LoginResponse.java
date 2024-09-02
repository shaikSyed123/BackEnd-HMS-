package com.tsarit.form_1.responses;

import org.springframework.stereotype.Component;

@Component
public class LoginResponse {
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
      
}
