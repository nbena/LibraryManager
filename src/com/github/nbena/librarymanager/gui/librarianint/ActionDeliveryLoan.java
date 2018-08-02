package com.github.nbena.librarymanager.gui.librarianint;

import java.sql.SQLException;

import com.github.nbena.librarymanager.core.Loan;
import com.github.nbena.librarymanager.core.ReservationException;
import com.github.nbena.librarymanager.gui.LibrarianModel;

public class ActionDeliveryLoan extends AbstractAction implements Action {
	
	private Loan loan;
	
	public ActionDeliveryLoan(LibrarianModel model){
		super(model);
		super.ask = true;
		super.confirmationMessage = "Confermi consegna?";
		super.resultMessage = "Consegna confermata";
	}
	
	@Override
	public void execute() throws SQLException, ReservationException{
		super.model.deliveryLoan(this.loan);
	}
	
	@Override
	public void setArgs(Object... args){
		this.loan = (Loan) args[0];
	}

}
