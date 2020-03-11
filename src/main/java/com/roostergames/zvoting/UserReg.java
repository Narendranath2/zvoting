package com.roostergames.zvoting;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class UserReg {

	@Id
	private String _id;
	@NotNull(message = "Name should not be empty")
	private String firstName;
	private String lastName;
	@NotNull(message = "Should be a valid email")
	private String email;
	@NotNull(message = "Should be a valid phone number")
	@Size(min=10,max=10)
	private String phone;
	@NotNull(message = "Password must contain atleast 6 characters")
	@Size(min=6, max=30, message="Password must contain atleast 6 characters")
	private String password;
	@NotNull(message = "Country should not be empty")
	private String country;
	@NotNull(message = "City should not be empty")
	private String city;
	@NotNull(message = "Adhar number should not be empty")
	private String adharId;
	@NotNull(message = "Voter Id should not be empty")
	private String voterId;
	public UserReg(String firstName, String lastName, String email, String phone, String country, String city,
			String adharId, String voterId, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.country = country;
		this.city = city;
		this.adharId = adharId;
		this.voterId = voterId;
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getPassword() {
		return password;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAdharId() {
		return adharId;
	}
	public void setAdharId(String adharId) {
		this.adharId = adharId;
	}
	public String getVoterId() {
		return voterId;
	}
	public void setVoterId(String voterId) {
		this.voterId = voterId;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
