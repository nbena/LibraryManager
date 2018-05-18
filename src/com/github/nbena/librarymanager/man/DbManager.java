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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;

import com.github.nbena.librarymanager.core.Book;
import com.github.nbena.librarymanager.core.Consultation;
import com.github.nbena.librarymanager.core.ConsultationReservation;
import com.github.nbena.librarymanager.core.Copy;
import com.github.nbena.librarymanager.core.CopyForConsultation;
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
	
	public void close() throws SQLException{
		connection.close();
	}
	
	public User saveUser(User user) throws SQLException{
		
		String query = "insert into user (name, surname, email, password, internal) values (?,?,?,?) returning id";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setString(0, user.getName());
		pstmt.setString(1, user.getSurname());
		pstmt.setString(2, user.getEmail());
		pstmt.setString(3, user.getHashedPassword());
		pstmt.setBoolean(4, (user instanceof InternalUser) ? true : false);
		
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()){
			int id = rs.getInt(0);
			user.setID(id);
		}else{
			throw new SQLException("User: cannot return id");
		}

		return user;
	}
	
	public Book addBook(Book book) throws SQLException{
		
		String query = "insert into book (title, authors, year, topic, phouse) values (?,?,?,?,?) returning id";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setString(0, book.getTitle());
		pstmt.setArray(1, connection.createArrayOf("String", book.getAuthors()));
		pstmt.setInt(2, book.getYearOfPublishing());
		pstmt.setString(3, book.getMainTopic());
		pstmt.setString(4, book.getPublishingHouse());
		
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()){
			int id = rs.getInt(0);
			book.setID(id);
		}else{
			throw new SQLException("Book: cannot return id");
		}
		return book;
	}
	
	public Loan saveLoan(Loan loan) throws SQLException{
		
		String query = "insert into loan (userid, copyid )  values (?,?) returning id";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setInt(0, loan.getUser().getID());
		pstmt.setInt(1, loan.getCopy().getID());
		
		
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()){
			int id = rs.getInt(0);
			loan.setID(id);
		}else{
			throw new SQLException("Loan: cannot return id");
		}
		return loan;
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
		pstmt.setObject(3, reservation.getReservationDate());
		
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			int id = rs.getInt(0);
			reservation.setID(id);
		}else{
			throw new SQLException("SeatReservation: cannot get id");
		}
		
		return reservation;
		
	}
	
	public LoanReservation saveLoanReservation(LoanReservation reservation) throws SQLException{
		
		String query = "insert into loan_reservation (userid, copyid) values (?,?)";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setInt(0, reservation.getUser().getID());
		pstmt.setInt(1, reservation.getCopy().getID());
		
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			int id = rs.getInt(0);
			reservation.setID(id);
		}else{
			throw new SQLException("LoanReservation: cannot get id");
		}
		
		return reservation;
		
	}
	
	public ConsultationReservation saveConsultationReservation(ConsultationReservation reservation) throws SQLException{
				
		String query = "insert into consultation_reservation (userid, copyid, date, seat_reservation) values (?,?,?,?)";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setInt(0, reservation.getUser().getID());
		pstmt.setInt(1, reservation.getCopy().getID());
		pstmt.setObject(2, reservation.getReservationDate());
		pstmt.setInt(3, 0);
		
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			int id = rs.getInt(0);
			reservation.setID(id);
		}else{
			throw new SQLException("ConsultationReservation: cannot get id");
		}
		
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
	
	private Copy getCopyFrom(ResultSet rs, int startingIndex) throws SQLException{
		
		int copyid = rs.getInt(startingIndex);
		String title = rs.getString(startingIndex + 1);
		String [] authors = (String[]) rs.getArray(startingIndex + 2).getArray();
		int year = rs.getInt(startingIndex + 3);
		String topic = rs.getString(startingIndex + 4);
		String phouse = rs.getString(startingIndex + 5);
		
		Copy copy = new Copy(title, authors, year, topic, phouse);
		copy.setID(copyid);
		
		return copy;
		
	}
	
	private Seat getSeatFrom(ResultSet rs, int startingIndex) throws SQLException{
		
		int seatNumber = rs.getInt(startingIndex );
		int tableNumber = rs.getInt(startingIndex + 1);
		boolean free = rs.getBoolean(startingIndex + 2);
		
		Seat seat = new Seat(seatNumber, tableNumber, free);
		return seat;
	}
	
	public List<LoanReservation> getLoanReservationsByUser(InternalUser user) throws SQLException{
		
		String query = "select id, copyid, title, authors, year, topic, phouse, timestamp "+
		"from copy as c join loan_reservation as l on c.id = l.copyid where userid=? order by timestamp desc";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		ResultSet rs = pstmt.executeQuery();
		
		List<LoanReservation> reservations = new LinkedList<LoanReservation>();
		
		
		while (rs.next()){
			
			int id = rs.getInt(0);
//			int copyid = rs.getInt(1);
//			String title = rs.getString(1);
//			int year = rs.getInt(3);
//			String topic = rs.getString(4);
//			String phouse = rs.getString(5);
			
			OffsetDateTime timestamp = (OffsetDateTime) rs.getObject(6);
			
//			String[] authors = (String[]) rs.getArray(2).getArray();
//			
//			Copy copy = new Copy(title, authors, year, topic, phouse);
//			copy.setID(copyid);
			
			Copy copy = getCopyFrom(rs, 1);
			
			LoanReservation reservation = new LoanReservation(id, timestamp, user, copy);
			reservation.setTimestamp(timestamp);
			
			reservations.add(reservation);
			
		}
		
		return reservations;
	}
	
	public LoanReservation getLoanReservationByUserCopy(InternalUser user, Copy copy) throws SQLException{
		return null;
	}
	
	public void setSeatOccupied(Seat seat, boolean occupied) throws SQLException{
		String query = "update seat set free=? where table_number=? and seat_number=?";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setBoolean(0, !occupied);
		pstmt.setInt(1, seat.getTableNumber());
		pstmt.setInt(2, seat.getNumber());
		
		pstmt.execute();
	}
	
	public SeatReservation getSeatReservationOrNothing(InternalUser user, OffsetDateTime date)throws SQLException{
		String query = "select id, seat_number, table_number, timestamp, reservation_date from seat_reservation where userid=? and date=?";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setInt(0, user.getID());
		pstmt.setString(1, date.toString());
		
		ResultSet rs = pstmt.executeQuery();
		SeatReservation reservation = null;
		if (rs.next()){
			int id = rs.getInt(0);
			OffsetDateTime timestamp = (OffsetDateTime) rs.getObject(3);
			LocalDate reservationDate = (LocalDate) rs.getObject(4);
			Seat seat = new Seat(rs.getInt(1), rs.getInt(2), false);
			reservation = new SeatReservation(id, timestamp, reservationDate, user, seat);
		}
		return reservation;
	}
	
	public ConsultationReservation getConsultationReservation(InternalUser user, Book book, OffsetDateTime date)throws SQLException{
		String query = "select cr.id, copyid, title, authors, year, topic, phouse, seat_number, table_number, free, timestamp, reservation_date "+
						"from book join copy on book.id = copy.id "+
						"join consultation_reservation as cr on copy.id = consultation.copyid "+
						"join user on cr.userid = user.id "+
						"user.id=? and book.id=? and reservation_date = ?;";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setInt(0, user.getID());
		pstmt.setInt(1, book.getID());
		pstmt.setObject(2, date);
		
		ResultSet rs = pstmt.executeQuery();
		ConsultationReservation reservation = null;
		if (rs.next()){
			int id = rs.getInt(0);
			
			Copy copy = getCopyFrom(rs, 1);
			CopyForConsultation copyForConsultation = CopyForConsultation.create(copy);
			Seat seat = getSeatFrom(rs, 6);
			
			OffsetDateTime timestamp = (OffsetDateTime) rs.getObject(9);
			LocalDate reservationDate = (LocalDate) rs.getObject(10);
			
			reservation = new ConsultationReservation(id, user, copyForConsultation, seat, reservationDate, timestamp);
		}
		return reservation;
		
	}
	
	public Consultation startConsultation(Consultation consultation)throws SQLException{
		String query = "insert into consultation (userid, copyid) values (?,?) returning id";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setInt(0, consultation.getUser().getID());
		pstmt.setInt(1, consultation.getCopy().getID());
		
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()){
			int id = rs.getInt(0);
			consultation.setID(id);
		}else{
			throw new SQLException("Cannot return id");
		}
		return consultation;
	}
	
	public void endConsultation(Consultation consultation)throws SQLException{
		String query = "update consultation set end=current_time where id=?";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setInt(0, consultation.getID());
		
		pstmt.execute();
	}
	
	public boolean authenticateUser(User user)throws SQLException{
		return false;
	}
	

}
