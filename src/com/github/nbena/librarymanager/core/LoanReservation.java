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

import java.time.OffsetDateTime;

public class LoanReservation {
	
	private int ID;
	private OffsetDateTime timestamp;
	private InternalUser user;
	private Copy copy;
	
	

	public LoanReservation(int iD, OffsetDateTime timestamp, InternalUser user, Copy copy) {
		ID = iD;
		this.timestamp = timestamp;
		this.user = user;
		this.copy = copy;
	}

	public LoanReservation(InternalUser user, Copy copy, boolean setTimestamp) {
		if (setTimestamp){
			this.timestamp = OffsetDateTime.now();
		}
		this.user = user;
		this.copy = copy;
	}

	public OffsetDateTime getTimestamp() {
		return timestamp;
	}
	
	public InternalUser getUser() {
		return user;
	}

	public void setUser(InternalUser user) {
		this.user = user;
	}

	public Copy getCopy() {
		return copy;
	}

	public void setCopy(Copy copy) {
		this.copy = copy;
	}
	
	
	public Loan createLoan(){
		return new Loan(this.user, this.copy);
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public void setTimestamp(OffsetDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
