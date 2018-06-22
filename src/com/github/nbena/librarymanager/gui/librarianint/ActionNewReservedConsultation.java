package com.github.nbena.librarymanager.gui.librarianint;

import java.sql.SQLException;

import com.github.nbena.librarymanager.core.InternalUser;
import com.github.nbena.librarymanager.core.ReservationException;
import com.github.nbena.librarymanager.gui.LibrarianModel;

public class ActionNewReservedConsultation extends AbstractActionWithBookUser {
	
	public ActionNewReservedConsultation(LibrarianModel model){
		super(model);
		super.ask = true;
		super.confirmationMessage = "Confermi l'inizio della consultazione?";
		super.resultMessage = "Confermato";
	}
	
	
	@Override
	public void execute() throws SQLException, ReservationException{
		super.model.startReservedConsultation((InternalUser) super.user, super.title,
				super.authors, super.year, super.topic, super.phouse);
	}

}
