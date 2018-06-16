package com.github.nbena.librarymanager.gui.librarianint;

import com.github.nbena.librarymanager.gui.LibrarianModel;

public class ActionNewNotReservedConsultation extends AbstractActionWithBookUser {

	public ActionNewNotReservedConsultation(LibrarianModel model){
		super(model);
		super.ask = true;
		super.confirmationMessage = "Confermi l'inizio della consultazione?";
		super.resultMessage = "Prestito confermato";		
	}
	
	@Override
	public void execute(){
		//super.model.
	}
	
	
}
