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

public class Loan implements IDble {

	private User user;
	private Copy copy;
	private LocalDate start;
	private LocalDate end;
	private boolean active;
	private LocalDate restitutionDate;
	private boolean renewAvailable;
	int ID;

	public static final int MAX_MONTHS_SINGLE_LOAN_DURATION = 2;

	public Loan(User user, Copy copy){
		this.user = user;
		this.copy = copy;
		
		this.start = LocalDate.now();
		this.end = LocalDate.now().plusMonths(MAX_MONTHS_SINGLE_LOAN_DURATION);
		this.restitutionDate = null;
		
		this.renewAvailable = true;
	}

	public Loan(int ID, User user, Copy copy, LocalDate start, LocalDate end, boolean active, LocalDate restitutionDate,
			boolean renewAvailable) {
		this.user = user;
		this.copy = copy;
		this.start = start;
		this.end = end;
		this.active = active;
		this.restitutionDate = restitutionDate;
		this.renewAvailable = renewAvailable;
		this.ID = ID;
	}



	public /*@ pure @*/ LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	public /*@ pure @*/ boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public /*@ pure @*/ LocalDate getRestitutionDate() {
		return this.restitutionDate;
	}

	public void setRestitutionDate(LocalDate restitutionDate) {
		this.restitutionDate = restitutionDate;
	}

	public /*@ pure @*/ int getID() {
		return this.ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

//	public Loan(User user, Copy copy, boolean setOthers) {
//		this.user = user;
//		this.copy = copy;
//
//		if (setOthers){
//			this.start = LocalDateTime.now();
//			this.end = this.start.plusMonths(4);
//			this.active = true;
//			this.renewAvailable = true;
//		}
//	}

	public/*@ pure @*/ User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public /*@ pure @*/ Copy getCopy() {
		return copy;
	}

	public void setCopy(Copy copy) {
		this.copy = copy;
	}


	public /*@ pure @*/ boolean isRenewAvailable() {
		// if end date is older that now
		// then it's in late and we won't let you renew it
		return renewAvailable && !this.isInLate();
	}
	
	public boolean isInLate(){
		return this.end.isBefore(LocalDate.now());
	}

	public void setRenewAvailable(boolean renewAvailable) {
		this.renewAvailable = renewAvailable;
	}


	public /*@ pure @*/ LocalDate getStart() {
		return start;
	}



	public void setStart(LocalDate start) {
		this.start = start;
	}

//	@Override
//	public /*@ pure @*/ String toString() {
//		return "Loan [user=" + user + ", copy=" + copy + ", start=" + start + ", end=" + end + ", active=" + active
//				+ ", restitutionDate=" + restitutionDate + ", renewAvailable=" + renewAvailable + "]";
//	}


}
