package com.github.nbena.librarymanager.core;

public class User {
	
	private int ID;
	private String name;
	private String surname;
	private String hashedPassword;
	private String email;
	

	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public User(int iD, String name, String surname, String email, String hashedPassword) {
		ID = iD;
		this.name = name;
		this.surname = surname;
		this.hashedPassword = hashedPassword;
		this.email = email;
	}
	
	

	public User(int iD, String hashedPassword) {
		ID = iD;
		this.hashedPassword = hashedPassword;
	}



	public User(int iD) {
		ID = iD;
	}



	public int getID() {
		return ID;
	}


	public void setID(int iD) {
		ID = iD;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getHashedPassword() {
		return hashedPassword;
	}


	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}
	
	
	

}
