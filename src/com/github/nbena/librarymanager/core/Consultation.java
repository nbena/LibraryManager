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

public class Consultation implements IDble{
	

	private CopyForConsultation copy;
	private User user;
	private OffsetDateTime start;
	private OffsetDateTime end;
	private int ID;
	
	
	public Consultation( User user, CopyForConsultation copy) {
		this.copy = copy;
		this.user = user;
	}

	public Consultation(int ID, CopyForConsultation copy, User user, OffsetDateTime start, OffsetDateTime end) {
		this.copy = copy;
		this.user = user;
		this.start = start;
		this.end = end;
		this.ID = ID;
	}

	public Consultation() {
	}

	public CopyForConsultation getCopy() {
		return copy;
	}

	public void setCopy(CopyForConsultation copy) {
		this.copy = copy;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public OffsetDateTime getStart() {
		return start;
	}

	public void setStart(OffsetDateTime start) {
		this.start = start;
	}

	public OffsetDateTime getEnd() {
		return end;
	}

	public void setEnd(OffsetDateTime end) {
		this.end = end;
	}

}
