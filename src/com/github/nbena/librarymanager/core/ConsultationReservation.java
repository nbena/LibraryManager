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

public class ConsultationReservation extends AbstractReservation {
	
	// private int ID;
	
	// private InternalUser user;
	private CopyForConsultation copy;
	private Seat seat;
	
	private LocalDate reservationDate;
	// private OffsetDateTime timestamp;

	
	public ConsultationReservation(int ID, InternalUser user, CopyForConsultation copy, Seat seat,
			LocalDate reservationDate, OffsetDateTime timestamp) {
		// super.ID = iD;
		// super.user = user;
		super(ID, user, timestamp);
		this.copy = copy;
		this.seat = seat;
		this.reservationDate = reservationDate;
		// super.timestamp = timestamp;
	}

	public ConsultationReservation(InternalUser user, LocalDate reservationDate, CopyForConsultation copy, Seat seat
			) {
		super(user, OffsetDateTime.now());
		// super.user = user;
		this.copy = copy;
		this.seat = seat;
		this.reservationDate = reservationDate;
		// super.timestamp = OffsetDateTime.now();
	}

	public CopyForConsultation getCopy() {
		return copy;
	}
	public void setCopy(CopyForConsultation copy) {
		this.copy = copy;
	}
	public Seat getSeat() {
		return seat;
	}
	public void setSeat(Seat seat) {
		this.seat = seat;
	}
	public LocalDate getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(LocalDate reservationDate) {
		this.reservationDate = reservationDate;
	}
	
	public boolean isDone(){
		return this.reservationDate.isAfter(LocalDate.now());
	}

}
