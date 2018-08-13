package com.github.nbena.librarymanager.gui.librarianint;

import java.sql.SQLException;

import com.github.nbena.librarymanager.core.ConsultationReservation;
import com.github.nbena.librarymanager.core.ReservationException;
import com.github.nbena.librarymanager.gui.LibrarianModel;

public class ActionNewReservedConsultation extends AbstractLibrarianAction {
	
	private ConsultationReservation reservation;
	
	public ActionNewReservedConsultation(LibrarianModel model){
		super(model);
		super.ask = true;
		super.confirmationMessage = "Confermi l'inizio della consultazione?";
		super.resultMessage = "Consultazione confermata";
	}
	
	
	@Override
	public void execute() throws SQLException, ReservationException{
		super.model.startReservedConsultation(this.reservation);
	}


	@Override
	public void setArgs(Object... args) {
		this.reservation = (ConsultationReservation) args[0];
	}

}
