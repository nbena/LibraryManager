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
import java.util.Arrays;

public class ConsultationReservation extends AbstractReservationWithBook {
	
	// private int ID;
	
	// private InternalUser user;
	// private CopyForConsultation copy;
	private Seat seat;
	
	private LocalDate reservationDate;
	// private OffsetDateTime timestamp;

	
	public ConsultationReservation(int ID, InternalUser user, CopyForConsultation copy, Seat seat,
			LocalDate reservationDate, OffsetDateTime timestamp) {
		// super.ID = iD;
		// super.user = user;
		super(ID, user, copy, timestamp);
		// this.copy = copy;
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

	@Override
	public CopyForConsultation getCopy() {
		return (CopyForConsultation) copy;
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
	
//	public boolean isDone(){
//		return this.reservationDate.isAfter(LocalDate.now());
//	}
	
	public String toString(){
		return "Id: "+this.ID+
				",\nTitle: "+this.getCopy().getTitle()+
				",\nUser: "+this.user.toString()+
				"\n,ReservationDate: "+this.reservationDate+
				"\n,Authors: "+Arrays.toString(this.getCopy().getAuthors())+
				"\n,Timestamp: "+this.timestamp.toString()+
				"\n,year: "+Integer.toString(this.getCopy().getYearOfPublishing())+
				"\n,mainTopic: "+this.getCopy().getMainTopic()+
				"\n,phouse: "+this.getCopy().getPublishingHouse();
	}

}
