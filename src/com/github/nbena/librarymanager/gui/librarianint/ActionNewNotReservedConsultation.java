package com.github.nbena.librarymanager.gui.librarianint;

import java.sql.SQLException;

import com.github.nbena.librarymanager.core.ReservationException;
import com.github.nbena.librarymanager.gui.LibrarianModel;

public class ActionNewNotReservedConsultation extends AbstractActionWithBookUser {

	public ActionNewNotReservedConsultation(LibrarianModel model){
		super(model);
		super.ask = true;
		super.confirmationMessage = "Confermi l'inizio della consultazione?";
		super.resultMessage = "Consultazione confermata";		
	}
	
	@Override
	public void execute() throws SQLException, ReservationException{
		super.model.startNotReservedConsultation(super.user, super.title,
				super.authors, super.year, super.topic, super.phouse);
	}
	
	
}
