package com.github.nbena.librarymanager.gui.librarianint;

import java.sql.SQLException;

import com.github.nbena.librarymanager.core.ReservationException;
import com.github.nbena.librarymanager.gui.LibrarianModel;

public class ActionDeliveryLoan extends AbstractActionWithBookUser implements Action {
	
	public ActionDeliveryLoan(LibrarianModel model){
		super(model);
		super.ask = true;
		super.confirmationMessage = "Confermi consegna?";
		super.resultMessage = "Consegna confermata";
	}
	
	@Override
	public void execute() throws SQLException, ReservationException{
		super.model.deliveryLoan(super.user, super.title,
				super.authors, super.year, super.topic, super.phouse);
	}

}
