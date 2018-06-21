/*  LibraryManager
    Copyright (C) 2018 nbena

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
    */

package com.github.nbena.librarymanager.core;

public class User extends Librarian implements IDble {
	
	protected String name;
	protected String surname;
	// private String hashedPassword;
	// private String email;
	
	public User(){}
	

//	public String getEmail() {
//		return email;
//	}
//
//
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
	
	

	public User(String name, String surname, String hashedPassword, String email) {
		this.name = name;
		this.surname = surname;
		this.hashedPassword = hashedPassword;
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
	
	public String toString(){
		return this.email;
	}


//	public int getID() {
//		return ID;
//	}
//
//
//	public void setID(int iD) {
//		ID = iD;
//	}


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


//	public String getHashedPassword() {
//		return hashedPassword;
//	}
//
//
//	public void setHashedPassword(String hashedPassword) {
//		this.hashedPassword = hashedPassword;
//	}
	
	public void hashPassword(){
		
	}
	
	
	

}
