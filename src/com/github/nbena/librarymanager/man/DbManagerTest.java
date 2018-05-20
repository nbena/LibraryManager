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
import com.github.nbena.librarymanager.core.Consultation;
import com.github.nbena.librarymanager.core.ConsultationReservation;
import com.github.nbena.librarymanager.core.Copy;
import com.github.nbena.librarymanager.core.CopyForConsultation;
import com.github.nbena.librarymanager.core.IDble;
import com.github.nbena.librarymanager.core.InternalUser;
import com.github.nbena.librarymanager.core.Loan;
import com.github.nbena.librarymanager.core.LoanReservation;
import com.github.nbena.librarymanager.core.Seat;
import com.github.nbena.librarymanager.core.SeatReservation;
import com.github.nbena.librarymanager.core.User;

public class DbManagerTest {
	
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
		this.db = new DbManager("localhost:5434/docker", "docker", "docker");
		books = new Book[]{
				new Book("Title1", new String[]{
						"Me",
						"You",
					},
						2018,
						"Info",
						"phouse"
						)
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
						"phouse")
				
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
						  this.consultationReservations[1].getCopy()
						  )			
		};
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
		String query = this.db.queryOnMultipleId(inital, items.length, false);
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
		assertTrue(count == books.length);
	}
	
	
  public void addUser() throws SQLException{
	  for (User u: users){
		  this.db.addUser(u);
	  }
	  usersCreated = true;
	  int count = getCountOf("select count (*) from lm_user where ", users);
	  assertTrue(count == users.length);
  }
  

  public void addCopies() throws SQLException{
	  addBooks();
	  Copy [] allCopies = (Copy[])ArrayUtils.addAll(copies, copiesForConsultation);
	  for (Copy c: allCopies){
		  this.db.addCopy(c, books[0]);
	  }
	  int count = getCountOf("select count (*) from lm_copy where ", allCopies);
	  assertTrue(count == copies.length + copiesForConsultation.length);
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
  
  public void loanOps() throws SQLException{
	  this.addCopies();
	  
	  this.addLoan();
	  Loan l = this.loans[0];
	  l.setRenewAvailable(false);
	  
	  Loan gotLoan = this.db.getLoanByUserCopy(l.getUser(), l.getCopy(), false);
	  assertTrue(gotLoan.getID() == l.getID());
	  
	  this.db.registerLoanDelivered(gotLoan);
	  gotLoan = this.db.getLoanByUserCopy(l.getUser(), l.getCopy(), true);
	  assertTrue(gotLoan != null);
	  assertTrue(gotLoan.getRestitutionDate().equals(LocalDate.now()));
	  
	  gotLoan = this.db.getActiveLoanByCopy(this.loans[1].getCopy());
	  assertTrue(gotLoan.getID() == this.loans[1].getID());
	  
	  
	  
	  for (LoanReservation lr: this.loanReservations){
		  this.db.addLoanReservation(lr);
	  }
	  
	  int count = getCountOf("select count (*) from loan_reservation where ", loanReservations);
	  assertTrue(count == this.loanReservations.length);
	  
	  LoanReservation expected = this.loanReservations[0];
	  LoanReservation got = this.db.getLoanReservationsByUser(expected.getUser()).get(0);
	  
	  assertTrue(expected.getID() == got.getID());
	  
	  got = this.db.getLoanReservationByUserCopy(expected.getUser(), expected.getCopy());
	  assertTrue(got.getID() ==  expected.getID());
	  
	  // this.db.cancelLoanReservation(got);
	  this.db.deleteItem(got);
	  
	  count = getCountOf("select count (*) from loan_reservation where ", new LoanReservation[]{got});
	  assertTrue(count == 0);
	  
	  // Now we resave it and try to create another and
	  // trigger will be raised
	  this.db.addLoanReservation(expected);
	  
	  boolean thrown = false;
	 
	  try{
		  this.db.addLoanReservation(expected);
	  }catch(SQLException e){
		  thrown = true;
		  assertTrue(e.getMessage().contains("this copy is already reserved"));
	  }
	  // db.addLoanReservation(expected);
	  assertTrue(thrown);
  }
  
  
  public void consultationOps() throws SQLException{
	  this.addCopies();
	  
	  for (ConsultationReservation cr: this.consultationReservations){
		  db.addConsultationReservation(cr);
	  }
	  
	  this.consultationReservationsCreated = true;
	  
	  int count = getCountOf("select count (*) from consultation_reservation where ", this.consultationReservations);
	  assertTrue(count == this.consultationReservations.length);
	  
	  ConsultationReservation expected = this.consultationReservations[0];
	  ConsultationReservation got = this.db.getConsultationReservation(
			  expected.getUser(),
			  expected.getCopy(),
			  expected.getReservationDate()
			  );
	  
	  assertTrue(expected.getID() == got.getID());
	  
	  // this.db.cancelConsultationReservation(got);
	  this.db.deleteItem(got);

	  count = getCountOf("select count (*) from consultation_reservation where ", new ConsultationReservation[]{got});
	  assertTrue(count == 0);
	  
	  // already used seat
	  got.setSeat(this.consultationReservations[1].getSeat());
	  boolean thrown = false;
	  try{
		  this.db.addConsultationReservation(got);
	  }catch(SQLException e){
		  thrown = true;
		  assertTrue(e.getMessage().contains("violates unique constraint"));
	  }
	  assertTrue(thrown);
	  
	  got.setCopy(this.consultationReservations[1].getCopy());
	  thrown = false;
	  try{
		  this.db.addConsultationReservation(got);
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
	  
	  for(Consultation c: this.consultations){
		  db.endConsultation(c);
	  }
	  
	  count = this.getCountOf("select count (*) from consultation where end_date is not null and ", this.consultations);
	  
	  assertTrue(count == this.consultations.length);
	  
	  CopyForConsultation c = this.db.getOneAvailableCopyForConsultation(this.copiesForConsultation[0], LocalDate.now().plusDays(4));
	  
	  assertTrue(this.copiesForConsultation[0].getID() == c.getID());
	  
  }
  
  public void seatReservationOps() throws SQLException{
	  for (SeatReservation sr: seatReservations){
		  this.db.addSeatReservation(sr);
	  }
	  
	  this.seatReservationsCreated = true;
	  
	  int count = getCountOf("select count (*) from seat_reservation where ", this.seatReservations);
	  assertTrue(count == this.seatReservations.length);
	  
	  int availableSeats = this.db.getAvailableSeats(LocalDate.now()).size();
	  
	  assertTrue(availableSeats < this.seats.size());
	  
	  SeatReservation expected = seatReservations[0];
	 //  SeatReservation got = this.db.getSeatReservationOrNothing(expected.getUser(), expected.getReservationDate());
	  Seat got = this.db.getReservedSeatOrNothing(expected.getUser(), expected.getReservationDate());
	  
	  assertTrue(expected.getSeat().equals(got));
	  
	  // this.db.cancelSeatReservation(got);
	  this.db.deleteItem(expected);

	  count = getCountOf("select count (*) from seat_reservation where ", new SeatReservation[]{expected});
	  assertTrue(count == 0);
	  
	  int newAvailableSeats = this.db.getAvailableSeats(LocalDate.now()).size();
	  assertTrue(availableSeats + 1 == newAvailableSeats);
	  
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
  
//  @Test
//  public void testAddUsers() throws SQLException{
//	  addUser();
//	  deleteUsers = true;
//  }
  
 
	
	

}
