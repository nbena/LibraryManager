package com.github.nbena.librarymanager.core;

public interface Loginable {
	
	public String getEmail();
	
	public String getHashedPassword();
	
	public void setEmail(String email);
	
	public void setHashedPassword(String hashedPassword);

}
