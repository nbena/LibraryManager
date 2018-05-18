package com.github.nbena.librarymanager.core;

public class InternalUser extends User {

	public InternalUser(int iD, String name, String surname, String email, String hashedPassword) {
		super(iD, name, surname, email, hashedPassword);
	}

	public InternalUser(int iD, String hashedPassword) {
		super(iD, hashedPassword);
	}

	public InternalUser(int iD) {
		super(iD);
	}
	

	

}
