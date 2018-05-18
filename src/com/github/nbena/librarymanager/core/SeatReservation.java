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
