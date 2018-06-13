package com.github.nbena.librarymanager.core;

public class Librarian implements IDble{
	
	protected int ID;
	protected String email;
	protected String hashedPassword;
	
	
	public Librarian(){}
	
	
	public Librarian(int ID, String email, String hashedPassword) {
		this.ID = ID;
		this.email = email;
		this.hashedPassword = hashedPassword;
	}


	public int getID() {
		return ID;
	}


	public void setID(int ID) {
		this.ID = ID;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getHashedPassword() {
		return hashedPassword;
	}


	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}
	
	
	
	

}
