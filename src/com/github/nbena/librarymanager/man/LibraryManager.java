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

package com.github.nbena.librarymanager.man;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.github.nbena.librarymanager.core.AbstractReservation;
import com.github.nbena.librarymanager.core.Book;
import com.github.nbena.librarymanager.core.ConsultationReservation;
import com.github.nbena.librarymanager.core.Copy;
import com.github.nbena.librarymanager.core.CopyForConsultation;
import com.github.nbena.librarymanager.core.InternalUser;
import com.github.nbena.librarymanager.core.Loan;
import com.github.nbena.librarymanager.core.ReservationException;
import com.github.nbena.librarymanager.core.Seat;
import com.github.nbena.librarymanager.core.SeatReservation;
import com.github.nbena.librarymanager.core.User;

public class LibraryManager {
	
	private DbManager dbManager;
	
	public LibraryManager(String uri, String username, String password) throws ClassNotFoundException, SQLException{
		this.dbManager = new DbManager(uri, username, password);
	}
	
	public void saveUser(User user) throws SQLException{
		this.dbManager.addUser(user);
	}
	
	
	public void printBadge(User user){
		
	}
	
	public boolean authenticateUser(String hashedPassword){
		
		boolean authenticated = true;
		try {
			authenticated = this.dbManager.authenticateUser(hashedPassword);
		} catch (SQLException e) {
			authenticated = false;
		}
		
		return authenticated;
	}
	
	
	public SeatReservation tryReserveSeat(InternalUser user, LocalDate date) throws ReservationException, SQLException{
			List<Seat> seats = this.dbManager.getAvailableSeats(date);
			if (seats.size() > 0){
				SeatReservation reservation = new SeatReservation(user, date, seats.get(0));
				this.dbManager.addSeatReservation(reservation);
				return reservation;
			}else{
				throw new ReservationException("No seats available");
			}			
	}
	
	public ConsultationReservation tryReserveConsultation(InternalUser user, Book book, LocalDate date) throws ReservationException, SQLException{

		CopyForConsultation copy = this.dbManager.getOneAvailableCopyForConsultation(book, date);
		if (copy != null){
			List<Seat> seats = this.dbManager.getAvailableSeats(date);
			if (seats.size() > 0){
				ConsultationReservation reservation = new ConsultationReservation(
						user, date, copy, seats.get(0));
				this.dbManager.addConsultationReservation(reservation);
				return reservation;
			}else{
				throw new ReservationException("No seats available");
			}
		}else{
			throw new ReservationException("No copies available");
		}
	}
	
	public void addBook(Book book) throws SQLException{
		this.dbManager.addBook(book);
	}
	
	public void deleteBook(Book book) throws SQLException{
		this.dbManager.deleteItem(book);
	}
	
	public void cancelReservation(AbstractReservation reservation) throws SQLException{
		this.dbManager.deleteItem(reservation);
	}
	
	public void deliveryBook(User user, Copy copy) throws SQLException{
		Loan loan = this.dbManager.getLoanByUserCopy(user, copy, false);
		if (loan != null){
			loan.setEnd(LocalDate.now());
			this.dbManager.registerLoanDelivered(loan);
		}else{
			throw new SQLException("Loan not found");
		}
	}
	
	public Loan loanNotReserved(User user, Copy copy) throws SQLException{
		Loan loan = new Loan(user, copy);
		this.dbManager.addLoan(loan);
		return loan;
	}
	
	public Loan loanReserved(InternalUser user, Copy copy){
		return null;
	}
	
	
	

}
