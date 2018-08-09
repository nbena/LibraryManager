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


package com.github.nbena.librarymanager.man;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.nbena.librarymanager.core.Book;
import com.github.nbena.librarymanager.core.BookCopiesNumber;
import com.github.nbena.librarymanager.core.Consultation;
import com.github.nbena.librarymanager.core.ConsultationReservation;
import com.github.nbena.librarymanager.core.Copy;
import com.github.nbena.librarymanager.core.CopyForConsultation;
import com.github.nbena.librarymanager.core.IDble;
import com.github.nbena.librarymanager.core.InternalUser;
import com.github.nbena.librarymanager.core.Librarian;
import com.github.nbena.librarymanager.core.Loan;
import com.github.nbena.librarymanager.core.LoanReservation;
import com.github.nbena.librarymanager.core.Seat;
import com.github.nbena.librarymanager.core.SeatReservation;
import com.github.nbena.librarymanager.core.Study;
import com.github.nbena.librarymanager.core.User;

public class DbManagerTest {
	
	private static final String LIBRARIAN_MAIL = "librarian@library.com";
	private static final String LIBRARIAN_PASSWORD = "e9a75486736a550af4fea861e2378305c4a555a05094dee1dca2f68afea49cc3a50e8de6ea131ea521311f4d6fb054a146e8282f8e35ff2e6368c1a62e909716";
	
	private DbManager db;
	
	private boolean booksCreated = false;
	private boolean usersCreated = false;
	private boolean seatReservationsCreated = false;
	private boolean loanReservationsCreated = false;
	private boolean consultationReservationsCreated = false;
	private boolean consultationsCreated = false;
	
	private boolean deleteBooks = false;
	private boolean deleteUsers = false;
	private boolean deleteSeatReservations = false;
	private boolean deleteLoanReservations = false;
	private boolean deleteConsultationReservations = false;
	private boolean deleteConsultations = false;
	
	
	// private Librarian librarian;
	private User [] users;
	private Book [] books;
	private Copy [] copies;
	private CopyForConsultation [] copiesForConsultation;
	private Loan [] loans;
	private LoanReservation [] loanReservations;
	private List<Seat> seats;
	private SeatReservation [] seatReservations;
	private ConsultationReservation [] consultationReservations;
	private Consultation [] consultations;
	
	@Before
	public void setUp() throws Exception {
		this.db = new DbManager("localhost:5435/docker", "docker", "docker");
		books = new Book[]{
				new Book("Title1", new String[]{
						"Me",
						"You",
					},
						2018,
						"Info",
						"phouse"
						),
				new Book("DeletableTitle",
						new String[]{
								"Me"
						},
						2018,
						"NULL",
						"phouse")
		};
		users = new User[]{
				new User(
						"User1",
						"User1",
						"user1@example.org",
						"password1"		
						),
				new InternalUser(
						"User2",
						"User2",
						"user2@example.org",
						"password2"						
						)
		};
		copies = new Copy[]{
				new Copy("Title1", new String[]{
						"Me",
						"You",
					},
						2018,
						"Info",
						"phouse"		
						),
				new Copy("Title1", new String[]{
						"Me",
						"You",
					},
						2018,
						"Info",
						"phouse"),
				new Copy("Title1", new String[]{
						"Me",
						"You",
					},
						2018,
						"Info",
						"phouse"),
				
		};
		copiesForConsultation = new CopyForConsultation[]{
				new CopyForConsultation("Title1", new String[]{
						"Me",
						"You",
					},
						2018,
						"Info",
						"phouse"		
						),
				new CopyForConsultation("Title1", new String[]{
						"Me",
						"You",
					},
						2018,
						"Info",
						"phouse")
				
		};
		loans = new Loan[]{
				new Loan(
						users[0],
						copies[0]
						),
				new Loan(
						users[0],
						copies[1]
						)
		};
		seats = this.db.getSeats();
		assertTrue(seats.size() > 0);
		seatReservations = new SeatReservation[]{
				new SeatReservation((InternalUser)users[1], LocalDate.now(), this.seats.get(0))
		};
		loanReservations = new LoanReservation[]{
				new LoanReservation(
						(InternalUser)users[1], copies[1])
		};
		this.consultationReservations = new ConsultationReservation[]{
				new ConsultationReservation(
						(InternalUser)this.users[1], LocalDate.now(),
						this.copiesForConsultation[0], this.seats.get(0)
						),
				new ConsultationReservation(
						(InternalUser)this.users[1],LocalDate.now(), 
						this.copiesForConsultation[1], this.seats.get(1)
						),			
		};
		this.consultations = new Consultation[]{
				new Consultation(
						  this.consultationReservations[1].getUser(),
						  this.consultationReservations[1].getCopy(),
						  new Seat(5, 5, false)
						  )			
		};
		
		Librarian toAuth = new Librarian();
		toAuth.setEmail(LIBRARIAN_MAIL);
		toAuth.setHashedPassword(LIBRARIAN_PASSWORD);
		/*this.librarian = */this.db.authenticateLibrarian(toAuth);
	}

	@After
	public void tearDown() throws Exception {
		if (this.booksCreated && this.deleteBooks){
			for (Book b: books){
				this.db.deleteItem(b);
			}
			this.booksCreated = false;
		}
		if (this.usersCreated && this.deleteUsers){
			for (User u: users){
				this.db.deleteItem(u);
			}
			this.usersCreated = false;
		}
		// no need to delete copies
		if (this.seatReservationsCreated && this.deleteSeatReservations){
			for (SeatReservation sr: this.seatReservations){
				this.db.deleteItem(sr);
			}
			this.seatReservationsCreated = false;
		}
		if (this.loanReservationsCreated && this.deleteLoanReservations){
			for(LoanReservation lr: this.loanReservations){
				this.db.deleteItem(lr);
			}
			this.loanReservationsCreated = false;
		}
		if (this.consultationReservationsCreated && this.deleteConsultationReservations){
			for(ConsultationReservation cr: this.consultationReservations){
				this.db.deleteItem(cr);
			}
			this.consultationReservationsCreated = false;
		}
		if (this.consultationsCreated && this.deleteConsultations){
			for(Consultation c: this.consultations){
				this.db.deleteItem(c);
			}
			this.consultationsCreated = false;
		}		
		this.db.close();
	}
	
	private PreparedStatement prepareMulti(String inital, IDble []items) throws SQLException{
		String query = DbManagerHelper.queryOnMultipleId(inital, items.length, false);
		Connection c= this.db.connection;
		PreparedStatement pstmt = c.prepareStatement(query);
		int i=1;
		for (IDble item: items){
			pstmt.setInt(i, item.getID());
			i++;
		}
		return pstmt;
	}
	
	private int getCountOf(String inital, IDble []items) throws SQLException{

		ResultSet rs = prepareMulti(inital, items).executeQuery();
		rs.next();
		int count = rs.getInt(1);
		return count;
	}
	
//	private void delete(String initial, IDble [] items) throws SQLException{
//		
//		prepareMulti(initial, items).execute();
//	}
	
	
	public void addBooks() throws SQLException{
		for (Book b: books){
			this.db.addBook(b);
			// query += "id=? or";
		}
		booksCreated = true;
		// query = query.substring(0, query.lastIndexOf("or"));
//		String query = db.queryOnMultipleId(inital, books.length, false); 
//		Connection c = db.connection;
//		PreparedStatement pstmt = c.prepareStatement(query);
//		
//		for (i=0;i<books.length;i++){
//			pstmt.setInt(i+1, books[i].getID());
//		}
//		
//		ResultSet rs = pstmt.executeQuery();
//		rs.next();
//		int count = rs.getInt(1);
		int count = getCountOf("select count(*) from book where ", books);
		assertTrue(count >= books.length);
	}
	
	
  public void addUser() throws SQLException{
	  
	  this.db.autoSave(false);
	  
	  for (User u: this.users){
		  this.db.addUser(u);
	  }
	  
	  this.db.commit(false);
	  
	  usersCreated = true;
	  int count = getCountOf("select count (*) from lm_user where ", users);
	  assertTrue(count >= users.length);
	  
	  // now call getUser
	  User got = this.db.getUser(this.users[0].getEmail());
	  assertTrue(got.getID() == this.users[0].getID());
	  
	  int usersCount = this.db.users().size();
	  assertTrue(usersCount >= users.length);
	  
	  this.db.commit(true);
	  this.db.autoSave(true);
	  
	  assertTrue(this.db.authenticateUser(this.users[0]) != null);
	  
	  // we modify a user
	  User mod = this.users[users.length - 1];
	  String oldPass = mod.getHashedPassword();
	  mod.setHashedPassword("will fail");
	  assertTrue(this.db.authenticateUser(mod) == null);
	  mod.setHashedPassword(oldPass);
	  
	  
  }
  

  public void addCopies() throws SQLException{
	  addBooks();
	  Copy [] allCopies = (Copy[])ArrayUtils.addAll(copies, copiesForConsultation);
	  for (Copy c: allCopies){
		  this.db.addCopy(c, books[0]);
	  }
	  int count = getCountOf("select count (*) from lm_copy where ", allCopies);
	  assertTrue(count >= copies.length + copiesForConsultation.length);
	  
	  @SuppressWarnings("unchecked")
	  List<Book> deletable = (List<Book>) this.db.getDeletableBooks(false);
	  boolean found = false;
	  for(int i=0;i<deletable.size();i++){
		  Book b = deletable.get(i);
		  if(b.getTitle().equals(this.books[1].getTitle())){
			  found = true;
			  i = deletable.size(); // break
		  }
	  }
	  assertTrue(found);
	  
	  // now we add one copy to that book.
	  this.db.addSomeCopies(deletable.get(0), 1, false);
	  List<BookCopiesNumber> allBooks = this.db.bookCopiesNumber();
	  for (int i = 0;i<allBooks.size();i++){
		  BookCopiesNumber b = allBooks.get(i);
		  if(b.getID() == deletable.get(0).getID()){
			  assertTrue(b.getCopiesNumber() == 1);
			  i = allBooks.size(); // break
		  }
	  }
	  
	  // now we delete them
	  this.db.deleteSomeCopies(deletable.get(0), 1);
	  allBooks = this.db.bookCopiesNumber();
	  for (int i = 0;i<allBooks.size();i++){
		  BookCopiesNumber b = allBooks.get(i);
		  if(b.getID() == deletable.get(0).getID()){
			  assertTrue(b.getCopiesNumber() == 0);
			  i = allBooks.size(); // break
		  }
	  }
  }
  
  public void addLoan() throws SQLException{
	  // addCopies();
	  for (Loan l: loans){
		  this.db.addLoan(l);
	  }
	  int count = getCountOf("select count (*) from loan where ", loans);
	  assertTrue(count == loans.length);
	  // loansCreated = true;
  }
  
//  public void updateLoan() throws SQLException{
//	  addLoan();
//	  Loan l = loans[0];
//	  l.setRenewAvailable(false);
//  }
  private void addLoanInLate() throws SQLException{
	  
	  // finding out the loanin late added in the Dockerfile.
	  boolean found = false;
	  
	  List<Loan> loansInLate = this.db.getLoansInLate();
	 
	  
	  for(int i=0;i<loansInLate.size();i++){
		  Loan loan = loansInLate.get(i);
		  if(loan.getCopy().getTitle().equals("Title3")){
			  found = true;
			  i = loansInLate.size();
		  }
	  }
	  
	  
	  assertTrue(found);
  }
  
  
  public void loanOps() throws SQLException{
	  this.addCopies();
	  
	  // we add a loan that we known it'll be in late
	  this.addLoanInLate();
	  
	  this.addLoan();
	  Loan l = this.loans[0];
	  l.setRenewAvailable(false);
	  
	  // Loan gotLoan = this.db.getLoanByUserCopy(l.getUser(), l.getCopy(), false);
//	  Loan gotLoan = this.db.getLoanByUserCopy(l.getUser(),
//			  l.getCopy().getTitle(),
//			  l.getCopy().getAuthors(),
//			  l.getCopy().getYearOfPublishing(),
//			  l.getCopy().getMainTopic(),
//			  l.getCopy().getPublishingHouse()
//			 );
//	  assertTrue(gotLoan.getID() == l.getID());
	  Loan gotLoan = this.loans[0];
	  
	  
	  // test the renew change
	  gotLoan.setRenewAvailable(false);
	  this.db.updateLoan(gotLoan);
	  
	  List<Loan> gotLoans = this.db.getLoansByUser(gotLoan.getUser(), false, false);
	  for(Loan loan: gotLoans){
		  if (loan.getID() == gotLoan.getID()){
			  assertTrue(loan.isRenewAvailable() == false);
			  break;
		  }
	  }
	  
	  // test the end
	  gotLoan.setEnd(LocalDate.now());
	  this.db.updateLoan(gotLoan);
	  gotLoans = this.db.getLoansByUser(gotLoan.getUser(), false, false);
	  for(Loan loan: gotLoans){
		  if (loan.getID() == gotLoan.getID()){
			  assertTrue(loan.getEnd().equals(LocalDate.now()));
			  break;
		  }
	  }
	  
	  // test the restitution
	  gotLoan.setRestitutionDate(LocalDate.now());
	  this.db.updateLoan(gotLoan);
	  gotLoans = this.db.getLoansByUser(gotLoan.getUser(), false, false);
	  for(Loan loan: gotLoans){
		  if (loan.getID() == gotLoan.getID()){
			  assertTrue(loan.getRestitutionDate().equals(LocalDate.now()));
			  break;
		  }
	  }	  
	  
	  
	  boolean found = false;
	  List<Loan> loansByUser = this.db.getLoansByUser(l.getUser(), true, true);
	  for(Loan loan: loansByUser){
		  if (loan.getID() == gotLoan.getID()){
			  found = true;
			  break;
		  }
	  }
	  assertTrue(found);
	  assertTrue(loansByUser.size() == 1);
	  
//	  gotLoan = this.db.getActiveLoanByCopy(this.loans[1].getCopy());
//	  assertTrue(gotLoan.getID() == this.loans[1].getID());
	  
	 //  List<Loan> allLoans = this.db.getLoans(l.getUser(), true, true);
	  // assertTrue(allLoans.size() == 1);
	  
	  List<Loan> allLoans = this.db.getLoansByUser(l.getUser(), false, true);
	  assertTrue(allLoans.size() > 0);
	  
	  allLoans = this.db.getLoansByUser(l.getUser(),false, false);
	  assertTrue(allLoans.size() == this.loans.length);
	  
	  
	  for (LoanReservation lr: this.loanReservations){
		  this.db.addLoanReservation(lr);
	  }
	  
	  int count = getCountOf("select count (*) from loan_reservation where ", loanReservations);
	  assertTrue(count == this.loanReservations.length);
	  
	  count = this.db.getLoanReservationsByUser(this.loanReservations[0].getUser(),
			  false, false).size();
	  assertTrue(count == this.loanReservations.length);
	  
	  LoanReservation expected = this.loanReservations[0];
	  LoanReservation got = this.db.getLoanReservationsByUser(expected.getUser(),
			  true, false).get(0);
	  
	  assertTrue(expected.getID() == got.getID());
	  
	  // got = this.db.getLoanReservationByUserCopy(expected.getUser(), expected.getCopy());
	  // assertTrue(got.getID() ==  expected.getID());
	  
	  
	  this.db.deleteItem(got);
	  
	  count = getCountOf("select count (*) from loan_reservation where ", new LoanReservation[]{got});
	  assertTrue(count == 0);
	  
	  // Now we resave it and try to create another and
	  // trigger will be raised
	  this.db.addLoanReservation(expected);
	  
	  this.db.setLoanReservationDone(expected);
	  
	  List<LoanReservation> reservations = this.db.getLoanReservationsByUser(
			  expected.getUser(), true, true);
	  
	  found = true;
	  for(int i =0;i<reservations.size();i++){
		  if(reservations.get(i).getID() == expected.getID()){
			  found = true;
			  i = reservations.size();
		  }
	  }
	  
	  assertTrue(found);
	  
	  boolean thrown = false;
	 
	  try{
		  this.db.addLoanReservation(expected);
	  }catch(SQLException e){
		  thrown = true;
		  assertTrue(e.getMessage().contains("this copy is already reserved"));
	  }
	  // db.addLoanReservation(expected);
	  assertTrue(thrown);
	  
	  
	  // Copy available = this.db.getOneAvailableCopyForLoan("Title1", null, 0, null, null);
	  List<Copy> forLoan = this.db.getAvailableCopiesForLoan("Title1", null, 0, null, null);
	  found = false;
	  for (int i=0;i<this.copies.length;i++){
		  for(int j=0;j<forLoan.size();i++){
			  if (this.copies[i].getID() == forLoan.get(j).getID()){
				  found = true;
				  i = this.copies.length;
				  j = forLoan.size();
		  	}
		  }
	  }
	  assertTrue(found);
	  
	  // expected = this.db.getLoanReservationByUserCopy(got.getUser(), got.getCopy());
//	  got = this.db.getLoanReservationByUserCopy(got.getUser(), got.getCopy().getTitle(),
//			  got.getCopy().getAuthors(), got.getCopy().getYearOfPublishing(),
//			  got.getCopy().getMainTopic(), got.getCopy().getPublishingHouse());
	  
	  // assertTrue(expected.getID() == expected.getID());
	  
	  List<BookCopiesNumber> booksCopiesNumber = this.db.bookCopiesNumber();
	  assertTrue(booksCopiesNumber.size() >= this.books.length);
  }
  
  
  public void consultationOps() throws SQLException{
	  this.addCopies();
	  
	  for (ConsultationReservation cr: this.consultationReservations){
		  db.addConsultationReservation(cr);
	  }
	  
	  this.consultationReservationsCreated = true;
	  
	  int count = getCountOf("select count (*) from consultation_reservation where ", this.consultationReservations);
	  assertTrue(count == this.consultationReservations.length);
	  
	  count = this.db.getConsultationReservationByUser(this.consultationReservations[0].getUser(), null, false, false).size();
	  assertTrue(count == this.consultationReservations.length);
	  
      ConsultationReservation expected = this.consultationReservations[0];
//	  ConsultationReservation got = this.db.getConsultationReservationByUserCopy(
//			  this.consultationReservations[0].getUser(),
//			  this.consultationReservations[0].getReservationDate(),
//			  this.consultationReservations[0].getCopy().getTitle(),
//			  this.consultationReservations[0].getCopy().getAuthors(),
//			  this.consultationReservations[0].getCopy().getYearOfPublishing(),
//			  this.consultationReservations[0].getCopy().getMainTopic(),
//			  this.consultationReservations[0].getCopy().getPublishingHouse()
//			  );
//	  
//	  assertTrue(expected.getID() == got.getID());
	  
	  // this.db.cancelConsultationReservation(got);
	  this.db.setConsultationReservationDone(expected);
	  List<ConsultationReservation> reservations = this.db.getConsultationReservationByUser(expected.getUser(),
			  	expected.getReservationDate(), false, false);
	  for(int i=0;i<reservations.size();i++){
		  ConsultationReservation cr = reservations.get(i);
		  if(cr.getID() == expected.getID()){
			  assertTrue(cr.isDone() == true);
			  i = reservations.size(); //break
		  }
	  }
	  // testing the is done has no sense because the attributes are separated (db and code):
	  // code: if reservationDate > today then true
	  // db	as a pure attribute.
	  // this separation is necessary.
	  
	  this.db.deleteItem(expected);

	  count = getCountOf("select count (*) from consultation_reservation where ",
			  new ConsultationReservation[]{expected});
	  assertTrue(count == 0);
	  
	  // already used seat
	  expected.setSeat(this.consultationReservations[1].getSeat());
	  boolean thrown = false;
	  try{
		  this.db.addConsultationReservation(expected);
	  }catch(SQLException e){
		  thrown = true;
		  assertTrue(e.getMessage().contains("violates unique constraint"));
	  }
	  assertTrue(thrown);
	  
	  expected.setCopy(this.consultationReservations[1].getCopy());
	  thrown = false;
	  try{
		  this.db.addConsultationReservation(expected);
	  }catch(SQLException e){
		  thrown = true;
		  assertTrue(e.getMessage().contains("violates unique constraint"));
	  }
	  assertTrue(thrown);

	  
	  for(Consultation c: this.consultations){
		  db.startConsultation(c);
	  }
	  
	  count = this.getCountOf("select count (*) from consultation where ",
			 this.consultations);
	  
	  assertTrue(count == this.consultations.length);
	  
	  // now we get the number of consultation in porgress
	  int progress = this.db.consultationsInProgress().size();
	  assertTrue(count == progress);
	  
	  // now same but for a specific user
	  int progressByUser = this.db.consultationsInProgressByUser(this.consultations[0].getUser()).size();
	  assertTrue(count >= progressByUser);
	  
	  for(Consultation c: this.consultations){
		  db.endConsultation(c);
	  }
	  
	  count = this.getCountOf("select count (*) from consultation where end_date is not null and ", this.consultations);
	  
	  assertTrue(count == this.consultations.length);
	  
	  // CopyForConsultation c = this.db.getOneAvailableCopyForConsultation(this.copiesForConsultation[0], LocalDate.now().plusDays(4));
	  
	  CopyForConsultation c = this.db.getIfAvailableForConsultation(this.copiesForConsultation[0], LocalDate.now().plusDays(4));
	  assertTrue(this.copiesForConsultation[0].getID() == c.getID());
	  
//	  c = this.db.getOneAvailableCopyForConsultation(LocalDate.now().plusDays(4),
//			  this.copiesForConsultation[0].getTitle(),
//			  this.copiesForConsultation[0].getAuthors(),
//			  this.copiesForConsultation[0].getYearOfPublishing(),
//			  this.copiesForConsultation[0].getMainTopic(),
//			  this.copiesForConsultation[0].getPublishingHouse()
//			  );
	  List<CopyForConsultation> forConsultations = this.db.getAvailableCopiesForConsultation(
			  LocalDate.now().plusDays(4),
			  this.copiesForConsultation[0].getTitle(),
			  this.copiesForConsultation[0].getAuthors(),
			  this.copiesForConsultation[0].getYearOfPublishing(),
			  this.copiesForConsultation[0].getMainTopic(),
			  this.copiesForConsultation[0].getPublishingHouse()
			  );
			  
	  // assertTrue(this.copiesForConsultation[0].getID() == c.getID());
	  boolean found = false;
	  for(int i=0;i<forConsultations.size();i++){
		  if(forConsultations.get(i).getID() == this.copiesForConsultation[0].getID()){
			  found = true;
			  i = forConsultations.size();
		  }
	  }
	  
	  assertTrue(found);
	  
  }
  
  public void seatReservationOps() throws SQLException{
	  for (SeatReservation sr: seatReservations){
		  this.db.addSeatReservation(sr);
	  }
	  
	  this.seatReservationsCreated = true;
	  
	  int count = getCountOf("select count (*) from seat_reservation where ", this.seatReservations);
	  assertTrue(count == this.seatReservations.length);
	  
	  count = this.db.getSeatsReservationByUser(this.seatReservations[0].getUser()).size();
	  assertTrue(count == this.seatReservations.length);
	  
	  int availableSeats = this.db.getAvailableSeats(LocalDate.now()).size();
	  
	  assertTrue(availableSeats < this.seats.size());
	  
	  SeatReservation expected = seatReservations[0];
	 //  SeatReservation got = this.db.getSeatReservationOrNothing(expected.getUser(), expected.getReservationDate());
	  Seat got = this.db.getReservedSeatOrNothing(expected.getUser(), expected.getReservationDate());
	  
	  assertTrue(expected.getSeat().equals(got));
	  
	  // this.db.cancelSeatReservation(got);
	  
	  Study study = this.seatReservations[0].createStudy();
	  /*study = */this.db.tryAddStudy(study);
	  
	  Study gotStudy;
	  boolean found = false;
	  List<Study> studies = this.db.getStudies();
	  for(int i=0;i< studies.size();i++){
		  gotStudy = studies.get(i);
		  if(gotStudy.getSeat().getNumber() == study.getSeat().getNumber() &&
				  gotStudy.getSeat().getTableNumber() == study.getSeat().getTableNumber() &&
				  gotStudy.getUser().getID() == study.getUser().getID()){
			  found = true;
			  i = studies.size();
		  }
	  }
	  assertTrue(found);
	  
	  gotStudy = this.db.getStudyByUser(study.getUser());
	  // assertTrue(gotStudy.getID() == study.getID());
	  assertTrue(gotStudy.getSeat().getNumber() == study.getSeat().getNumber() &&
				  gotStudy.getSeat().getTableNumber() == study.getSeat().getTableNumber() &&
				  gotStudy.getUser().getID() == study.getUser().getID());
	  
	  this.db.deleteStudyByUser(study.getUser());
	  
	  assertTrue(this.db.getStudies().size() == 0);
	  
	  this.db.deleteItem(expected);

	  count = getCountOf("select count (*) from seat_reservation where ", new SeatReservation[]{expected});
	  assertTrue(count == 0);
	  
	  int newAvailableSeats = this.db.getAvailableSeats(LocalDate.now()).size();
	  assertTrue(availableSeats + 1 == newAvailableSeats);
	  
	  // now it's time to start a reservation
	  
	 }
  
  
  private void searchEmptyTitle() throws SQLException{
	  List<Copy> copies = this.db.search("", null, 0, null, null);
	  assertTrue(copies.size() > 0);
  }
  
  private void searchTitleExists() throws SQLException {
	  List<Copy> copies = this.db.search(this.books[0].getTitle(),
			  null, 0, null, null);
	  assertTrue(copies.size() == this.copies.length + this.copiesForConsultation.length);
  }
  
  private void searchTitleNotExists() throws SQLException{
	  List<Copy> copies = this.db.search("junit: the non definitive guide",
			  null, 0, null, null);
	  assertTrue(copies.size() == 0);
  }
  
  private void searchByYearExists() throws SQLException {
	  List<Copy> copies = this.db.search(null, null, 2018, null, null);
	  assertTrue(copies.size() > 0);
  }
  
  private void searchByYearNotExists() throws SQLException{
	  List<Copy> copies = this.db.search(null, null, 1000, null, null);
	  assertTrue(copies.size() == 0);
  }
  
  private void searchByTopicExists() throws SQLException{
	  List<Copy> copies = this.db.search(null, null, 0, "Info", null);
	  assertTrue(copies.size() > 0);
  }
  
  private void searchByTopicNotExists() throws SQLException{
	  List<Copy> copies = this.db.search(null, null, 0, "Inf0", null);
	  assertTrue(copies.size() == 0);
  }
  
  private void searchByAuthorsExists() throws SQLException{
	  List<Copy> copies = this.db.search(null, new String[]{"Me", "You"}, 0, null, null);
	  assertTrue(copies.size() > 0);
  }
  
  private void searchByAuthorsNotExists() throws SQLException{
	  List<Copy> copies = this.db.search(null, new String[]{"Mee", "Youu"}, 0, null, null);
	  assertTrue(copies.size() == 0);
  }
  
  private void searchByPHouseExists() throws SQLException{
	  List<Copy> copies = this.db.search(null, null, 0, null, "oreilly");
	  assertTrue(copies.size() > 0);
  }
  
  private void searchByPHouseNotExists() throws SQLException{
	  List<Copy> copies = this.db.search(null, null, 0, null, "oreillyyyyy");
	  assertTrue(copies.size() == 0);
  }
  
   
  
  @Test
  public void testLoans() throws SQLException{
	  addUser();
	  loanOps();
	  deleteBooks = true;
	  deleteUsers = true;
	  // deleteSeatReservations = true;
  }
  
  @Test
  public void testSeats() throws SQLException{
	  addUser();
	  addCopies();
	  seatReservationOps();
	  this.deleteBooks = true;
	  this.deleteUsers = true;	  
	  this.deleteSeatReservations = true;
  }
  
  @Test
  public void testConsultations() throws SQLException {
	  this.addUser();
	  this.consultationOps();
	  this.deleteBooks = true;
	  this.deleteUsers = true;
	  this.deleteConsultationReservations = true;
	  this.deleteConsultations = true;
  }
  
  @Test
  public void testSearches() throws SQLException{
	  this.addCopies();
	  this.searchEmptyTitle();
	  this.searchTitleExists();
	  this.searchTitleNotExists();
	  this.searchByAuthorsExists();
	  this.searchByAuthorsNotExists();
	  this.searchByYearExists();
	  this.searchByYearNotExists();
	  this.searchByTopicExists();
	  this.searchByTopicNotExists();
	  this.searchByPHouseExists();
	  this.searchByPHouseNotExists();
	  this.deleteBooks = true;
	  this.deleteUsers = true;
	  this.deleteConsultationReservations = true;
	  this.deleteConsultations = true;
  }
  
//  @Test
//  public void testAddUsers() throws SQLException{
//	  addUser();
//	  deleteUsers = true;
//  }
  
 
	
	

}
