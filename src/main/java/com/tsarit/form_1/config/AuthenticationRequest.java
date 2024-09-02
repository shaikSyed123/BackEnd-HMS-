package com.tsarit.form_1.config;

import org.antlr.v4.runtime.misc.NotNull;

public class AuthenticationRequest {
	  @NotNull
	private String emailid;
	  @NotNull
	private String password;
	
	
	public String getEmailid() {
		return emailid;
	}


	public AuthenticationRequest(String emailid, String password) {
		 if (emailid == null || emailid.isEmpty() || password == null || password.isEmpty()) {
		        throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
		    }
		this.emailid = emailid;
		this.password = password;
	}


	public String getPassword() {
		return password;
	}
	
}
