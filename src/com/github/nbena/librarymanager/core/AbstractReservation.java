/*  LibraryManager a toy library manager
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

public abstract class AbstractReservation implements IDble{
	
	protected OffsetDateTime timestamp;
	protected int ID;
	protected InternalUser user;
	protected boolean done;
	

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

	public /*@ pure @*/InternalUser getUser() {
		return user;
	}

	public void setUser(InternalUser user) {
		this.user = user;
	}
	
	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	
	
	
}
