package com.github.nbena.librarymanager.man;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.nbena.librarymanager.core.Book;
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
	private boolean copiesCreated = false;
	private boolean copiesForConsultationCreated = false;
	private boolean seatReservationsCreated = false;
	
	private boolean deleteBooks = false;
	private boolean deleteCopies = false;
	private boolean deleteUsers = false;
	private boolean deleteSeatReservations = false;

	private User [] users;
	private Book [] books;
	private Copy [] copies;
	private CopyForConsultation [] copiesForConsultation;
	private Loan [] loans;
	private LoanReservation [] loanReservations;
	private List<Seat> seats;
	private SeatReservation [] seatReservations;

	@Before
	public void setUp() throws Exception {
		db = new DbManager("localhost:5434/docker", "docker", "docker");
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
						)
		};
		seats = db.getSeats();
		seatReservations = new SeatReservation[]{
				new SeatReservation(LocalDate.now(), (InternalUser)users[1], seats.get(0))
		};
	}

	@After
	public void tearDown() throws Exception {
		if (booksCreated && deleteBooks){
			for (Book b: books){
				db.deleteItem(b);
			}
			booksCreated = false;
		}
		if (usersCreated && deleteUsers){
			for (User u: users){
				db.deleteItem(u);
			}
			usersCreated = false;
		}
		// no need to delete copies
		if (seatReservationsCreated && deleteSeatReservations){
			for (SeatReservation sr: seatReservations){
				db.deleteItem(sr);
			}
		}
		db.close();
	}
	
	private PreparedStatement prepareMulti(String inital, IDble []items) throws SQLException{
		String query = db.queryOnMultipleId(inital, items.length, false);
		Connection c= db.connection;
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
			db.addBook(b);
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
		  db.addUser(u);
	  }
	  usersCreated = true;
	  int count = getCountOf("select count (*) from lm_user where ", users);
	  assertTrue(count == users.length);
  }
  

  public void addCopies() throws SQLException{
	  addBooks();
	  Copy [] allCopies = (Copy[])ArrayUtils.addAll(copies, copiesForConsultation);
	  for (Copy c: allCopies){
		  db.addCopy(c, books[0]);
	  }
	  int count = getCountOf("select count (*) from lm_copy where ", allCopies);
	  assertTrue(count == copies.length + copiesForConsultation.length);
	  copiesCreated = true;
  }
  
  public void addLoan() throws SQLException{
	  addCopies();
	  for (Loan l: loans){
		  db.addLoan(l);
	  }
	  int count = getCountOf("select count (*) from loan where ", loans);
	  assertTrue(count == loans.length);
	  // loansCreated = true;
  }
  
  public void updateLoan() throws SQLException{
	  addLoan();
	  Loan l = loans[0];
	  l.setRenewAvailable(false);
  }
  
  public void seatReservationOps() throws SQLException{
	  for (SeatReservation sr: seatReservations){
		  db.addSeatReservation(sr);
	  }
	  
	  seatReservationsCreated = true;
	  
	  int count = getCountOf("select count (*) from seat_reservation where ", seatReservations);
	  assertTrue(count == seatReservations.length);
	  
	  int availableSeats = db.getAvailableSeats(LocalDate.now()).size();
	  
	  assertTrue(availableSeats < seats.size());
	  
	  SeatReservation expected = seatReservations[0];
	  SeatReservation got = db.getSeatReservationOrNothing(expected.getUser(), expected.getReservationDate());
	  
	  assertTrue(expected.getID() == got.getID());
	  
	  db.cancelSeatReservation(got);
	  count = getCountOf("select count (*) from seat_reservation where ", new SeatReservation[]{got});
	  assertTrue(count == 0);
	  
	  int newAvailableSeats = db.getAvailableSeats(LocalDate.now()).size();
	  assertTrue(availableSeats + 1 == newAvailableSeats);
	  
	  }
  
  
  
  @Test
  public void testAddLoans() throws SQLException{
	  addUser();
	  // addCopies();
	  // addLoan();
	  updateLoan();
	  seatReservationOps();
	  deleteCopies = true;
	  deleteBooks = true;
	  deleteUsers = true;
	  deleteSeatReservations = true;
  }
  
//  @Test
//  public void testAddUsers() throws SQLException{
//	  addUser();
//	  deleteUsers = true;
//  }
  
 
	
	

}
