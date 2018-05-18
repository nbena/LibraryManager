package com.github.nbena.librarymanager.man;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import com.github.nbena.librarymanager.core.Book;
import com.github.nbena.librarymanager.core.Consultation;
import com.github.nbena.librarymanager.core.ConsultationReservation;
import com.github.nbena.librarymanager.core.Copy;
import com.github.nbena.librarymanager.core.InternalUser;
import com.github.nbena.librarymanager.core.Loan;
import com.github.nbena.librarymanager.core.LoanReservation;
import com.github.nbena.librarymanager.core.Seat;
import com.github.nbena.librarymanager.core.SeatReservation;
import com.github.nbena.librarymanager.core.User;

public class DbManager {
	
	private Connection connection;
	
	public DbManager(String path) throws SQLException{
		
		// Class.forName(arg0)
		connection = DriverManager.getConnection("jdbc:sqlite:"+path);
	}
	
	public void saveUser(User user) throws SQLException{
		
		String query = "insert into user (name, surname, email, password, internal) values (?,?,?,?)";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setString(0, user.getName());
		pstmt.setString(1, user.getSurname());
		pstmt.setString(2, user.getEmail());
		pstmt.setString(3, user.getHashedPassword());
		pstmt.setBoolean(4, (user instanceof InternalUser) ? true : false);
		
		ResultSet rs = pstmt.executeQuery();
		
		rs.next();
		
		// get id
	}
	
	public void addBook(Book book) throws SQLException{
		
		String query = "insert into book (title, authors, year, topic, phouse) values (?,?,?,?,?)";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setString(0, book.getTitle());
		pstmt.setString(1, null);
		pstmt.setInt(2, book.getYearsOfPublishing());
		pstmt.setString(3, book.getMainTopic());
		pstmt.setString(4, book.getPublishingHouse());
		
		pstmt.execute();
		
	}
	
	public void saveLoan(Loan loan)  throws SQLException{
		
		String query = "insert into loan (userid, copyid )  values (?,?)";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setInt(0, loan.getUser().getID());
		pstmt.setInt(1, loan.getCopy().getID());
		
		
		pstmt.execute();
	}
	
	public void updateLoan(Loan loan) throws SQLException{
		
		String query = "update loan set start=?, end=?, renew_available=?, restitution_date=?";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setString(0, loan.getStart().toString());
		pstmt.setString(1, loan.getEnd().toString());
		pstmt.setBoolean(2, loan.isRenewAvailable());
		pstmt.setString(3, loan.getRestitutionDate().toString());
		
		pstmt.execute();
		
	}
	
	public void deleteBook(Book book) throws SQLException{
		
		String query = "delete from book where id=?";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setInt(0, book.getID());
		
		pstmt.execute();
		
	}
	
	public SeatReservation saveSeatReservation(SeatReservation reservation) throws SQLException{
		
		String query = "insert into seat_reservation (userid, seat_number, table_number, date) values (?,?,?,?)";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setInt(0, reservation.getUser().getID());
		pstmt.setInt(1, reservation.getSeat().getNumber());
		pstmt.setInt(2, reservation.getSeat().getTableNumber());
		pstmt.setString(3, reservation.getReservationDate().toString());
		
		pstmt.execute();
		
		return reservation;
		
	}
	
	public LoanReservation saveLoanReservation(LoanReservation reservation) throws SQLException{
		
		String query = "insert into loan_reservation (userid, copyid) values (?,?)";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setInt(0, reservation.getUser().getID());
		pstmt.setInt(1, reservation.getCopy().getID());
		
		pstmt.execute();
		
		return reservation;
		
	}
	
	public ConsultationReservation saveConsultationReservation(ConsultationReservation reservation) throws SQLException{
				
		String query = "insert into consultation_reservation (userid, copyid, date, seat_reservation) values (?,?,?,?)";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setInt(0, reservation.getUser().getID());
		pstmt.setInt(1, reservation.getCopy().getID());
		pstmt.setString(2, reservation.getReservationDate().toString());
		pstmt.setInt(3, 0);
		
		pstmt.execute();
		
		return reservation;
		
	}
	
	public List<Seat> getAvailableSeats(LocalDateTime date) throws SQLException{
		
		String query = "select seat_number,table_number from seats where seats_number, table_number not in "+
				"(select seat_number, table_number from seat_reservation where date = ?)";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		ResultSet rs = pstmt.executeQuery();
		
		List<Seat> seats = new LinkedList<Seat>();
		while (rs.next()){
			
			Seat seat = new Seat(rs.getInt(0), rs.getInt(1), true);
			seats.add(seat);
		}
		
		return seats;
	}
	
	public void cancelSeatReservation(SeatReservation reservation) throws SQLException{
		
		String query = "delete from seat_reservation where id=?";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setInt(0, reservation.getID());
		
		pstmt.execute();
		
	}
	
	public void cancelLoanReservation(LoanReservation reservation) throws SQLException{
		
		String query = "delete from loan_reservation where id=?";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setInt(0, reservation.getID());
		
		pstmt.execute();
		
	}
	
	public void cancelConsultationReservation(ConsultationReservation reservation) throws SQLException{
		
		String query = "delete from consultation_reservation where id=?";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setInt(0, reservation.getID());

		pstmt.execute();
		
	}
	
	public Loan getLoanByUserCopy(User user, Copy copy) throws SQLException{
		return null;
	}
	
	public List<LoanReservation> getLoanReservationsByUser(InternalUser user) throws SQLException{
		
		String query = "select copyid, title, authors, year, topic, phouse, timestamp "+
		"from copy as c join loan_reservation as l on c.id = l.copyid where userid=? order by timestamp desc";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		ResultSet rs = pstmt.executeQuery();
		
		List<LoanReservation> reservations = new LinkedList<LoanReservation>();
		
		
		while (rs.next()){
			
			int id = rs.getInt(0);
			String title = rs.getString(1);
			String notParsedAuthors = rs.getString(2);
			int year = rs.getInt(3);
			String topic = rs.getString(4);
			String phouse = rs.getString(5);
			
			LocalDateTime timestamp = LocalDateTime.parse(rs.getString(6));
			
			String[] authors = notParsedAuthors.split(";");
			
			Copy copy = new Copy(title, authors, year, topic, phouse);
			copy.setID(id);
			
			LoanReservation reservation = new LoanReservation(user, copy, false);
			reservation.setTimestamp(timestamp);
			
			reservations.add(reservation);
			
		}
		
		return reservations;
	}
	
	public LoanReservation getLoanReservationByUserCopy(InternalUser user, Copy copy) throws SQLException{
		return null;
	}
	
	public void setSeatOccupied(Seat seat, boolean occupied) throws SQLException{
		String query = "";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		List<LoanReservation> reservations = new LinkedList<LoanReservation>();
		
		pstmt.setString(0, );
		pstmt.setString(1, );
		pstmt.setString(2, );
		
		pstmt.execute();
	}
	
	public SeatReservation getSeatReservationOrNothing(InternalUser user, LocalDateTime date)throws SQLException{
		return null;
	}
	
	public ConsultationReservation getConsultationReservation(InternalUser user, Book book, LocalDateTime date)throws SQLException{
		return null;
	}
	
	public void startConsultation(Consultation consultation)throws SQLException{
		String query = "";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setString(0, );
		pstmt.setString(1, );
		pstmt.setString(2, );
		
		pstmt.execute();
	}
	
	public void endConsultation(Consultation consultation)throws SQLException{
		String query = "";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setString(0, );
		pstmt.setString(1, );
		pstmt.setString(2, );
		
		pstmt.execute();
	}
	
	public boolean authenticateUser(User user)throws SQLException{
		return false;
	}
	

}
