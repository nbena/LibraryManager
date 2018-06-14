package com.github.nbena.librarymanager.core;

public interface Loginable extends Emailable {
	
	// public String getEmail();
	
	// public void setEmail(String email);
	
	public String getHashedPassword();
	
	public void setHashedPassword(String hashedPassword);

}
