package com.github.nbena.librarymanager.gui.librarianint;

import java.sql.SQLException;

import com.github.nbena.librarymanager.core.InternalUser;
import com.github.nbena.librarymanager.core.ReservationException;
import com.github.nbena.librarymanager.gui.LibrarianModel;

/**
 * This class is used for starting a new loan assuming it has been previously
 * reserved
 * @author nbena
 *
 */
public class ActionNewReservedLoan extends AbstractActionWithBookUser implements Action {

	public ActionNewReservedLoan(LibrarianModel model) {
		super(model);
		super.ask = true;
		super.confirmationMessage = "Confermi questo prestito?";
		super.resultMessage = "Prestito confermato";
	}
	
	@Override
	public void execute () throws SQLException, ReservationException{
		super.model.loanReserved((InternalUser) super.user, super.title,
				super.authors, super.year, super.topic);
	}


}
