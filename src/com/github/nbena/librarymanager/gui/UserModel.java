package com.github.nbena.librarymanager.gui;

import java.sql.SQLException;
import java.time.LocalDate;

import com.github.nbena.librarymanager.core.AbstractReservation;
import com.github.nbena.librarymanager.core.Book;
import com.github.nbena.librarymanager.core.ConsultationReservation;
import com.github.nbena.librarymanager.core.Copy;
import com.github.nbena.librarymanager.core.InternalUser;
import com.github.nbena.librarymanager.core.LoanReservation;
import com.github.nbena.librarymanager.core.ReservationException;
import com.github.nbena.librarymanager.core.SeatReservation;
import com.github.nbena.librarymanager.core.User;
import com.github.nbena.librarymanager.man.LibraryManager;

public class UserModel extends AbstractModel {

	public UserModel(LibraryManager manager) {
		super(manager);
	}
	
	
	public boolean authenticate(User user){
		boolean result = super.manager.authenticateUser(user.getHashedPassword());
		if (result){
			super.setUser(user);
		}
		return result;
	}
	
	public SeatReservation reserveSeat(LocalDate date) throws ReservationException, SQLException{
		return super.manager.tryReserveSeat((InternalUser) super.user, date);
	}
	
	public ConsultationReservation reserveConsultation(Book book, LocalDate date) throws ReservationException, SQLException{
		return super.manager.tryReserveConsultation((InternalUser) super.user, book, date);
	}
	
	public void cancelReservation(AbstractReservation reservation) throws SQLException{
		super.manager.cancelReservation(reservation);
	}
	
	public LoanReservation reserveLoan(Copy copy) throws SQLException, ReservationException{
		return super.manager.tryReserveLoan((InternalUser) super.user, copy);
	}


}
