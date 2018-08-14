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

import java.security.InvalidParameterException;
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

public class DbManager {

	Connection connection;


	public DbManager(String path, String username, String password) throws SQLException, ClassNotFoundException{

		Class.forName("org.postgresql.Driver");
		connection = DriverManager.getConnection("jdbc:postgresql://"+path, username, password);
	}

	public void close() throws SQLException{
		connection.close();
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

		String query = "update loan set start_date=?, end_date=?, renew_available=?, restitution_date=? "+
						"where id = ?";

		PreparedStatement pstmt = connection.prepareStatement(query);

		pstmt.setObject(1, loan.getStart());
		pstmt.setObject(2, loan.getEnd());
		pstmt.setBoolean(3, loan.isRenewAvailable());
		pstmt.setObject(4, loan.getRestitutionDate());
		pstmt.setInt(5, loan.getID());

		pstmt.execute();

	}

	public List<Seat> getSeats() throws SQLException{

		String query = "select seat_number, table_number, free from seat";
		Statement stat = connection.createStatement();

		ResultSet rs = stat.executeQuery(query);
		List<Seat> seats = new LinkedList<Seat>();
		while(rs.next()){
			Seat seat = DbManagerHelper.getSeatFrom(rs, 1, true);
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
	
	public void setLoanReservationDone(LoanReservation reservation) throws SQLException{
		
		String query = "update loan_reservation set done = true where id = ?";
		PreparedStatement pstmt = this.connection.prepareStatement(query);
		
		pstmt.setInt(1, reservation.getID());
		
		pstmt.execute();
	
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
		}
		return reservation;
	}

	public /*@ pure @*/ List<Seat> getAvailableSeats(LocalDate date) throws SQLException{

		// with the new addiction is very easy to find out a free seat.
		// TODO ADD A STUDY ALMOST EVERYWHERE.
		String query = "select seat_number,table_number, free from seat "+
				 "as s where not exists "+
				"(select * from seat_reservation as sr where reservation_date = ? and "+
				"s.seat_number = sr.seat_number and s.table_number = sr.table_number)"+
				/* "union select * from study "+ */
				/* "where study.seat_number = s.seat_number and study.table_number = s.table_number)";*/
				 "and free = true ";
		
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

	public List<LoanReservation> getLoanReservationsByUser(InternalUser user,
			boolean useDoneParam, boolean doneParam) throws SQLException{

		String query = DbManagerHelper.getLoanReservationByUserQuery(useDoneParam);

		PreparedStatement pstmt = connection.prepareStatement(query);
		
		pstmt = DbManagerHelper.prepareQueryLoanReservationDoneParam(pstmt,
				user, useDoneParam, doneParam);

		ResultSet rs = pstmt.executeQuery();

		List<LoanReservation> reservations = new LinkedList<LoanReservation>();


		while (rs.next()){

			int id = rs.getInt(1);

			OffsetDateTime timestamp = (OffsetDateTime) rs.getObject(9, OffsetDateTime.class);

			Copy copy = DbManagerHelper.getCopyFrom(rs, 2);

			LoanReservation reservation = new LoanReservation(id, user, copy, timestamp);

			reservations.add(reservation);

		}

		return reservations;
	}

//	public ConsultationReservation getConsultationReservationByUserCopy(
//			InternalUser user, LocalDate date, String title, String [] authors,
//			int year, String mainTopic, String phouse) throws SQLException{
//
//		String query = DbManagerHelper.getConsultationReservationQuery(
//				title, authors, year, mainTopic, phouse);
//
//		int lastUsedIndex = 1;
//		PreparedStatement pstmt = this.connection.prepareStatement(query);
//
//		Object [] res = DbManagerHelper.searchPrepare(lastUsedIndex, pstmt,
//				this.connection, title, authors, year, mainTopic, phouse);
//
//		// System.out.println(query);
//
//		pstmt = (PreparedStatement) res[0];
//		lastUsedIndex = (int) res[1];
//
//		pstmt.setInt(lastUsedIndex, user.getID());
//		pstmt.setObject(lastUsedIndex + 1, date);
//
//		ResultSet rs = pstmt.executeQuery();
//
//		ConsultationReservation reservation = null;
//
//		if(rs.next()){
//
//			reservation = DbManagerHelper.getFullConsultationReservationFrom(rs, 1, user);
//		}
//
//		return reservation;
//	}

//	public LoanReservation getLoanReservationByUserCopy(InternalUser user, String title, String [] authors,
//			int year, String mainTopic, String phouse) throws SQLException{
//
//
//		String query = DbManagerHelper.getLoanReservationQuery(title,
//				authors, year, mainTopic, phouse);
//
//		int lastUsedIndex = 1;
//		PreparedStatement pstmt = this.connection.prepareStatement(query);
//    	Object [] res = DbManagerHelper.searchPrepare(lastUsedIndex, pstmt,
//    			this.connection, title, authors, year, mainTopic, phouse);
//
//    	pstmt = (PreparedStatement) res[0];
//    	lastUsedIndex = (int) res[1];
//
//    	pstmt.setInt(lastUsedIndex, user.getID());
//
//		ResultSet rs = pstmt.executeQuery();
//
//		LoanReservation reservation = null;
//
//		if(rs.next()){
//
//			Copy copy = DbManagerHelper.getCopyFrom(rs, 1);
//			reservation = DbManagerHelper.getLoanReservation(rs, 9, copy, user);
//
//		}
//		return reservation;
//	}

//	public LoanReservation getLoanReservationByUserCopy(InternalUser user, Copy copy) throws SQLException{
//
//		String query = "select id, time_stamp from loan_reservation "+
//						"where userid=? and copyid=? and done = false "+
//						"order by time_stamp desc limit 1";
//
//		PreparedStatement pstmt = this.connection.prepareStatement(query);
//		pstmt.setInt(1, user.getID());
//		pstmt.setInt(2, copy.getID());
//
//		LoanReservation reservation = null;
//		ResultSet rs = pstmt.executeQuery();
//		if(rs.next()){
//
////			int id = rs.getInt(1);
////			OffsetDateTime timestamp = rs.getObject(2, OffsetDateTime.class);
////			reservation = new LoanReservation(id, user, copy, timestamp);
//			reservation = DbManagerHelper.getLoanReservation(rs, 1, copy, user);
//		}
//		return reservation;
//	}
	
//	public Loan getLoanByUserCopy(User user, String title, String [] authors,
//			int year, String mainTopic, String phouse) throws SQLException{
//		
//		String query = DbManagerHelper.getLoanByUserCopyQuery(title,
//				authors, year, mainTopic, phouse);
//		
//		PreparedStatement pstmt = this.connection.prepareStatement(query);
//		
//		Object [] res = DbManagerHelper.searchPrepare(1, pstmt,
//				this.connection, title, authors, year, mainTopic, phouse);
//		
//		pstmt = (PreparedStatement) res[0];
//		
//		Loan loan = null;
//		
//		ResultSet rs = pstmt.executeQuery();
//		
//		if(rs.next()){
//			Copy copy = DbManagerHelper.getCopyFrom(rs, 1);
//			loan = DbManagerHelper.getLoanFrom(rs, 9, copy, user);
//		}
//		return loan;
//	}


//	@Deprecated
//	public Loan getLoanByUserCopy(User user, Copy copy, boolean delivered) throws SQLException{
//		String query = "select id, start_date, end_date, restitution_date, renew_available "+
//						"from loan where userid=? "+
//						"and copyid=?";
//
//		if (delivered){
//			query += " and restitution_date is not null";
//		}else{
//			query += " and restitution_date is null";
//		}
//
//		PreparedStatement pstmt = this.connection.prepareStatement(query);
//
//		pstmt.setInt(1, user.getID());
//		pstmt.setInt(2, copy.getID());
//
//		ResultSet rs = pstmt.executeQuery();
//		Loan loan = null;
//		if(rs.next()){
//			loan = DbManagerHelper.getLoanFrom(rs, 1, copy, user);
//		}
//		return loan;
//	}


	public Loan getActiveLoanByCopy(Copy copy) throws SQLException{
		String query = "select id, start_date, end_date, restitution_date, renew_available, userid "+
						"from loan where copyid = ? and end_date is null";

		PreparedStatement pstmt = this.connection.prepareStatement(query);

		pstmt.setInt(1, copy.getID());

		ResultSet rs = pstmt.executeQuery();
		Loan loan = null;
		if(rs.next()){

			User user = new User(rs.getInt(6));
			loan = DbManagerHelper.getLoanFrom(rs, 1, copy, user);
		}

		return loan;
	}

//	public void setSeatOccupied(Seat seat, boolean occupied) throws SQLException{
//		String query = "update seat set free=? where table_number=? and seat_number=?";
//
//		PreparedStatement pstmt = connection.prepareStatement(query);
//
//		pstmt.setBoolean(1, !occupied);
//		pstmt.setInt(2, seat.getTableNumber());
//		pstmt.setInt(3, seat.getNumber());
//
//		pstmt.execute();
//	}

//	public SeatReservation getSeatReservationOrNothing(InternalUser user, LocalDate date)throws SQLException{
//		String query = "select id, seat_number, table_number, time_stamp, reservation_date from seat_reservation where userid=? and reservation_date=?";
//
//		PreparedStatement pstmt = connection.prepareStatement(query);
//
//		pstmt.setInt(1, user.getID());
//		pstmt.setObject(2, date);
//
//		ResultSet rs = pstmt.executeQuery();
//		SeatReservation reservation = null;
//		if (rs.next()){
//			int id = rs.getInt(1);
//			OffsetDateTime timestamp = (OffsetDateTime) rs.getObject(4, OffsetDateTime.class);
//			LocalDate reservationDate = (LocalDate) rs.getObject(5, LocalDate.class);
//			Seat seat = new Seat(rs.getInt(2), rs.getInt(3), false);
//			reservation = new SeatReservation(id, reservationDate, user, seat, timestamp);
//		}
//		return reservation;
//	}

	public Seat getReservedSeatOrNothing(InternalUser user, LocalDate date) throws SQLException{

		String query = "select seat_number, table_number "+
						"from seat_reservation where reservation_date = ? "+
						"and userid=? "+
						"union "+
						"select seat_number, table_number "+
						"from consultation_reservation where reservation_date = ? "+
						"and userid=? "+
						"union "+
						"select seat_number, table_number "+
						"from study where userid=?";

		PreparedStatement pstmt = this.connection.prepareStatement(query);

		pstmt.setObject(1, date);
		pstmt.setInt(2, user.getID());
		pstmt.setObject(3, date);
		pstmt.setInt(4, user.getID());
		pstmt.setInt(5, user.getID());

		Seat seat = null;
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			seat = new Seat(rs.getInt(1), rs.getInt(2), false);
		}

		return seat;
	}


//	@Deprecated
//	public ConsultationReservation getConsultationReservation(InternalUser user, Book book, LocalDate date)
//					throws SQLException{
//
//		String query = "select copyid, title, authors, year, main_topic, "+
//						"phouse, status, for_consultation, "+
//						"seat_number, table_number, "+ // seat
//						"cr.id, time_stamp, reservation_date "+
//						"from book join lm_copy on book.id = lm_copy.bookid "+
//						"join consultation_reservation as cr on lm_copy.id = cr.copyid "+
//						"where cr.userid=? and reservation_date = ? and "+
//						"title=? and authors=?";
//
//		PreparedStatement pstmt = connection.prepareStatement(query);
//
//		pstmt.setInt(1, user.getID());
//		// pstmt.setInt(2, book.getID());
//		pstmt.setObject(2, date);
//		pstmt.setString(3, book.getTitle());
//		pstmt.setArray(4, connection.createArrayOf("varchar", book.getAuthors()));
//
//		ResultSet rs = pstmt.executeQuery();
//		ConsultationReservation reservation = null;
//		if (rs.next()){
//
////			Copy copy = DbManagerHelper.getCopyFrom(rs, 1);
////			Seat seat = DbManagerHelper.getSeatFrom(rs, 8, false);
////
////			reservation = DbManagerHelper.getConsultationReservationFrom(rs, 10, copy, seat, user);
//
//			reservation = DbManagerHelper.getFullConsultationReservationFrom(rs, 1, user);
//		}
//		return reservation;
//	}

	public Consultation startConsultation(Consultation consultation)throws SQLException{
		String query = "insert into consultation (userid, copyid, seat_number, table_number) "+
						"values (?, ?, ?, ?) returning id";

		PreparedStatement pstmt = connection.prepareStatement(query);

		pstmt.setInt(1, consultation.getUser().getID());
		pstmt.setInt(2, consultation.getCopy().getID());
		pstmt.setInt(3, consultation.getSeat().getNumber());
		pstmt.setInt(4, consultation.getSeat().getTableNumber());

		ResultSet rs = pstmt.executeQuery();
		if (rs.next()){
			int id = rs.getInt(1);
			consultation.setID(id);
		}else{
			throw new SQLException("Cannot return id");
		}
		return consultation;
	}

	
	public void autoSave(boolean save) throws SQLException{
		this.connection.setAutoCommit(save);
	}
	
	/**
	 * Commit the current transaction
	 * @param setSave if <pre>true</pre>: <pre>this.connection.setAutoCommit(true);</pre>
	 * @throws SQLException
	 */
	public void commit(boolean setSave) throws SQLException{
		this.connection.commit();
		if(setSave){
			this.autoSave(true);
		}
	}

	public void endConsultation(Consultation consultation)throws SQLException{
		String query = "update consultation set end_date=current_timestamp where id=?";

		PreparedStatement pstmt = connection.prepareStatement(query);

		pstmt.setInt(1, consultation.getID());

		pstmt.execute();
	}
	
	public void setConsultationReservationDone(ConsultationReservation reservation) throws SQLException{
		
		String query = "update consultation_reservation set done=true where id=?";
		
		PreparedStatement pstmt = this.connection.prepareStatement(query);
		
		pstmt.setInt(1, reservation.getID());
		
		pstmt.execute();
	}

//	public User getUser(Emailable login) throws SQLException{
//
//		String query = "select id, name, surname, email, internal "+
//						"from lm_user where email = ?";
//
//		PreparedStatement pstmt = this.connection.prepareStatement(query);
//
//		pstmt.setString(1, login.getEmail());
//
//		User user = null;
//
//		ResultSet rs = pstmt.executeQuery();
//
//		if(rs.next()){
//
//		}
//
//		return user;
//	}

	public Librarian authenticateLibrarian(Librarian librarian) throws SQLException{
		String query =  "select id, email from librarian where "+
						"email=? and password=?";

		PreparedStatement pstmt = this.connection.prepareStatement(query);

		pstmt.setString(1, librarian.getEmail());
		pstmt.setString(2, librarian.getHashedPassword());

		Librarian returned = new Librarian();

		ResultSet rs = pstmt.executeQuery();

		if (rs.next()){
			returned.setID(rs.getInt(1));
			returned.setEmail(rs.getString(2));
		}

		return returned;
	}

	public User authenticateUser(Librarian user)throws SQLException{
		String query = "select id, name, surname, email, internal "+
						" from lm_user where email=? and password=?";

		PreparedStatement pstmt = this.connection.prepareStatement(query);

		pstmt.setString(1, user.getEmail());
		pstmt.setString(2, user.getHashedPassword());

		ResultSet rs = pstmt.executeQuery();

		User returned = null;

		if(rs.next()){
			returned = DbManagerHelper.getUserFrom(rs, 1);
		}
		return returned;
	}

//	public Copy getOneAvailableCopyForLoan(String title, String [] authors, int year,
//			String mainTopic, String phouse) throws SQLException{
//
//		String query = DbManagerHelper.getSearchQuery(
//				title, authors, year, mainTopic, phouse);
//
//		query += "and status = \'free\' and for_consultation = false limit 1";
//
//		PreparedStatement pstmt = this.connection.prepareStatement(query);
//
//		pstmt = (PreparedStatement)(DbManagerHelper.searchPrepare(
//				1, pstmt, this.connection,
//				title, authors, year, mainTopic, phouse)[0]);
//
//		Copy copy = null;
//
//		ResultSet rs = pstmt.executeQuery();
//
//		if(rs.next()){
//			copy = DbManagerHelper.getCopyFrom(rs, 1);
//		}
//
//		return copy;
//	}
	
	public List<Copy> getAvailableCopiesForLoan(String title, String [] authors, int year,
			String mainTopic, String phouse) throws SQLException{

		String query = DbManagerHelper.getSearchQuery(
				title, authors, year, mainTopic, phouse);

		query += "and status = \'free\' and for_consultation = false";

		PreparedStatement pstmt = this.connection.prepareStatement(query);

		pstmt = (PreparedStatement)(DbManagerHelper.searchPrepare(
				1, pstmt, this.connection,
				title, authors, year, mainTopic, phouse)[0]);

		List<Copy> copies = new LinkedList<Copy>();

		ResultSet rs = pstmt.executeQuery();

		while(rs.next()){
			Copy copy = DbManagerHelper.getCopyFrom(rs, 1);
			copies.add(copy);
		}

		return copies;
	}

//	public CopyForConsultation getOneAvailableCopyForConsultation(LocalDate date,
//						String title,
//						String [] authors, int year, String mainTopic,
//						String phouse) throws SQLException{
//
//		String query = DbManagerHelper.getOneAvailableCopyForConsultationQuery(
//			title, authors, year, mainTopic, phouse);
//
//
//		PreparedStatement pstmt = this.connection.prepareStatement(query);
//
//		Object [] res = DbManagerHelper.searchPrepare(1, pstmt, this.connection,
//				title, authors, year, mainTopic, phouse);
//
//		pstmt = (PreparedStatement) res[0];
//		int lastUsedIndex = (int) res[1];
//
//		pstmt.setObject(lastUsedIndex, date);
//
//		CopyForConsultation copy = null;
//		ResultSet rs = pstmt.executeQuery();
//		if(rs.next()){
//			Copy from = DbManagerHelper.getCopyFrom(rs, 1);
//			copy = new CopyForConsultation(from);
//			// copy.setID(from.getID());
//		}
//
//		return copy;
//	}
	
	
	public List<CopyForConsultation> getAvailableCopiesForConsultation(LocalDate date,
			String title,
			String [] authors, int year, String mainTopic,
			String phouse) throws SQLException{

		String query = DbManagerHelper.getOneAvailableCopyForConsultationQuery(
				title, authors, year, mainTopic, phouse);


		PreparedStatement pstmt = this.connection.prepareStatement(query);

		Object [] res = DbManagerHelper.searchPrepare(1, pstmt, this.connection,
				title, authors, year, mainTopic, phouse);

		pstmt = (PreparedStatement) res[0];
		int lastUsedIndex = (int) res[1];

		pstmt.setObject(lastUsedIndex, date);

		List<CopyForConsultation> copies = new LinkedList<CopyForConsultation>();
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()){
			Copy from = DbManagerHelper.getCopyFrom(rs, 1);
			CopyForConsultation copy = new CopyForConsultation(from);
			copies.add(copy);
			// copy.setID(from.getID());
		}

		return copies;
	}


	public CopyForConsultation getIfAvailableForConsultation(CopyForConsultation copy, LocalDate date) throws SQLException{

		String query = "select lm_copy.id, title, authors, year, main_topic, "+
					   "phouse, status, for_consultation "+
					   "from lm_copy join book on lm_copy.bookid=book.id "+
					   "where "+
					   "lm_copy.id=? and lm_copy.id not in "+
					   "(select copyid from consultation_reservation "+
					   "where lm_copy.id=? and reservation_date=?) "+
					   "and for_consultation = true limit 1";

		PreparedStatement pstmt = this.connection.prepareStatement(query);
		pstmt.setInt(1, copy.getID());
		pstmt.setInt(2, copy.getID());
		pstmt.setObject(3, date);

		ResultSet rs = pstmt.executeQuery();
		CopyForConsultation returned = null;
		if(rs.next()){
			Copy from = DbManagerHelper.getCopyFrom(rs, 1);
			returned = new CopyForConsultation(from);
			// returned.setID(from.getID());
		}
		return returned;
	}

	// IMPORTANT: on consultation user cannot decide year and so, just author and title.
//	@Deprecated
//	public CopyForConsultation getOneAvailableCopyForConsultation(Book book, LocalDate date) throws SQLException{
//
//		String query = "select lm_copy.id, title, authors, year, main_topic, phouse, status "+
//						"from book join lm_copy on book.id=lm_copy.bookid where lm_copy.id not in "+
//						"(select copyid from consultation_reservation where reservation_date=?) "+
//						"and title=? and authors=? and for_consultation = true "+
//						"limit 1";
//
//		PreparedStatement pstmt = this.connection.prepareStatement(query);
//		pstmt.setObject(1, date);
//		pstmt.setString(2, book.getTitle());
//		pstmt.setArray(3, this.connection.createArrayOf("varchar", book.getAuthors()));
//
//		CopyForConsultation copy = null;
//		ResultSet rs = pstmt.executeQuery();
//		if(rs.next()){
//			Copy from = DbManagerHelper.getCopyFrom(rs, 1);
//			copy = CopyForConsultation.create(from);
//			copy.setID(from.getID());
//		}
//		return copy;
//	}


//	@Deprecated
//	public void registerLoanDelivered(Loan loan) throws SQLException{
//
//		String query = "update loan set restitution_date = current_date where id=?";
//
//		PreparedStatement pstmt = this.connection.prepareStatement(query);
//
//		pstmt.setInt(1, loan.getID());
//		pstmt.execute();
//	}


	private SeatReservation getSeatReservationFrom(ResultSet rs, int startingIndex, InternalUser user) throws SQLException{

		int id = rs.getInt( startingIndex );
		Seat seat = DbManagerHelper.getSeatFrom(rs, startingIndex + 1, false);
		LocalDate reservationDate = rs.getObject(startingIndex + 3, LocalDate.class);
		OffsetDateTime timestamp = rs.getObject(startingIndex + 4, OffsetDateTime.class);

		SeatReservation reservation = new SeatReservation(id,
				reservationDate, user,
				seat, timestamp
				);

		return reservation;
	}


	public List<SeatReservation> getSeatsReservationByUser(InternalUser user) throws SQLException{

		String query = "select id, seat_number, table_number, reservation_date, "+
						"time_stamp from seat_reservation where userid=?";

		PreparedStatement pstmt = this.connection.prepareStatement(query);

		pstmt.setInt(1, user.getID());
		ResultSet rs = pstmt.executeQuery();
		List<SeatReservation> reservations = new LinkedList<SeatReservation>();
		while(rs.next()){
			reservations.add(this.getSeatReservationFrom(rs, 1, user));
		}

		return reservations;
	}

	/**
	 * get the consultations reservations for a given user, and possibly, for a given date.
	 * @param user 
	 * @param date can be <pre>null</pre> if you want all the reservations.
	 * @return
	 * @throws SQLException
	 */
    public List<ConsultationReservation> getConsultationReservationByUser(InternalUser user, LocalDate date,
    		boolean useDoneParam, boolean doneParam) throws SQLException{

		String query = DbManagerHelper.getConsultationReservationByUserQuery(date, useDoneParam);

		PreparedStatement pstmt = this.connection.prepareStatement(query);

		pstmt = DbManagerHelper.prepareQueryConsultationReservationDoneParam(pstmt, user, date,
				useDoneParam, doneParam);
		
	
		ResultSet rs = pstmt.executeQuery();
		List<ConsultationReservation> reservations = new LinkedList<ConsultationReservation>();
		while (rs.next()){
			reservations.add(DbManagerHelper.getFullConsultationReservationFrom(rs, 1, user));
		}
		return reservations;
    }
    
   

    public List<Loan> getLoansByUser(User user, boolean delivered, boolean checkDelivered) throws SQLException{

		String query = "select loan.id, start_date, end_date, restitution_date, renew_available, "+
				"lm_copy.id, title, authors, year, main_topic, phouse, status "+
				"from loan join lm_copy on copyid=lm_copy.id join book on bookid=book.id "
				+"where userid=? ";

		if (checkDelivered){
			if (delivered){
				query += " and restitution_date is not null";
			}else{
				query += " and restitution_date is null";
			}
		}
		
		// System.out.println(query);;


		PreparedStatement pstmt = this.connection.prepareStatement(query);
		pstmt.setInt(1, user.getID());

		ResultSet rs = pstmt.executeQuery();
		List<Loan> loans = new LinkedList<Loan>();
		while(rs.next()){
			Copy copy = DbManagerHelper.getCopyFrom(rs, 6);
			Loan loan = DbManagerHelper.getLoanFrom(rs, 1, copy, user);

			loans.add(loan);
		}
		return loans;
    }

    /**
     *
     * @param title
     * @param author
     * @param year
     * @param mainTopic
     * @return
     * @throws SQLException
     */
    /*@
     @ requires !(title == null && authors == null && year == 0 && mainTopic == null && phouse == null) &&
     @  (authors != null) ==> authors.length > 0;
     @*/
    public List<Copy> search(String title, String [] authors, int year,
    		String mainTopic, String phouse) throws SQLException{

    	if (title == null && authors == null && year == 0 && mainTopic == null && phouse == null || (
    			authors != null && authors.length == 0)){
    		throw new InvalidParameterException("Wrong combination of parameters");
    	}


    	String query = DbManagerHelper.getSearchQuery(
    			title, authors, year, mainTopic, phouse);

    	PreparedStatement pstmt = this.connection.prepareStatement(query);

    	int lastUsedIndex = 1;

    	pstmt = (PreparedStatement)(DbManagerHelper.searchPrepare(
    			lastUsedIndex, pstmt,
    			this.connection, title, authors, year, mainTopic, phouse)[0]);

    	ResultSet rs = pstmt.executeQuery();

    	List<Copy> copies = new LinkedList<Copy>();

    	while(rs.next()){
    		Copy copy = DbManagerHelper.getCopyFrom(rs, 1);
    		boolean forConsultation = rs.getBoolean(8);
    		if (forConsultation){
    			CopyForConsultation otherCopy = new CopyForConsultation(copy);
    			copies.add(otherCopy);
    			// System.out.println(otherCopy.getID());
    		}else{
    			// System.out.println(copy.getID());
    			copies.add(copy);
    		}
    	}

    	return copies;
    }

    public List<User> users() throws SQLException{

    	String query = "select id, name, surname, email, internal from lm_user";

    	Statement stat = this.connection.createStatement();

    	List<User> users = new LinkedList<User>();

    	ResultSet rs = stat.executeQuery(query);

    	while (rs.next()){
    		User u = DbManagerHelper.getUserFrom(rs, 1);
    		users.add(u);
    	}

    	return users;
    }
    
    /**
     * 
     * @param user
     * @return
     * @throws SQLException
     */
    public List<Consultation> consultationsInProgressByUser(User user) throws SQLException {
    	
    	String query = DbManagerHelper.CONSULTATION_IN_PROGRESS_BY_USER_QUERY;
    	
    	PreparedStatement pstmt = this.connection.prepareStatement(query);
    	
    	pstmt.setInt(1, user.getID());
    	
    	
    	List<Consultation> consultations = new LinkedList<Consultation>();
    	
    	ResultSet rs = pstmt.executeQuery();
    	
    	while(rs.next()){
    		
    		Copy copy = DbManagerHelper.getCopyFrom(rs, 6);
    		CopyForConsultation copyForConsultation = new CopyForConsultation(copy);
    		
    		Consultation consultation = DbManagerHelper.getConsultationFrom(rs, 1, copyForConsultation, user);
    		
    		consultations.add(consultation);
    		
    	}
    	
    	return consultations;
    }
    
    
    public List<Consultation> consultationsInProgress() throws SQLException{
    	
    	String query = DbManagerHelper.CONSULTATION_IN_PROGRESS_QUERY;
    	
    	Statement stat = this.connection.createStatement();
    	
    	List<Consultation> consultations = new LinkedList<Consultation>();
    	
    	ResultSet rs = stat.executeQuery(query);
    	
    	while(rs.next()){
    		Copy copy = DbManagerHelper.getCopyFrom(rs, 6);
    		CopyForConsultation copyForConsultation = new CopyForConsultation(copy);
    		
    		User user = DbManagerHelper.getUserFrom(rs, 13);
    		
    		Consultation consultation = DbManagerHelper.getConsultationFrom(rs, 1, copyForConsultation, user);
    		
    		consultations.add(consultation);
    	}
    	
    	return consultations;
    }
    
    public User getUser(String email) throws SQLException{
    	
    	String query = "select id, name, surname, email, internal from lm_user "+
    					"where email = ?";
    	
    	PreparedStatement pstmt = this.connection.prepareStatement(query);
    	pstmt.setString(1, email);
    	
    	User user = null;
    	ResultSet rs = pstmt.executeQuery();
    	
    	if(rs.next()){
    		
    		user = DbManagerHelper.getUserFrom(rs, 1);
    	}
    	return user;
    }
    
    /**
     * getLoanInLate returns a List that contains all the loans in late,
     * the ones that: end_date < today and restitution_date is null.
     * @param user can be <pre>null</pre> to get all
     * @return List<Loan>
     * @throws SQLException
     */
    public List<Loan> getLoansInLate(User user) throws SQLException{
    	
    	String query = null;
    	if(user != null){
    		query = DbManagerHelper.LOANS_IN_LATE_BY_USER_QUERY;
    	}else{
    		query = DbManagerHelper.LOANS_IN_LATE_QUERY;
    	}
    	
    	PreparedStatement pstmt = this.connection.prepareStatement(query);
    	
    	if(user != null){
    		pstmt.setInt(1, user.getID());
    	}
    	
    	List<Loan> loans = new LinkedList<Loan>();
    	
    	ResultSet rs = pstmt.executeQuery();
    	
    	while(rs.next()){
    		Loan loan = null;
    		Copy copy = null;
    		
    		if(user == null){
        		user = DbManagerHelper.getUserFrom(rs, 1);
        		copy = DbManagerHelper.getCopyFrom(rs, 11);
        		loan = DbManagerHelper.getLoanFrom(rs, 6, copy, user);
    		}else{
    			copy = DbManagerHelper.getCopyFrom(rs, 6);
    			loan = DbManagerHelper.getLoanFrom(rs, 1, copy, user);
    		}

    		loans.add(loan);
    	}
    	return loans;
    }
    
    /**
     * <pre>getDeletableBooks()</pre> returns the list
     * of the Book objects that are not linked with a copy.
     * @return
     * @throws SQLException
     */
    public List<? extends Book> getDeletableBooks (boolean withCopiesNumber) throws SQLException{
    	 
    	String query = "select id, title, authors, year, main_topic, phouse, 'free'"+
    					"from book where id not in ("+
    					"select bookid from lm_copy)";
    	
    	Statement stat = this.connection.createStatement();
    	
    	List<Book> books = new LinkedList<Book>();
    	
    	ResultSet rs = stat.executeQuery(query);
    	
    	while(rs.next()){
    		Book book = DbManagerHelper.getCopyFrom(rs, 1);
    		
    		if (withCopiesNumber){
    			book = new BookCopiesNumber(book, 0);
    		}
    		
    		books.add(book);
    	}
    	
    	return books;
    }
    
    public List<BookCopiesNumber> bookCopiesNumber() throws SQLException{
    	
    	// Pg allows this not-perfect query recognizing that we group by a 
    	// primary key.
    	
    	// a left join is not enough because the count is evaluated to 1
    	String query = "select book.id, title, authors, year, main_topic, phouse, " +
    				   "'free', count(*) as c "+
    				   "from book join lm_copy on book.id = lm_copy.bookid "+
    				   "group by book.id "+
    				   //"order by title, authors "+
    				   "union "+
    				   "select book.id, title, authors, year, main_topic, phouse, " +
    				   "'free', 0 as c "+
    				   "from book where book.id not in ( "+
    				   "select bookid from lm_copy) "+
    				   "order by c desc, title, authors asc";
    	
    	Statement stat = this.connection.createStatement();
    	
    	List<BookCopiesNumber> books = new LinkedList<BookCopiesNumber>();
    	
    	ResultSet rs = stat.executeQuery(query);
    	
    	while(rs.next()){
    		BookCopiesNumber b = DbManagerHelper.getBookCopiesNumberFrom(rs, 1);
    		books.add(b);
    	}
    	
    	return books;
    }
    
    public int deleteSomeCopies(Book book, int number) throws SQLException{
    	
    	String query = "select delete_copies(?, ?)";
    	
    	PreparedStatement pstmt = this.connection.prepareStatement(query);
    	
    	pstmt.setInt(1, book.getID());
    	pstmt.setInt(2, number);
    	
    	int res = 0;
    	
    	ResultSet rs = pstmt.executeQuery();
    	rs.next();
    	res = rs.getInt(1);
    	
    	return res;
    }
    
    public void addSomeCopies(Book book, int number, boolean forConsultation) throws SQLException{
    	
    	String query = "select add_more_copies(?, ?, ?)";
    	
    	PreparedStatement pstmt = this.connection.prepareStatement(query);
    	
    	pstmt.setInt(1, book.getID());
    	pstmt.setInt(2, number);
    	pstmt.setBoolean(3, forConsultation);
    	
    	pstmt.execute();
    }
    
    
    public List<Study> getStudies() throws SQLException {
    	
    	String query = "select lm_user.id, name, surname, email, internal, "+
    			"study.id, seat_number, table_number "+
    			"from study join lm_user on userid=lm_user.id "+
    			"order by table_number, seat_number";
    	
    	Statement stat = this.connection.createStatement();
    	
    	ResultSet rs = stat.executeQuery(query);
    	
    	List<Study> studies = new LinkedList<Study>();
    	
    	while(rs.next()){
    		User user = DbManagerHelper.getUserFrom(rs, 1);
    		
    		Study study = DbManagerHelper.getStudyFrom(rs, 6, user);
    		
    		studies.add(study);
    	}
    	
    	return studies;
    }
    
    public /*@ pure @*/ Study getStudyByUser(User user) throws SQLException {
    	
    	String query = "select study.id, "+
    			"seat_number, table_number "+
    			"from study where userid=?";
    	
    	PreparedStatement pstmt = this.connection.prepareStatement(query);
    	
    	pstmt.setInt(1, user.getID());
    	
    	ResultSet rs = pstmt.executeQuery();
    	
    	rs.next();
    		
    	Study study = DbManagerHelper.getStudyFrom(rs, 1, user);
    	
    	return study;
    }
    
    public void tryAddStudy(Study study) throws SQLException{
    	
    	String query = "select try_add_study(?, ?, ?)";
    	
    	PreparedStatement pstmt = this.connection.prepareStatement(query);
    	
    	pstmt.setInt(1, study.getUser().getID());
    	pstmt.setInt(2, study.getSeat().getNumber());
    	pstmt.setInt(3, study.getSeat().getTableNumber());
    	
    	pstmt.execute();
    	
    	// ResultSet rs = pstmt.executeQuery();
    	
    	// rs.next();
    	
    	// int id = rs.getInt(1);
    	// study.setID(id);
    	
    	// return study;
    }
    
    
    public void deleteStudyByUser(User user) throws SQLException{
    	
    	String query = "delete from study where userid=?";
    	
    	PreparedStatement pstmt = this.connection.prepareStatement(query);
    	
    	pstmt.setInt(1, user.getID());
    	
    	pstmt.execute();
    }
    
    
    public void cleanup() throws SQLException{
    	
    	String query = "select cleanup_reservation()";
    	
    	Statement stat = this.connection.createStatement();
    	
    	stat.execute(query);
    }


}
