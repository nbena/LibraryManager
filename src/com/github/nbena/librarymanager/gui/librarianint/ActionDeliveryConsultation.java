package com.github.nbena.librarymanager.gui.librarianint;

import java.sql.SQLException;

import com.github.nbena.librarymanager.core.Consultation;
import com.github.nbena.librarymanager.gui.LibrarianModel;

public class ActionDeliveryConsultation extends AbstractAction implements Action {
	
	private /*@ spec_public @*/ Consultation consultation;
	
	public ActionDeliveryConsultation(LibrarianModel model){
		super(model);
		super.ask = true;
		super.confirmationMessage = "Confermi consegna?";
		super.resultMessage = "Consegna confermata";
	}
	
	@Override
	public void execute() throws SQLException{
		super.model.deliveryConsultation(this.consultation);
	}

	/*@
	 @ \requires args.length == 1 &&
	 @ consultation != null &&
	 @ args[0] instanceof Consultation;
	 @*/
	@Override
	public void setArgs(Object... args) {
		this.consultation = (Consultation) args[0];
	}

}
