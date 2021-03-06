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

public class InternalUser extends User {

	public InternalUser(int ID, String name, String surname, String email, String hashedPassword) {
		super(ID, name, surname, email, hashedPassword);
	}

	public InternalUser(int ID, String hashedPassword) {
		super(ID, hashedPassword);
	}

	public InternalUser(String name, String surname, String hashedPassword, String email) {
		super(name, surname, hashedPassword, email);
	}

	public InternalUser(int ID) {
		super(ID);
	}

	public InternalUser() {
	}
	

	

}
