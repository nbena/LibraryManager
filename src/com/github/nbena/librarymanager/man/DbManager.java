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
import java.sql.Statement;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;

import com.github.nbena.librarymanager.core.AbstractReservation;
import com.github.nbena.librarymanager.core.Book;
import com.github.nbena.librarymanager.core.Consultation;
import com.github.nbena.librarymanager.core.ConsultationReservation;
import com.github.nbena.librarymanager.core.Copy;
import com.github.nbena.librarymanager.core.CopyForConsultation;
import com.github.nbena.librarymanager.core.CopyStatus;
import com.github.nbena.librarymanager.core.IDble;
import com.github.nbena.librarymanager.core.InternalUser;
import com.github.nbena.librarymanager.core.Loan;
import com.github.nbena.librarymanager.core.LoanReservation;
import com.github.nbena.librarymanager.core.Seat;
import com.github.nbena.librarymanager.core.SeatReservation;
import com.github.nbena.librarymanager.core.User;

public class DbManager {
	
	Connection connection;
	
	
	public DbManager(String path, String username, String password) throws SQLException, ClassNotFoundException{
		
		Class.forName("org.postgresql.Driver");
		connection = DriverManager.getConnection("jdbc:postgresql://"+path, username, password);
	}
	
	public void close() throws SQLException{
		connection.close();
	}
	
	String queryOnMultipleId(String initial, int length, boolean and){
		String logical = and ? "and" : "or";
		String query = initial;
		for (int i=0;i<length;i++){
			query += " id=? "+logical;
		}
		query = query.substring(0, query.lastIndexOf(logical));
		return query;
	}
	
	public User addUser(User user) throws SQLException{
		
		String query = "insert into lm_user(name, surname, email, password, internal) values (?,?,?,?,?) returning id";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setString(1, user.getName());
		pstmt.setString(2, user.getSurname());
		pstmt.setString(3, user.getEmail());
		pstmt.setString(4, user.getHashedPassword());
		pstmt.setBoolean(5, (user instanceof InternalUser) ? true : false);
		
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()){
			int id = rs.getInt(1);
			user.setID(id);
		}else{
			throw new SQLException("User: cannot return id");
		}

		return user;
	}
	
	public Book addBook(Book book) throws SQLException{
		
		String query = "insert into book (title, authors, year, main_topic, phouse) values (?,?,?,?,?) returning id";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setString(1, book.getTitle());
		pstmt.setArray(2, connection.createArrayOf("text", book.getAuthors()));
		pstmt.setInt(3, book.getYearOfPublishing());
		pstmt.setString(4, book.getMainTopic());
		pstmt.setString(5, book.getPublishingHouse());
		
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()){
			int id = rs.getInt(1);
			book.setID(id);
		}else{
			throw new SQLException("Book: cannot return id");
		}
		return book;
	}
	
	public Copy addCopy(Copy copy, Book book) throws SQLException{
		
		String query = "insert into lm_copy(bookid, for_consultation) values (?,?) returning id";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setInt(1, book.getID());
		pstmt.setBoolean(2, copy instanceof CopyForConsultation);
		
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			int id = rs.getInt(1);
			copy.setID(id);
		}else{
			throw new SQLException("Copy: cannot return id");
		}
		return copy;
	}
	
	
	public Loan addLoan(Loan loan) throws SQLException{
		
		String query = "insert into loan (userid, copyid )  values (?,?) returning id";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setInt(1, loan.getUser().getID());
		pstmt.setInt(2, loan.getCopy().getID());
		
		
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()){
			int id = rs.getInt(1);
			loan.setID(id);
		}else{
			throw new SQLException("Loan: cannot return id");
		}
		return loan;
	}
	
	public void updateLoan(Loan loan) throws SQLException{
		
		String query = "update loan set start_date=?, end_date=?, renew_available=?, restitution_date=?";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setString(1, loan.getStart().toString());
		pstmt.setString(2, loan.getEnd().toString());
		pstmt.setBoolean(3, loan.isRenewAvailable());
		pstmt.setString(4, loan.getRestitutionDate().toString());
		
		pstmt.execute();
		
	}
	
	public List<Seat> getSeats() throws SQLException{
		
		String query = "select seat_number, table_number, free from seat";
		Statement stat = connection.createStatement();
		
		ResultSet rs = stat.executeQuery(query);
		List<Seat> seats = new LinkedList<Seat>();
		while(rs.next()){
			Seat seat = getSeatFrom(rs, 1, true);
			seats.add(seat);
		}
		return seats;
	}
	
//	public void deleteBook(Book book) throws SQLException{
//		
//		String query = "delete from book where id=?";
//		
//		PreparedStatement pstmt = connection.prepareStatement(query);
//		
//		pstmt.setInt(1, book.getID());
//		
//		pstmt.execute();
//		
//	}
//	
//	private void deleteUser(User u){
//		String query = "delete from lm_user where id=?";
//		
//		PreparedStatement pstmt = connection.prepareStatement(query);
//		
//		pstmt.setInt(1, user.getID());
//		
//		pstmt.execute();		
//	}
	
	void deleteItem(IDble item) throws SQLException{
		String table = "";
		if (item instanceof Book){
			table = "book";
		}else if (item instanceof Copy || item instanceof CopyForConsultation){
			table = "lm_copy";
		}else if (item instanceof ConsultationReservation){
			table = "consultation_reservation";
		}else if (item instanceof User || item instanceof InternalUser){
			table = "lm_user";
		}else if (item instanceof Loan){
			table = "loan";
		}else if(item instanceof LoanReservation){
			table = "loan_reservation";
		}else if(item instanceof SeatReservation){
			table = "seat_reservation";
		}
		String query = "delete from "+table+" where id=?";
		PreparedStatement pstmt = connection.prepareStatement(query);
		pstmt.setInt(1, item.getID());
		pstmt.execute();
	}
	
	public SeatReservation addSeatReservation(SeatReservation reservation) throws SQLException{
		
		String query = "insert into seat_reservation (userid, seat_number, table_number, reservation_date) values (?,?,?,?) returning id";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setInt(1, reservation.getUser().getID());
		pstmt.setInt(2, reservation.getSeat().getNumber());
		pstmt.setInt(3, reservation.getSeat().getTableNumber());
		pstmt.setObject(4, reservation.getReservationDate());
		
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			int id = rs.getInt(1);
			reservation.setID(id);
		}else{
			throw new SQLException("SeatReservation: cannot get id");
		}
		
		return reservation;
		
	}
	
	public LoanReservation addLoanReservation(LoanReservation reservation) throws SQLException{
		
		String query = "insert into loan_reservation (userid, copyid) values (?,?) returning id";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setInt(1, reservation.getUser().getID());
		pstmt.setInt(2, reservation.getCopy().getID());
		
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			int id = rs.getInt(1);
			reservation.setID(id);
		}else{
			throw new SQLException("LoanReservation: cannot get id");
		}
		
		return reservation;
		
	}
	
	public ConsultationReservation addConsultationReservation(ConsultationReservation reservation) throws SQLException{
				
		String query = "insert into consultation_reservation (userid, copyid, reservation_date, seat_number, table_number) values (?,?,?,?,?) returning id";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setInt(1, reservation.getUser().getID());
		pstmt.setInt(2, reservation.getCopy().getID());
		pstmt.setObject(3, reservation.getReservationDate());
		pstmt.setInt(4, reservation.getSeat().getNumber());
		pstmt.setInt(5, reservation.getSeat().getTableNumber());
		
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			int id = rs.getInt(1);
			reservation.setID(id);
		}else{
			throw new SQLException("ConsultationReservation: cannot get id");
		}
		
		return reservation;
		
	}
	
	public List<Seat> getAvailableSeats(LocalDate date) throws SQLException{
		
		String query = "select seat_number,table_number, free  from seat as s where not exists "+
				"(select * from seat_reservation as sr where reservation_date = ? and "+
				"s.seat_number = sr.seat_number and s.table_number = sr.table_number)";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		pstmt.setObject(1, date);
		
		ResultSet rs = pstmt.executeQuery();
		
		List<Seat> seats = new LinkedList<Seat>();
		while (rs.next()){
			
			Seat seat = new Seat(rs.getInt(1), rs.getInt(2), true);
			seats.add(seat);
		}
		
		return seats;
	}
	
//	public void cancelSeatReservation(SeatReservation reservation) throws SQLException{
//		
//		String query = "delete from seat_reservation where id=?";
//		
//		PreparedStatement pstmt = connection.prepareStatement(query);
//		
//		pstmt.setInt(1, reservation.getID());
//		
//		pstmt.execute();
//		
//	}
//	
//	public void cancelLoanReservation(LoanReservation reservation) throws SQLException{
//		
//		String query = "delete from loan_reservation where id=?";
//		
//		PreparedStatement pstmt = connection.prepareStatement(query);
//		
//		pstmt.setInt(1, reservation.getID());
//		
//		pstmt.execute();
//		
//	}
//	
//	public void cancelConsultationReservation(ConsultationReservation reservation) throws SQLException{
//		
//		String query = "delete from consultation_reservation where id=?";
//		
//		PreparedStatement pstmt = connection.prepareStatement(query);
//		
//		pstmt.setInt(1, reservation.getID());
//
//		pstmt.execute();
//		
//	}
//	
//	public void cancelReservation(AbstractReservation reservation) throws SQLException{
//		String tableName = "";
//		if (reservation instanceof SeatReservation){
//			tableName = "seat_reservation";
//		}else if (reservation instanceof ConsultationReservation){
//			tableName = "consultation_reservation";
//		}else if (reservation instanceof LoanReservation){
//			tableName = "loan_reservation";
//		}
//		
//		String query = "delete from "+tableName+" where id=?";
//		PreparedStatement pstmt = this.connection.prepareStatement(query);
//		pstmt.setInt(1, reservation.getID());
//		
//		pstmt.execute();
//	}
	
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
		String status = rs.getString(startingIndex + 6);
		
		Copy copy = new Copy(title, authors, year, topic, phouse);
		copy.setStatus(CopyStatus.from(status));
		copy.setID(copyid);
		
		return copy;
		
	}
	
	private Seat getSeatFrom(ResultSet rs, int startingIndex, boolean useFree) throws SQLException{
		
		int seatNumber = rs.getInt(startingIndex );
		int tableNumber = rs.getInt(startingIndex + 1);
		boolean free = false;
		if (useFree){
			free = rs.getBoolean(startingIndex + 2);
		}
		
		
		Seat seat = new Seat(seatNumber, tableNumber, free);
		return seat;
	}
	
	public List<LoanReservation> getLoanReservationsByUser(InternalUser user) throws SQLException{
		
		String query = "select l.id, copyid, title, authors, year, main_topic, phouse, status, time_stamp "+
		"from book join lm_copy as c on book.id = c.bookid join loan_reservation as l on c.id = l.copyid where userid=? order by time_stamp desc";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		pstmt.setInt(1, user.getID());
		
		ResultSet rs = pstmt.executeQuery();
		
		List<LoanReservation> reservations = new LinkedList<LoanReservation>();
		
		
		while (rs.next()){
			
			int id = rs.getInt(1);
//			int copyid = rs.getInt(1);
//			String title = rs.getString(1);
//			int year = rs.getInt(3);
//			String topic = rs.getString(4);
//			String phouse = rs.getString(5);
			
			OffsetDateTime timestamp = (OffsetDateTime) rs.getObject(9, OffsetDateTime.class);
			
//			String[] authors = (String[]) rs.getArray(2).getArray();
//			
//			Copy copy = new Copy(title, authors, year, topic, phouse);
//			copy.setID(copyid);
			
			Copy copy = getCopyFrom(rs, 2);
			
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
		
		pstmt.setBoolean(1, !occupied);
		pstmt.setInt(2, seat.getTableNumber());
		pstmt.setInt(3, seat.getNumber());
		
		pstmt.execute();
	}
	
	public SeatReservation getSeatReservationOrNothing(InternalUser user, LocalDate date)throws SQLException{
		String query = "select id, seat_number, table_number, time_stamp, reservation_date from seat_reservation where userid=? and reservation_date=?";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setInt(1, user.getID());
		pstmt.setObject(2, date);
		
		ResultSet rs = pstmt.executeQuery();
		SeatReservation reservation = null;
		if (rs.next()){
			int id = rs.getInt(1);
			OffsetDateTime timestamp = (OffsetDateTime) rs.getObject(4, OffsetDateTime.class);
			LocalDate reservationDate = (LocalDate) rs.getObject(5, LocalDate.class);
			Seat seat = new Seat(rs.getInt(2), rs.getInt(3), false);
			reservation = new SeatReservation(id, timestamp, reservationDate, user, seat);
		}
		return reservation;
	}
	
	public ConsultationReservation getConsultationReservation(InternalUser user, Book book, LocalDate date)throws SQLException{
		String query = "select cr.id, copyid, title, authors, year, main_topic, phouse, status, seat_number, table_number, time_stamp, reservation_date "+
						"from book join lm_copy on book.id = lm_copy.bookid "+
						"join consultation_reservation as cr on lm_copy.id = cr.copyid "+
						"where cr.userid=? and reservation_date = ? and "+
						"title=? and authors=?";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setInt(1, user.getID());
		// pstmt.setInt(2, book.getID());
		pstmt.setObject(2, date);
		pstmt.setString(3, book.getTitle());
		pstmt.setArray(4, connection.createArrayOf("varchar", book.getAuthors()));
		
		ResultSet rs = pstmt.executeQuery();
		ConsultationReservation reservation = null;
		if (rs.next()){
			int id = rs.getInt(1);
			
			Copy copy = getCopyFrom(rs, 2);
			CopyForConsultation copyForConsultation = CopyForConsultation.create(copy);
			Seat seat = getSeatFrom(rs, 9, false);
			
			OffsetDateTime timestamp = (OffsetDateTime) rs.getObject(11, OffsetDateTime.class);
			LocalDate reservationDate = (LocalDate) rs.getObject(12, LocalDate.class);
			
			reservation = new ConsultationReservation(id, user, copyForConsultation, seat, reservationDate, timestamp);
		}
		return reservation;
		
	}
	
	public Consultation startConsultation(Consultation consultation)throws SQLException{
		String query = "insert into consultation (userid, copyid) values (?,?) returning id";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setInt(1, consultation.getUser().getID());
		pstmt.setInt(2, consultation.getCopy().getID());
		
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()){
			int id = rs.getInt(1);
			consultation.setID(id);
		}else{
			throw new SQLException("Cannot return id");
		}
		return consultation;
	}
	
	public void endConsultation(Consultation consultation)throws SQLException{
		String query = "update consultation set end_date=current_timestamp where id=?";
		
		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt.setInt(1, consultation.getID());
		
		pstmt.execute();
	}
	
	public boolean authenticateUser(String hashedPassword)throws SQLException{
		return false;
	}
	
	public CopyForConsultation getOneAvailableCopyForConsultation(Book book, LocalDate date) throws SQLException{
		
		String query = "select lm_copy.id, title, authors, year, main_topic, phouse, status "+
						"from book join lm_copy on book.id=lm_copy.bookid where lm_copy.id not in "+
						"(select copyid from consultation_reservation where reservation_date=?) "+
						"and title=? and authors=? and for_consultation = true";
		
		PreparedStatement pstmt = this.connection.prepareStatement(query);
		pstmt.setObject(1, date);
		pstmt.setString(2, book.getTitle());
		pstmt.setArray(3, this.connection.createArrayOf("varchar", book.getAuthors()));
		
		CopyForConsultation copy = null;
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			Copy from = this.getCopyFrom(rs, 1);
			copy = CopyForConsultation.create(from);
			copy.setID(from.getID());
		}
		return copy;
	}
	

}
