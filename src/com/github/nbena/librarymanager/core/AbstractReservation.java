package com.github.nbena.librarymanager.core;

import java.time.OffsetDateTime;

public abstract class AbstractReservation implements IDble{
	
	protected OffsetDateTime timestamp;
	protected int ID;
	protected InternalUser user;
	

}
