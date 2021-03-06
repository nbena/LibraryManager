package com.github.nbena.librarymanager.gui.action;

import java.sql.SQLException;

import com.github.nbena.librarymanager.core.Loan;
import com.github.nbena.librarymanager.core.LibraryManagerException;
import com.github.nbena.librarymanager.gui.LibrarianModel;

public class ActionDeliveryLoan extends AbstractLibrarianAction implements Action {
	
	private Loan loan;
	
	public ActionDeliveryLoan(LibrarianModel model){
		super(model);
		super.ask = true;
		super.confirmationMessage = "Confermi consegna?";
		super.resultMessage = "Consegna confermata";
	}
	
	@Override
	public void execute() throws SQLException, LibraryManagerException{
		super.model.deliveryLoan(this.loan);
	}
	
	@Override
	public void setArgs(Object... args){
		this.loan = (Loan) args[0];
	}

}
