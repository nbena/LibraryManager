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

public abstract class AbstractReservationWithBook extends AbstractReservation {
	
	protected Copy copy;
	protected boolean done;
	
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

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

}
