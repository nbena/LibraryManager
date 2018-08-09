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

	public SeatReservation(int ID, LocalDate reservationDate, InternalUser user,
			Seat seat, OffsetDateTime timestamp) {
		// super.ID = iD;
		// super.timestamp = timestamp;
		super(ID, user, timestamp);
		this.reservationDate = reservationDate;
		// super.user = user;
		this.seat = seat;
	}

	public SeatReservation(InternalUser user, LocalDate reservationDate, Seat seat) {
		// super.timestamp = OffsetDateTime.now();
		super(user, OffsetDateTime.now());
		this.reservationDate = reservationDate;
		// super.user = user;
		this.seat = seat;
	}


	public LocalDate getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(LocalDate reservationDate) {
		this.reservationDate = reservationDate;
	}


	public Seat getSeat() {
		return this.seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}
	
	public Study createStudy(){
		Study s = new Study(this.user, this.seat);
		s.getSeat().setFree(false);
		return s;
	}

	
}
