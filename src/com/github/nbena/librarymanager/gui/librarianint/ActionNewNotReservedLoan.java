package com.github.nbena.librarymanager.gui.librarianint;

import java.sql.SQLException;

import com.github.nbena.librarymanager.core.ReservationException;
import com.github.nbena.librarymanager.gui.LibrarianModel;

@Deprecated
public class ActionNewNotReservedLoan extends AbstractActionWithBookUser implements Action {

	public ActionNewNotReservedLoan(LibrarianModel model){
		super(model);
		super.ask = true;
		super.confirmationMessage = "Confermi questo prestito?";
		super.resultMessage = "Prestito confermato";
		
	}
	

	@Override
	public void execute() throws SQLException, ReservationException {
		super.model.loanNotReserved(super.user, super.title,
				super.authors, super.year, super.topic, super.phouse);
	}
	
	

}
