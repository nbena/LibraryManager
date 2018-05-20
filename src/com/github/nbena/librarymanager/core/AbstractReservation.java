package com.github.nbena.librarymanager.core;

import java.time.OffsetDateTime;

public abstract class AbstractReservation implements IDble{
	
	protected OffsetDateTime timestamp;
	protected int ID;
	protected InternalUser user;
	
	public abstract boolean isDone();

	protected AbstractReservation(int iD, InternalUser user, OffsetDateTime timestamp) {
		ID = iD;
		this.user = user;
		this.timestamp = timestamp;
	}

	protected AbstractReservation(InternalUser user, OffsetDateTime timestamp) {
		this.user = user;
		this.timestamp = timestamp;
	}
	
	protected AbstractReservation(){}

	public OffsetDateTime getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(OffsetDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getID() {
		return this.ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public InternalUser getUser() {
		return user;
	}

	public void setUser(InternalUser user) {
		this.user = user;
	}
	
	
	
}
