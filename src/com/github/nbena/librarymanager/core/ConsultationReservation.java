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

import java.time.LocalDateTime;

public class ConsultationReservation {
	
	private int ID;
	
	private InternalUser user;
	private CopyForConsultation copy;
	private Seat seat;
	
	private LocalDateTime reservationDate;
	private LocalDateTime timestamp;
	
	public ConsultationReservation(InternalUser user, CopyForConsultation copy, Seat seat,
			LocalDateTime reservationDate) {
		this.user = user;
		this.copy = copy;
		this.seat = seat;
		this.reservationDate = reservationDate;
		this.timestamp = LocalDateTime.now();
	}

	public InternalUser getUser() {
		return user;
	}
	public void setUser(InternalUser user) {
		this.user = user;
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
	public LocalDateTime getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(LocalDateTime reservationDate) {
		this.reservationDate = reservationDate;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
