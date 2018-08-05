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

//TODO add a trigger that avoids users to reserve themselves seats AND
// consultation the same day.

package com.github.nbena.librarymanager.man;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.github.nbena.librarymanager.core.AbstractReservation;
import com.github.nbena.librarymanager.core.Book;
import com.github.nbena.librarymanager.core.BookCopiesNumber;
import com.github.nbena.librarymanager.core.Consultation;
import com.github.nbena.librarymanager.core.ConsultationReservation;
import com.github.nbena.librarymanager.core.Copy;
import com.github.nbena.librarymanager.core.CopyForConsultation;
import com.github.nbena.librarymanager.core.CopyStatus;
import com.github.nbena.librarymanager.core.InternalUser;
import com.github.nbena.librarymanager.core.Librarian;
import com.github.nbena.librarymanager.core.Loan;
import com.github.nbena.librarymanager.core.LoanReservation;
import com.github.nbena.librarymanager.core.Loginable;
import com.github.nbena.librarymanager.core.ReservationException;
import com.github.nbena.librarymanager.core.Seat;
import com.github.nbena.librarymanager.core.SeatReservation;
import com.github.nbena.librarymanager.core.User;

public class LibraryManager {

	public static final String NO_COPIES = "Non ci sono copie che matchano questi parametri "+
				"oppure potrebbero essere già occupdate";
	public static final String NO_SEATS = "Non ci sono posti disponibili per la "+
				"data specificata";
	public static final String NO_RESERVATION = "Non ci sono prenotazioni che matchano "+
				"questi parametri";
	public static final String NO_LOAN = "Non ci sono prestiti che matchano questi parametri";

	private /*@ spec_public @*/DbManager dbManager;

	public LibraryManager(String uri, String username, String password) throws ClassNotFoundException, SQLException{
		this.dbManager = new DbManager(uri, username, password);
	}

	/*@ 
	 @ requires user != null;
	 @
	 @ ensures authenticateUserWithError(user) != null;
	 @ 
	 @*/
	public void saveUser(User user) throws SQLException{
		this.dbManager.addUser(user);
	}


	public void printBadge(User user){

	}

	public User authenticateUser(Loginable user){

		User returned = null;
		try {
			returned = this.dbManager.authenticateUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returned;
	}
	
	public /*@ pure @*/ User authenticateUserWithError(Loginable user) throws SQLException{
		User returned = null;
		returned = this.dbManager.authenticateUser(user);
		return returned;
	}

	public void deregisterUser(User user) throws SQLException{
		this.dbManager.deleteItem(user);
	}

	public Librarian authenticateLibrarian(Loginable librarian){
		Librarian returned = null;
		try{
			returned = this.dbManager.authenticateLibrarian(librarian);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returned;
	}

	/**
	 * Trying to reserve a new seat for this user.
	 * @param user
	 * @param date
	 * @return
	 * @throws ReservationException
	 * @throws SQLException
	 */
	/*@
	 @ ensures date.isAfter(LocalDate.now());
	 @ ensures user != null;
	 @
	 @*/
	public SeatReservation tryReserveSeat(InternalUser user, LocalDate date) throws ReservationException, SQLException{

			List<Seat> seats = this.dbManager.getAvailableSeats(date);
			if (seats.size() <= 0){
				throw new ReservationException(NO_SEATS);
			}

			SeatReservation reservation = new SeatReservation(user, date, seats.get(0));
			this.dbManager.addSeatReservation(reservation);
			return reservation;
	}

	/*@
	 @ ensures \result.isFree() == false; 
	 @*/
	private Seat getAndSetSeatOccupied(LocalDate date) throws SQLException, ReservationException{
		List<Seat> seats = this.dbManager.getAvailableSeats(date);
		if (seats.size() <= 0){
			throw new ReservationException(NO_SEATS);
		}

		Seat seat = seats.get(0);
		seat.setFree(false);
		this.dbManager.setSeatOccupied(seat, true);
		return seat;
	}

	/*@
	 @ requires user != null && copy != null && date !=null; 
	 @ requires date.isAfter(LocalDate.now());
	 @ 
	 @ ensures \result.getCopy().getID() == copy.getID();
	 @ ensures \result.getReservationDate().equals(date);
	 @
	 @*/
	public ConsultationReservation tryReserveConsultation(InternalUser user, CopyForConsultation copy,
			LocalDate date) throws ReservationException, SQLException{

		// CopyForConsultation copy = this.dbManager.getOneAvailableCopyForConsultation(book, date);
		CopyForConsultation returned = this.dbManager.getIfAvailableForConsultation(copy, date);
		if (returned == null){
			throw new ReservationException(NO_COPIES);
		}
		List<Seat> seats = this.dbManager.getAvailableSeats(date);
		if (seats.size() <= 0){
			throw new ReservationException(NO_SEATS);
		}

		ConsultationReservation reservation = new ConsultationReservation(
						user, date, copy, seats.get(0));
		this.dbManager.addConsultationReservation(reservation);
		return reservation;
	}

	/*@ 
	 @ ensures copy.getStatus() != CopyStatus.RESERVED;
	 @ 
	 @*/
	public LoanReservation tryReserveLoan(InternalUser user, Copy copy) throws SQLException, ReservationException{

		if(copy.getStatus() == CopyStatus.RESERVED){
			throw new ReservationException("Questa copia è già riservata");
		}

		Loan loan = this.dbManager.getActiveLoanByCopy(copy);
		if(loan != null){
			if (!loan.isRenewAvailable()){
				loan.setRenewAvailable(false);
				this.dbManager.updateLoan(loan);
			}
		}

		copy.setStatus(CopyStatus.RESERVED);
		// no need to update in the db because we have a trigger that does it
		// for us when a new loan is inserted.

		LoanReservation reservation = new LoanReservation(user, copy);
		this.dbManager.addLoanReservation(reservation);

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

	/*@
	 @ ensures loan.getEnd().equals(LocalDate.now());
	 @ ensures loan.getRestitutionDate().equals(LocalDate.now());
	 @*/
	public void deliveryLoan(Loan loan) throws SQLException{
		loan.setEnd(LocalDate.now());
		loan.setRestitutionDate(LocalDate.now());
		this.dbManager.updateLoan(loan);
	}


	/*@
	 @ ensures \result.getEnd().equals(LocalDate.now().plusMonths(Loan.MAX_MONTHS_SINGLE_LOAN_DURATION)) &&
	 @ \result.isRenewAvailable() == true;
	 @ 
	 @*/
	public Loan loanNotReserved(User user, String title, String [] authors,
			int year, String mainTopic, String phouse) throws SQLException, ReservationException{

		Copy copy = this.dbManager.getOneAvailableCopyForLoan(title,
				authors, year, mainTopic, phouse);
		if (copy == null){
			throw new ReservationException(NO_COPIES);
		}

		Loan loan = new Loan(user, copy);
		this.dbManager.addLoan(loan);
		return loan;
	}

	public Loan loanReserved(LoanReservation reservation) throws /*ReservationException, */
		SQLException{

		Loan loan = reservation.createLoan();
		this.dbManager.addLoan(loan);
		return loan;
	}


	/**
	 * tryRenewLoan finds if it is possible to renew the given loan, and if yes,
	 * it'll renew it.
	 * It does the following:
	 * <pre>
	 * loan.setEnd(LocalDate.now().plusMonths(Loan.MAX_MONTHS_SINGLE_LOAN_DURATION));
	 * loan.setRenewAvailable(false);
	 * </pre>
	 * @param loan
	 * @return
	 * @throws SQLException
	 */
	/*@ 
	 @ ensures \result ==> (loan.isRenewAvailable() == false && 
	 @	loan.getEnd().equals(LocalDate.now().plusMonths(Loan.MAX_MONTHS_SINGLE_LOAN_DURATION))); 
	 @
	 @*/
	public boolean tryRenewLoan(Loan loan) throws SQLException{
		boolean possible = true;
		if(loan.isRenewAvailable()){
			loan.setRenewAvailable(false);
			loan.setEnd(LocalDate.now().plusMonths(Loan.MAX_MONTHS_SINGLE_LOAN_DURATION));
			this.dbManager.updateLoan(loan);
		}else{
			possible = false;
		}
		return possible;
	}

	// NOT REALLY IMPLEMENTED
	/*@
	 @ ensures this.dbManager.getAvailableSeats(LocalDate.now()).size() > 0 ==> \result != null;
	 @ ensures \result.isFree() == false;
	 @*/
	public Seat startNotReservedConsultation(User user, String title, String [] authors,
			int year, String mainTopic, String phouse) throws SQLException, ReservationException{
		// CopyForConsultation copy = this.dbManager.getOneAvailableCopyForConsultation(book, LocalDate.now());
		CopyForConsultation copy = this.dbManager.getOneAvailableCopyForConsultation(
				LocalDate.now(), title, authors, year, mainTopic, phouse);
		if (copy == null){
			throw new ReservationException(NO_COPIES);
		}
		List<Seat> seats = this.dbManager.getAvailableSeats(LocalDate.now());
		if (seats.size() <= 0){
			throw new ReservationException(NO_SEATS);
		}
		Seat seat = seats.get(0);
		seat.setFree(false);

		Consultation consultation = copy.startConsultation(user);

		this.dbManager.startConsultation(consultation);
		this.dbManager.setSeatOccupied(seat, true);

		return seat;
	}

	/*@
	 @ requires reservation != null;
	 @ ensures \result.isFree() != false;
	 @*/
	public Seat startReservedConsultation(ConsultationReservation reservation) throws SQLException, ReservationException{
		// ConsultationReservation reservation = this.dbManager.getConsultationReservation(user, book, LocalDate.now());

//		if (reservation == null){
//			throw new ReservationException(NO_RESERVATION);
//		}

		this.dbManager.autoSave(false);

		Consultation consultation = reservation.getCopy()
				.startConsultation(reservation.getUser());
		
		// consequently, we set the ConsultationReservation to be done.
		this.dbManager.setConsultationReservationDone(reservation);

		Seat seat = reservation.getSeat();
		seat.setFree(false);

		this.dbManager.startConsultation(consultation);
		this.dbManager.setSeatOccupied(seat, false);
		
		this.dbManager.commit(true);
		

		return seat;

	}

	public Seat getOrAssignSeat(User user) throws SQLException, ReservationException{
		Seat seat = null;
		if (user instanceof InternalUser){
			seat = this.dbManager.getReservedSeatOrNothing((InternalUser) user, LocalDate.now());
			if(seat == null){
				seat = this.getAndSetSeatOccupied(LocalDate.now());
			}
		}else{
			seat = this.getAndSetSeatOccupied(LocalDate.now());
		}
		return seat;
	}

	/*@ 
	 @ ensures consultation.getCopy().isInConsultation() == false;
	 @
	 @*/
	public void deliveryConsultation(Consultation consultation) throws SQLException{
		consultation.getCopy().setInConsultation(false);
		this.dbManager.endConsultation(consultation);
	}

	public List<SeatReservation> getSeatReservationsByUser(InternalUser user) throws SQLException{
		return this.dbManager.getSeatsReservationByUser(user);
	}

	public List<ConsultationReservation> getConsultationReservationByUser(InternalUser user, LocalDate date,
			boolean useDoneParam, boolean doneParam) throws SQLException{
		return this.dbManager.getConsultationReservationByUser(user, date, useDoneParam, doneParam);
	}

	public List<LoanReservation> getLoanReservationsByUser(InternalUser user,
			boolean useDoneParam, boolean doneParam) throws SQLException{
		return this.dbManager.getLoanReservationsByUser(user, useDoneParam, doneParam);
	}

	public List<Loan> getLoansByUser(User user, boolean delivered) throws SQLException{
		return this.dbManager.getLoansByUser(user, delivered, true);
	}

//	public List<Loan> getAllLoansByUser(User user) throws SQLException{
//		return this.dbManager.getLoansByUser(user, false, false);
//	}

	public List<Copy> search(String title, String [] authors, int year, String mainTopic,
			String phouse) throws SQLException{
		return this.dbManager.search(title, authors, year, mainTopic, phouse);
	}

	public User getUserFromMail(String email) throws SQLException{
		return this.dbManager.getUser(email);
	}

	public List<User> users() throws SQLException{
		return this.dbManager.users();
	}


//	public LoanReservation getLoanReservationByUserCopy(InternalUser user,
//			String title, String [] authors,
//			int year, String mainTopic, String phouse) throws SQLException{
//
//		return this.dbManager.getLoanReservationByUserCopy(user,
//				title, authors, year, mainTopic, phouse);
//	}

//	public Loan getLoanByUserCopy(User user, String title, String [] authors,
//			int year, String mainTopic, String phouse) throws SQLException{
//
//		return this.dbManager.getLoanByUserCopy(user,
//				title, authors, year, mainTopic, phouse);
//	}
//
//	public ConsultationReservation getConsultationReservationByUserCopy(InternalUser user,
//			LocalDate date,
//			String title, String [] authors,
//			int year, String mainTopic,
//			String phouse
//			) throws SQLException{
//
//		return this.dbManager.getConsultationReservationByUserCopy(user, date,
//				title, authors, year, mainTopic, phouse);
//	}

	public void close(){
		try {
			this.dbManager.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*@
	 @ ensures (user != null) ==> (\forall int i; i>=0 && i<\result.size();
	 @	\result.get(i).getUser().getID() == user.getID()); 
	 @ 
	 @ ensures \result.size() >= 0;
	 @
	 @*/
	public List<Consultation> getConsultationsInProgressByUser(User user) throws SQLException{
		List<Consultation> result = null;
		if (user!=null){
			result = this.dbManager.consultationsInProgressByUser(user);
		}else{
			result = this.dbManager.consultationsInProgress();
		}
		return result;
	}
	
//	public User searchUser(String email) throws SQLException{
//		return this.dbManager.fillUser(email);
//	}
	
	public List<Loan> getLoansInLate() throws SQLException{
		return this.dbManager.getLoansInLate();
	}
	
	public List<? extends Book> getDeletableBooks(boolean withCopiesNumber) throws SQLException{
		return this.dbManager.getDeletableBooks(withCopiesNumber);
	}
	
	public List<BookCopiesNumber> books() throws SQLException{
		return this.dbManager.bookCopiesNumber();
	}
	
	
	public int deleteCopies(Book book, int number) throws SQLException{
		return this.dbManager.deleteSomeCopies(book, number);
	}
	
	public void addCopies(Book book, int number, boolean forConsultation) throws SQLException{
		this.dbManager.addSomeCopies(book, number, forConsultation);
	}



}
