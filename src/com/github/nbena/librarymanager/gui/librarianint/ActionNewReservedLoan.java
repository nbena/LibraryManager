package com.github.nbena.librarymanager.gui.librarianint;

import java.sql.SQLException;

import com.github.nbena.librarymanager.core.LoanReservation;
import com.github.nbena.librarymanager.core.ReservationException;
import com.github.nbena.librarymanager.gui.LibrarianModel;

/**
 * This class is used for starting a new loan assuming it has been previously
 * reserved
 * @author nbena
 *
 */

public class ActionNewReservedLoan extends AbstractAction implements Action{
	
	private LoanReservation reservation;
	
	public ActionNewReservedLoan(LibrarianModel model){
		super(model);
		super.ask = true;
		super.confirmationMessage = "Confermi l'inizio del prestitio?";
		super.resultMessage = "Prestito confermato";
	}
	
	@Override
	public void execute() throws SQLException, ReservationException{
		super.model.loanReserved(this.reservation);
	}
	
	@Override
	public void setArgs(Object... args){
		this.reservation = (LoanReservation) args[0];
	}

}
