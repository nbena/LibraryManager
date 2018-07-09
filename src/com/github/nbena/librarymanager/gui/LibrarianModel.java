/*  LibraryManager a toy library manager
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

import com.github.nbena.librarymanager.core.Book;
import com.github.nbena.librarymanager.core.BookCopiesNumber;
import com.github.nbena.librarymanager.core.Consultation;
import com.github.nbena.librarymanager.core.ConsultationReservation;
import com.github.nbena.librarymanager.core.InternalUser;
import com.github.nbena.librarymanager.core.Loan;
import com.github.nbena.librarymanager.core.LoanReservation;
import com.github.nbena.librarymanager.core.ReservationException;
import com.github.nbena.librarymanager.core.Seat;
import com.github.nbena.librarymanager.core.User;
import com.github.nbena.librarymanager.man.LibraryManager;

public class LibrarianModel extends AbstractModel {
	
	
	public LibrarianModel(LibraryManager manager) {
		super(manager);
	}
	
	/**
	 * Get the full user object from the given mail.
	 * @param email the search param
	 * @return the user with this email
	 * @throws SQLException
	 * @throws ReservationException if the user is not found
	 */
	public User getUser(String email) throws SQLException, ReservationException{
		User user = super.manager.getUserFromMail(email);
		if(user!=null){
			throw new ReservationException("Utente non trovato");
		}
		return user;
	}
	
	public User getUser(User u) throws SQLException, ReservationException{
		return this.getUser(u.getEmail());
	}

//	public void createUser(String name, String surname, String email, String password, boolean internal) throws SQLException{
//		User user;
//		if (internal){
//			user = new InternalUser(name, surname, password, email);
//		}else{
//			user = new User(name, surname, password, email);
//		}
//		user.hashPassword();
//		
//		super.manager.saveUser(user);
//	}
	public void addUser(User u) throws SQLException{
		super.manager.saveUser(u);
	}
	
	public void addBook(Book book) throws SQLException{
		super.manager.addBook(book);
	}
	
	public void deleteBook(Book book) throws SQLException{
		super.manager.deleteBook(book);
	}
	
	public void sendMailFor(Loan loan){
		
	}
	
//	public void deliveryBook(User user, Copy copy) throws SQLException, ReservationException{
//		super.manager.deliveryBook(user, copy);
//	}
	
	public void deliveryLoan(User user, String title, String [] authors, int year,
			String mainTopic, String phouse) throws SQLException, ReservationException{
		
		Loan loan = super.manager.getLoanByUserCopy(user, title, authors, year,
				mainTopic, phouse);
		if(loan == null){
			throw new ReservationException(LibraryManager.NO_LOAN);
		}
		
		super.manager.deliveryLoan(loan);
	}
	
	public Loan loanNotReserved(User user, String title, String [] authors, int year,
			String mainTopic, String phouse) throws ReservationException, SQLException{
//		Copy copy = super.manager.getOneAvailableCopyForLoan(title, authors,
//				year, mainTopic);
//		Loan loan = null;
//		if (copy!=null){
//			loan = super.manager.loanNotReserved(user, copy);
//		}else{
//			throw new ReservationException("Non sono state trovate copie con questi " +
//					"con questi parametri");
//		}
//		return loan;
		return super.manager.loanNotReserved(user, title, authors, year, mainTopic, phouse);
	}
	
	
//	public Loan loanNotReserved(User user, Copy copy) throws SQLException{
//		return super.manager.loanNotReserved(user, copy);
//	}
	
	public Loan loanReserved(InternalUser user, String title, String [] authors, int year,
			String mainTopic, String phouse) throws SQLException, ReservationException{
		
		LoanReservation reservation = super.manager.getLoanReservationByUserCopy(
				user, title, authors, year, mainTopic, phouse);

		if (reservation == null){
			throw new ReservationException(LibraryManager.NO_RESERVATION);
		}

		return super.manager.loanReserved(reservation);
	}
	
//	public Loan loanReserved(InternalUser user, Copy copy) throws ReservationException, SQLException{
//		return super.manager.loanReserved(user, copy);
//	}
	
	public boolean renewLoan(Loan loan) throws SQLException{
		return super.manager.tryRenewLoan(loan);
	}
	
	public Seat startNotReservedConsultation(User user, String title,
			String [] authors, int year, String mainTopic, String phouse) throws SQLException, ReservationException{
		return super.manager.startNotReservedConsultation(user, title,
				authors, year, mainTopic, phouse);
	}

	
//	public Seat startReservedConsultation(InternalUser user, String title, String [] authors,
//			int year, String mainTopic, String phouse) throws SQLException, ReservationException{
//
//		ConsultationReservation reservation = super.manager.getConsultationReservationByUserCopy(
//				user, LocalDate.now(), title, authors, year, mainTopic, phouse);
//		
//		if(reservation == null){
//			throw new ReservationException(LibraryManager.NO_RESERVATION);
//		}
//		
//		return super.manager.startReservedConsultation(reservation);
//	}
	
	public Seat startReservedConsultation(ConsultationReservation reservation) throws SQLException, ReservationException{
		if (reservation == null){
			throw new ReservationException(LibraryManager.NO_RESERVATION);
		}
		return super.manager.startReservedConsultation(reservation);
	}
	
	public List<ConsultationReservation> getConsultationReservationsByUserToday(InternalUser user) throws SQLException{
		
		return super.manager.getConsultationReservationByUser(user, LocalDate.now());
	}
	
	// for this one we can have a list of the consultations in progress
	// JUST THE CONSULTATIONS IN PROGRESS.
	// the same is not allowed for loans 'privacy' reason.
	// it can change
	public void deliveryConsultation(Consultation consultation) throws SQLException{
		super.manager.deliveryConsultation(consultation);
	}
	
	public List<User> users() throws SQLException{
		return super.manager.users();
	}
	
	public List<Consultation> consultations(User user) throws SQLException{
		return super.manager.getConsultationInProgressByUser(user);
	}
	
//	public User fillUser(String email) throws SQLException, ReservationException{
//		User user = super.manager.searchUser(email);
//		if (user==null){
//			throw new ReservationException("User not found");
//		}
//		return user;
//	}
	
	public List<Loan> getLoansInLate() throws SQLException{
		return super.manager.getLoansInLate();
	}
	
	// here we cast from parent to child which is not good
	// but here I know it's safe
	@SuppressWarnings("unchecked")
	public List<BookCopiesNumber> getDeletableBooks() throws SQLException{
		return (List<BookCopiesNumber>) super.manager.getDeletableBooks(true);
	}
	
	public List<BookCopiesNumber> books() throws SQLException{
		return super.manager.books();
	}
	
	
	public int deleteCopies(Book book, int number) throws SQLException{
		return super.manager.deleteCopies(book, number);
	}
	
	public void addCopies(Book book, int number, boolean forConsultation) throws SQLException{
		super.manager.addCopies(book, number, forConsultation);
	}


}
