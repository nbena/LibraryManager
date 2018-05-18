package com.github.nbena.librarymanager.core;

import java.time.LocalDateTime;

public class Loan {
	
	private User user;
	private Copy copy;
	private LocalDateTime start;
	private LocalDateTime end;
	private boolean active;
	private LocalDateTime restitutionDate;
	private boolean renewAvailable;
	
	public static final int ONE_LOAN_MAX_DURATION_MONTH = 4;

	public Loan(User user, Copy copy, boolean setOthers) {
		this.user = user;
		this.copy = copy;
		
		if (setOthers){
			this.start = LocalDateTime.now();
			this.end = this.start.plusMonths(4);
			this.active = true;
			this.renewAvailable = true;
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Copy getCopy() {
		return copy;
	}

	public void setCopy(Copy copy) {
		this.copy = copy;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public LocalDateTime getRestitutionDate() {
		return restitutionDate;
	}

	public void setRestitutionDate(LocalDateTime restitutionDate) {
		this.restitutionDate = restitutionDate;
	}

	public boolean isRenewAvailable() {
		return renewAvailable;
	}

	public void setRenewAvailable(boolean renewAvailable) {
		this.renewAvailable = renewAvailable;
	}
	
	
	
	

}
