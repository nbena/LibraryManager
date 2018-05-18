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

public class SeatReservation {
	
	private int ID;
	private OffsetDateTime timestamp;
	private OffsetDateTime reservationDate;
	private InternalUser user;
	private Seat seat;

	public SeatReservation(int iD, OffsetDateTime timestamp, OffsetDateTime reservationDate, InternalUser user,
			Seat seat) {
		ID = iD;
		this.timestamp = timestamp;
		this.reservationDate = reservationDate;
		this.user = user;
		this.seat = seat;
	}

	public SeatReservation(OffsetDateTime reservationDate, InternalUser user, Seat seat) {
		this.timestamp = OffsetDateTime.now();
		this.reservationDate = reservationDate;
		this.user = user;
		this.seat = seat;
	}

	public OffsetDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(OffsetDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public OffsetDateTime getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(OffsetDateTime reservationDate) {
		this.reservationDate = reservationDate;
	}

	public InternalUser getUser() {
		return user;
	}

	public void setUser(InternalUser user) {
		this.user = user;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
	

}
