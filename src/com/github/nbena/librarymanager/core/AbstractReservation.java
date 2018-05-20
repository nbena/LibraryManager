package com.github.nbena.librarymanager.core;

import java.time.OffsetDateTime;

public abstract class AbstractReservation implements IDble{
	
	protected OffsetDateTime timestamp;
	protected int ID;
	protected InternalUser user;
	protected boolean done;
	
	protected AbstractReservation(){
		
	}

	public AbstractReservation(OffsetDateTime timestamp, InternalUser user, boolean done) {
		this.timestamp = timestamp;
		this.user = user;
		this.done = done;
	}
	
	

}
