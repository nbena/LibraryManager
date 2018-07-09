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

package com.github.nbena.librarymanager.gui;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.github.nbena.librarymanager.core.AbstractReservation;
import com.github.nbena.librarymanager.core.ConsultationReservation;
import com.github.nbena.librarymanager.core.Copy;
import com.github.nbena.librarymanager.core.CopyForConsultation;
import com.github.nbena.librarymanager.core.InternalUser;
import com.github.nbena.librarymanager.core.Loan;
import com.github.nbena.librarymanager.core.LoanReservation;
import com.github.nbena.librarymanager.core.ReservationException;
import com.github.nbena.librarymanager.core.SeatReservation;
import com.github.nbena.librarymanager.core.User;
import com.github.nbena.librarymanager.man.LibraryManager;

public class UserModel extends AbstractModel {

	public UserModel(LibraryManager manager) {
		super(manager);
	}
	
	
	public boolean authenticate(User user){
		User u = super.manager.authenticateUser(user);
		boolean result = false;
		if (u!=null){
			super.setUser(user);
			result = true;
		}
		return result;
	}
	
	public void setUser(User user){
		super.setUser(user);
	}
	
	public SeatReservation reserveSeat(LocalDate date) throws ReservationException, SQLException{
		return super.manager.tryReserveSeat((InternalUser) super.user, date);
	}
	
//	public ConsultationReservation reserveConsultation(Book book, LocalDate date) throws ReservationException, SQLException{
//		return super.manager.tryReserveConsultation((InternalUser) super.user, book, date);
//	}
//	
	public ConsultationReservation reserveConsultation(CopyForConsultation copy,
			LocalDate date) throws ReservationException, SQLException{
		return super.manager.tryReserveConsultation((InternalUser) super.user, copy, date);
	}
	
	public void cancelReservation(AbstractReservation reservation) throws SQLException{
		super.manager.cancelReservation(reservation);
	}
	
	public LoanReservation reserveLoan(Copy copy) throws SQLException, ReservationException{
		return super.manager.tryReserveLoan((InternalUser) super.user, copy);
	}
	
	public List<SeatReservation> getSeatsReservations() throws SQLException{
		return super.manager.getSeatReservationByUser((InternalUser) super.user);
	}
	
	public List<ConsultationReservation> getConsultationReservation() throws SQLException{
		return super.manager.getConsultationReservationByUser((InternalUser) super.user, null);
	}
	
	public List<LoanReservation> getLoanReservation() throws SQLException{
		return super.manager.getLoanReservationByUser((InternalUser) super.user);
	}
	
	public List<Loan> getActiveLoan() throws SQLException{
		return super.manager.getLoanByUser(super.user, false);
	}
	
	public List<Copy> search(String title, String [] authors, int year, String mainTopic,
			String phouse) throws SQLException{
		return super.manager.search(title, authors, year, mainTopic, phouse);
	}
	
	public void deregister() throws SQLException{
		super.manager.deregisterUser(this.user);
	}
	
	public Loan renewLoan(Loan loan) throws SQLException, ReservationException{
		boolean res = super.manager.tryRenewLoan(loan);
		if (!res){
			throw new ReservationException("Fail to renew");
		}
		return loan;
	}


}
