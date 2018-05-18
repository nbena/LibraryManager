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
