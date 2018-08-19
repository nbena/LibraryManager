package com.github.nbena.librarymanager.gui.action;

import java.sql.SQLException;

import com.github.nbena.librarymanager.core.Loan;
import com.github.nbena.librarymanager.core.LibraryManagerException;
import com.github.nbena.librarymanager.gui.LibrarianModel;

public class ActionSendMail extends AbstractLibrarianAction {
	
	private Loan loan;
	
	public ActionSendMail(LibrarianModel model) {
		super(model);
		super.ask = true;
		super.confirmationMessage = "Confermi di voler inviare un sollecito?";
		super.resultMessage = "Sollecito inviato";
	}


	@Override
	public void setArgs(Object... args) {
		this.loan = (Loan) args[0];
	}

	@Override
	public void execute() throws SQLException, LibraryManagerException {
		super.model.sendMailFor(this.loan);
	}

}
