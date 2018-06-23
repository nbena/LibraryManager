package com.github.nbena.librarymanager.core;

import java.time.OffsetDateTime;

public abstract class AbstractReservationWithBook extends AbstractReservation {
	
	protected Copy copy;
	
	public void setCopy(Copy copy){
		this.copy = copy;
	}
	
	public Copy getCopy(){
		return this.copy;
	}
	
	public AbstractReservationWithBook(){
		super();
	}
	
	public AbstractReservationWithBook(int ID, InternalUser user, Copy copy, OffsetDateTime timestamp){
		super(ID, user, timestamp);
		this.copy = copy;
	}
	
	public AbstractReservationWithBook(InternalUser user, OffsetDateTime timestamp){
		super(user, timestamp);
	}

}
