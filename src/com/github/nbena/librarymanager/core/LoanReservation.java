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

public class LoanReservation extends AbstractReservation {
	
	// private int ID;
	// private OffsetDateTime timestamp;
	// private InternalUser user;
	private Copy copy;
	private boolean done;
	
	

	public LoanReservation(int ID, InternalUser user, Copy copy, OffsetDateTime timestamp) {
		// super.ID = iD;
		// super.timestamp = timestamp;
		// super.user = user;
		super(ID, user, timestamp);
		this.copy = copy;
	}

	public LoanReservation(InternalUser user, Copy copy) {
		super.timestamp = OffsetDateTime.now();
		super.user = user;
		this.copy = copy;
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


	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
	
	
}
