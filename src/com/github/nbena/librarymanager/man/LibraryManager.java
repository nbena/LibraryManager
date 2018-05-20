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
import com.github.nbena.librarymanager.core.Consultation;
import com.github.nbena.librarymanager.core.ConsultationReservation;
import com.github.nbena.librarymanager.core.Copy;
import com.github.nbena.librarymanager.core.CopyForConsultation;
import com.github.nbena.librarymanager.core.InternalUser;
import com.github.nbena.librarymanager.core.Loan;
import com.github.nbena.librarymanager.core.LoanReservation;
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
			if (seats.size() <= 0){
				throw new ReservationException("No seats available");
			}

			SeatReservation reservation = new SeatReservation(user, date, seats.get(0));
			this.dbManager.addSeatReservation(reservation);
			return reservation;			
	}
	
	public ConsultationReservation tryReserveConsultation(InternalUser user, Book book, LocalDate date) throws ReservationException, SQLException{

		CopyForConsultation copy = this.dbManager.getOneAvailableCopyForConsultation(book, date);
		if (copy == null){
			throw new ReservationException("No copies available");
		}
		List<Seat> seats = this.dbManager.getAvailableSeats(date);
		if (seats.size() <= 0){
			throw new ReservationException("No seats available");
		}

		ConsultationReservation reservation = new ConsultationReservation(
						user, date, copy, seats.get(0));
		this.dbManager.addConsultationReservation(reservation);
		return reservation;
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
	
	public void deliveryBook(User user, Copy copy) throws SQLException, ReservationException{
		Loan loan = this.dbManager.getLoanByUserCopy(user, copy, false);
		if (loan == null){
			throw new ReservationException("Loan not found");
		}

		loan.setEnd(LocalDate.now());
		this.dbManager.registerLoanDelivered(loan);			
	}
	
	public Loan loanNotReserved(User user, Copy copy) throws SQLException{
		Loan loan = new Loan(user, copy);
		this.dbManager.addLoan(loan);
		return loan;
	}
	
	public Loan loanReserved(InternalUser user, Copy copy) throws ReservationException, SQLException{
		LoanReservation reservation = this.dbManager.getLoanReservationByUserCopy(user, copy);
		if (reservation == null){
			throw new ReservationException("Reservation not found");
		}
		Loan loan = reservation.createLoan();
		this.dbManager.addLoan(loan);
		return loan;
	}
	
	
	public boolean tryRenewLoan(Loan loan) throws SQLException{
		boolean possible = true;
		if(loan.isRenewAvailable()){
			loan.setRenewAvailable(false);
			loan.setRestitutionDate(LocalDate.now().plusMonths(Loan.MAX_MONTHS_SINGLE_LOAN_DURATION));
			this.dbManager.updateLoan(loan);
		}else{
			possible = false;
		}
		return possible;
	}
	
	public Seat startNotReservedConsultation(User user, Book book) throws SQLException, ReservationException{
		CopyForConsultation copy = this.dbManager.getOneAvailableCopyForConsultation(book, LocalDate.now());
		if (copy == null){
			throw new ReservationException("No copies available");
		}
		List<Seat> seats = this.dbManager.getAvailableSeats(LocalDate.now());
		if (seats.size() <= 0){
			throw new ReservationException("No seats available");
		}
		Seat seat = seats.get(0);
		seat.setFree(false);
		
		Consultation consultation = copy.startConsultation(user);
		
		this.dbManager.startConsultation(consultation);
		this.dbManager.setSeatOccupied(seat, true);
		
		return seat;
	}
	
	
	

}
