package com.qa.data;

public class Users {

	private String username;
	private String password;
	private String sign;
	
	public Users(){
		super();
	}
	
	public Users(String username, String password ,String sign){
		super();
		this.username = username;
		this.password = password;
		this.sign = sign;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void  setUsername(String username){
		this.username = username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void  setPassword(String password){
		this.password = password;
	}
	public String getSign(){
		return sign;
	}
	
	public void  setSign(String sign){
		this.sign = sign;
	}
}
