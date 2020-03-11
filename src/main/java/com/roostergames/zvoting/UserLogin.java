package com.roostergames.zvoting;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserLogin {
	@NotNull(message = "Username should not be empty")
	private String username;
	@NotNull(message = "Password should not be empty")
	private String password;
	
	public UserLogin(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
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
