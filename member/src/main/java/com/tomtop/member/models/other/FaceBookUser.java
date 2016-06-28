package com.tomtop.member.models.other;

import java.io.Serializable;

public class FaceBookUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String email;
	private String firstName;
	private String lastName;
	private String gender;
	private String link;
	private Integer timezone;
	private Boolean verified;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
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

	public void setTimezone(Integer timezone) {
		this.timezone = timezone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", email=" + email + ", firstName="
				+ firstName +  ", lastName="
				+ lastName + ", gender=" + gender + ", link=" + link
				+ ", name=" + name + ", timezone="
				+ timezone  + ", verified="
				+ verified + "]";
	}

}
