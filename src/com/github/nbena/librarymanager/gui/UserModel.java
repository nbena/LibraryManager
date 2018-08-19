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
import com.github.nbena.librarymanager.core.LibraryManagerException;
import com.github.nbena.librarymanager.core.SeatReservation;
import com.github.nbena.librarymanager.core.User;
import com.github.nbena.librarymanager.man.LibraryManager;

public class UserModel extends AbstractModel {
	
	private User user;

	public UserModel(LibraryManager manager) {
		super(manager);
	}
	
	public void setUser(User u){
		this.user = u;
	}
	
	
	public boolean authenticate(User user){
		User u = super.manager.authenticateUser(user);
		boolean result = false;
		if (u!=null){
			this.user = user;
			result = true;
		}
		return result;
	}
	
	
	public SeatReservation reserveSeat(LocalDate date) throws LibraryManagerException, SQLException{
		return super.manager.tryReserveSeat((InternalUser) user, date);
	}
	
//	public ConsultationReservation reserveConsultation(Book book, LocalDate date) throws ReservationException, SQLException{
//		return super.manager.tryReserveConsultation((InternalUser) super.user, book, date);
//	}
//	
	public ConsultationReservation reserveConsultation(CopyForConsultation copy,
			LocalDate date) throws LibraryManagerException, SQLException{
		return super.manager.tryReserveConsultation((InternalUser) user, copy, date);
	}
	
	public void cancelReservation(AbstractReservation reservation) throws SQLException{
		super.manager.cancelReservation(reservation);
	}
	
	public LoanReservation reserveLoan(Copy copy) throws SQLException, LibraryManagerException{
		return super.manager.tryReserveLoan((InternalUser) user, copy);
	}
	
	public List<SeatReservation> getSeatsReservations() throws SQLException{
		return super.manager.getSeatReservationsByUser((InternalUser) user);
	}
	
	/**
	 * Get all the ConsultationReservation for this user that are not done yet.
	 * @return
	 * @throws SQLException
	 */
	public List<ConsultationReservation> getConsultationReservations() throws SQLException{
		return super.manager.getConsultationReservationByUser((InternalUser) user, null, true, false);
	}
	
	public List<LoanReservation> getLoanReservations() throws SQLException{
		return super.manager.getLoanReservationsByUser((InternalUser) user, true, false);
	}
	
	public List<Loan> getActiveLoans() throws SQLException{
		return super.manager.getLoansByUser(user, false);
	}
	
	public List<Copy> search(String title, String [] authors, int year, String mainTopic,
			String phouse) throws SQLException{
		return super.manager.search(title, authors, year, mainTopic, phouse);
	}
	
	public void deregister() throws SQLException{
		super.manager.deregisterUser(this.user);
	}
	
	public Loan renewLoan(Loan loan) throws SQLException, LibraryManagerException{
		boolean res = super.manager.tryRenewLoan(loan);
		if (!res){
			throw new LibraryManagerException("Impossibile rinnovare");
		}
		return loan;
	}
	
	public List<Loan> getLoansInLate() throws SQLException{
		return super.manager.getLoansInLate(this.user);
	}

	public User getUser(){
		return this.user;
	}

}
