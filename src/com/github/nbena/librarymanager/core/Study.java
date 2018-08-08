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

public class Study implements IDble {
	
	private int ID;
	private User user;
	private Seat seat;
	
	

	public Study(User user, Seat seat) {
		this.user = user;
		this.seat = seat;
	}

	public Study(int iD, User user, Seat seat) {
		ID = iD;
		this.user = user;
		this.seat = seat;
	}

	@Override
	public int getID() {
		return this.ID;
	}

	@Override
	public void setID(int ID) {
		this.ID = ID;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

}
