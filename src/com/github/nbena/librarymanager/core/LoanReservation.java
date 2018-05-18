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
		return new Loan(this.user, this.copy, true);
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
