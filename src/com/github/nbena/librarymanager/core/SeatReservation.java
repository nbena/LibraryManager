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

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class SeatReservation extends AbstractReservation {
	
	// private int ID;
	// private OffsetDateTime timestamp;
	private LocalDate reservationDate;
	// private InternalUser user;
	private Seat seat;

	public SeatReservation(int iD, OffsetDateTime timestamp, LocalDate reservationDate, InternalUser user,
			Seat seat) {
		super.ID = iD;
		super.timestamp = timestamp;
		this.reservationDate = reservationDate;
		super.user = user;
		this.seat = seat;
	}

	public SeatReservation(InternalUser user, LocalDate reservationDate, Seat seat) {
		super.timestamp = OffsetDateTime.now();
		this.reservationDate = reservationDate;
		super.user = user;
		this.seat = seat;
	}

	public OffsetDateTime getTimestamp() {
		return super.timestamp;
	}

	public void setTimestamp(OffsetDateTime timestamp) {
		super.timestamp = timestamp;
	}

	public LocalDate getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(LocalDate reservationDate) {
		this.reservationDate = reservationDate;
	}

	public InternalUser getUser() {
		return super.user;
	}

	public void setUser(InternalUser user) {
		super.user = user;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public int getID() {
		return super.ID;
	}

	public void setID(int iD) {
		super.ID = iD;
	}
	
	

}
