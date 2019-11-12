package com.revature.models;

public class User {

	private int ers_users_id;
	private String ers_username;
	private String ers_password;
	private String ers_first_name;
	private String ers_last_name;
	private String ers_email;
	private int user_role_id;
	
	public User(int ers_users_id, String ers_username, String ers_password, String ers_first_name, String ers_last_name,
			String ers_email, int user_role_id) {
		super();
		this.ers_users_id = ers_users_id;
		this.ers_username = ers_username;
		this.ers_password = ers_password;
		this.ers_first_name = ers_first_name;
		this.ers_last_name = ers_last_name;
		this.ers_email = ers_email;
		this.user_role_id = user_role_id;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getErs_users_id() {
		return ers_users_id;
	}
	public void setErs_users_id(int ers_users_id) {
		this.ers_users_id = ers_users_id;
	}
	public String getErs_username() {
		return ers_username;
	}
	public void setErs_username(String ers_username) {
		this.ers_username = ers_username;
	}
	public String getErs_password() {
		return ers_password;
	}
	public void setErs_password(String ers_password) {
		this.ers_password = ers_password;
	}
	public String getErs_first_name() {
		return ers_first_name;
	}
	public void setErs_first_name(String ers_first_name) {
		this.ers_first_name = ers_first_name;
	}
	public String getErs_last_name() {
		return ers_last_name;
	}
	public void setErs_last_name(String ers_last_name) {
		this.ers_last_name = ers_last_name;
	}
	public String getErs_email() {
		return ers_email;
	}
	public void setErs_email(String ers_email) {
		this.ers_email = ers_email;
	}
	public int getUser_role_id() {
		return user_role_id;
	}
	public void setUser_role_id(int user_role_id) {
		this.user_role_id = user_role_id;
	}
	@Override
	public String toString() {
		return "User [ers_users_id=" + ers_users_id + ", ers_username=" + ers_username + ", ers_password="
				+ ers_password + ", ers_first_name=" + ers_first_name + ", ers_last_name=" + ers_last_name
				+ ", ers_email=" + ers_email + ", user_role_id=" + user_role_id + "]";
	}
	
}
