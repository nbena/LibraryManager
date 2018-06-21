package com.github.nbena.librarymanager.man;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import com.github.nbena.librarymanager.core.ConsultationReservation;
import com.github.nbena.librarymanager.core.Copy;
import com.github.nbena.librarymanager.core.CopyForConsultation;
import com.github.nbena.librarymanager.core.CopyStatus;
import com.github.nbena.librarymanager.core.InternalUser;
import com.github.nbena.librarymanager.core.Loan;
import com.github.nbena.librarymanager.core.LoanReservation;
import com.github.nbena.librarymanager.core.Seat;
import com.github.nbena.librarymanager.core.User;

public class DbManagerHelper {


	static User getUserFrom(ResultSet rs, int startingIndex) throws SQLException{

		User returned;
		int id = rs.getInt(startingIndex);
		String name = rs.getString(startingIndex + 1);
		String surname = rs.getString(startingIndex + 2);
		String email = rs.getString(startingIndex + 3);
		boolean internal = rs.getBoolean(startingIndex + 4);
		if (internal){
			returned = new InternalUser(id);
		}else{
			returned = new User(id);
		}
		returned.setName(name);
		returned.setSurname(surname);
		returned.setEmail(email);

		return returned;
	}

	static LoanReservation getLoanReservation(ResultSet rs, int startingIndex, Copy copy, InternalUser user) throws SQLException{
		LoanReservation  reservation = null;
		int id = rs.getInt(startingIndex );
		OffsetDateTime timestamp = rs.getObject(startingIndex + 1, OffsetDateTime.class);
		reservation = new LoanReservation(id, user, copy, timestamp);
		return reservation;
	}


	static Copy getCopyFrom(ResultSet rs, int startingIndex) throws SQLException{

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

	/**
	 * getSeatFrom builds a Seat object from the ResultSet
	 * starting at the given offset.
	 * @param rs the ResultSet
	 * @param startingIndex	the offset to start with
	 * @param useFree set to <pre>true</pre> if you want to fetch also
	 * this field, otherwise it'll be set to false
	 * @return a new Seat object.
	 * @throws SQLException
	 */
	static Seat getSeatFrom(ResultSet rs, int startingIndex, boolean useFree) throws SQLException{

		int seatNumber = rs.getInt(startingIndex );
		int tableNumber = rs.getInt(startingIndex + 1);
		boolean free = false;
		if (useFree){
			free = rs.getBoolean(startingIndex + 2);
		}


		Seat seat = new Seat(seatNumber, tableNumber, free);
		return seat;
	}

	static Loan getLoanFrom(ResultSet rs, int startingIndex, Copy copy, User user) throws SQLException{
		int id = rs.getInt(startingIndex);
		LocalDate start = rs.getObject(startingIndex + 1, LocalDate.class);
		LocalDate end = rs.getObject(startingIndex + 2, LocalDate.class);
		// boolean active = rs.getBoolean(startingIndex + 3);
		LocalDate restitutionDate = rs.getObject(startingIndex + 3, LocalDate.class);
		
		boolean renewAvailable = rs.getBoolean(startingIndex + 4);

		boolean active = (restitutionDate == null);

		Loan l = new Loan(id, user, copy, start, end, active, restitutionDate, renewAvailable);

		return l;
	}

	static ConsultationReservation getConsultationReservationFrom(
			ResultSet rs, int startingIndex,
			Copy copy,
			Seat seat,
			InternalUser user) throws SQLException{

		int id = rs.getInt(startingIndex);

		// Copy copy = DbManagerHelper.getCopyFrom(rs, startingIndex + 1);
		CopyForConsultation copyForConsultation = CopyForConsultation.create(copy);
		// Seat seat = DbManagerHelper.getSeatFrom(rs, startingIndex, false);

		OffsetDateTime timestamp = (OffsetDateTime) rs.getObject(startingIndex + 1, OffsetDateTime.class);
		LocalDate reservationDate = (LocalDate) rs.getObject(startingIndex + 2, LocalDate.class);

		ConsultationReservation reservation = new ConsultationReservation(id, user, copyForConsultation,
				seat, reservationDate, timestamp);

		return reservation;
	}

	static ConsultationReservation getFullConsultationReservationFrom(
			ResultSet rs, int startingIndex, InternalUser user) throws SQLException{
		Copy copy = DbManagerHelper.getCopyFrom(rs, startingIndex);
		Seat seat = DbManagerHelper.getSeatFrom(rs, startingIndex + 8, false);

		ConsultationReservation reservation = DbManagerHelper.getConsultationReservationFrom(rs, startingIndex + 10,
				copy, seat, user);

		return reservation;
	}

	static String getSearchQuery(String title, String [] authors, int year,
			String mainTopic){
    	String query = "select lm_copy.id, title, authors, year, main_topic, phouse, "+
				"status, for_consultation from lm_copy join book on lm_copy.bookid " +
				"= book.id where ";

    	if (title != null){
    		query += "title like ? and ";
    	}
    	if (authors != null){
    		query += "authors @> ? and ";
    	}
    	if (year != 0){
    		query += "year = ? and ";
    	}
    	if(mainTopic != null){
    		query += "main_topic like ? ";
    	}

    	if (query.endsWith("and ")){
    		query = query.substring(0, query.lastIndexOf("and "));
    	}

    	return query;
	}

	static String getConsultationReservationQuery(String title, String [] authors,
			int year, String mainTopic){

		String query = DbManagerHelper.getLoanReservationQuery(
				title, authors, year, mainTopic)
				.replaceAll("loan_reservation.id, time_stamp",
						"seat_number, table_number, "+ // seat
						"cr.id, time_stamp, reservation_date ")
				.replaceAll("from loan_reservation join lm_copy on loan_reservation.copyid = lm_copy.id",
							"from consultation_reservation as cr join lm_copy on cr.copyid = lm_copy.id")
				.replaceAll("and loan_reservation.userid=\\?",
						" and cr.userid=\\? ")
				.concat(" and reservation_date=?");

		return query;
	}


	static String getLoanReservationQuery(String title, String [] authors,
			int year, String mainTopic){
		String query = DbManagerHelper.getSearchQuery(title, authors, year, mainTopic)
				.replaceAll("select lm_copy.id, title, authors, year, main_topic, phouse, "+
				"status, for_consultation",
				"select lm_copy.id, title, authors, year, main_topic, phouse, "+
				"status, for_consultation, loan_reservation.id, time_stamp "
				)
				.replaceAll("from lm_copy join book",
				"from loan_reservation join lm_copy on loan_reservation.copyid = lm_copy.id join book"
				)
				.concat("and loan_reservation.userid=?");

		return query;
	}
	
	static String getLoanByUserCopyQuery(String title, String [] authors,
			int year, String mainTopic){
		
		String query = DbManagerHelper.getSearchQuery(title, authors,
				year, mainTopic)
				.replaceAll("from lm_copy join book",
						"from loan join lm_copy on copyid=lm_copy.id join book ")
				.replaceAll("for_consultation",
						"for_consultation, loan.id, start_date, end_date, "+
						"restitution_date, renew_available ");
		
		return query;
	}

	static String getOneAvailableCopyForConsultationQuery(String title,
			String [] authors, int year, String mainTopic){

//				String internalQuery = DbManagerHelper.getSearchQuery(
//					title, authors, year, mainTopic)
//					.replaceAll("select lm_copy.id, title, authors, year, main_topic, "+
//											"phouse, status, for_consultation",
//											"select lm_copy.id ");
		String internalQuery = "select lm_copy.id "+
							   "from lm_copy join consultation_reservation "+
							   "on lm_copy.id = consultation_reservation.copyid "+
							   "where reservation_date=?";
		
		String query = DbManagerHelper.getSearchQuery(title, authors,
			year, mainTopic)
			.concat("and lm_copy.id not in ("+internalQuery+") ")
			.concat("and for_consultation=true ")
			.concat("limit 1");

		return query;
	}


	static Object[] searchPrepare(int lastUsedIndex, PreparedStatement pstmt,
			Connection connection,
			String title, String [] authors, int year,
			String mainTopic) throws SQLException{

    	if (title!=null){
    		pstmt.setString(lastUsedIndex, "%"+title+"%");
    		lastUsedIndex++;
    	}
    	if (authors != null){
    		pstmt.setArray(lastUsedIndex, connection.createArrayOf("varchar", authors));
    		lastUsedIndex++;
    	}

    	if (year != 0){
    		pstmt.setInt(lastUsedIndex, year);
    		lastUsedIndex++;
    	}
    	if(mainTopic != null){
    		pstmt.setString(lastUsedIndex, "%"+mainTopic+"%");
    		lastUsedIndex++;
    	}

    	return new Object[]{pstmt, lastUsedIndex};
	}

	// used in test
	static 	String queryOnMultipleId(String initial, int length, boolean and){
		String logical = and ? "and" : "or";
		String query = initial;
		for (int i=0;i<length;i++){
			query += " id=? "+logical;
		}
		query = query.substring(0, query.lastIndexOf(logical));
		return query;
	}
}
