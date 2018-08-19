package com.github.nbena.librarymanager.gui.librarianint;

import java.sql.SQLException;

import com.github.nbena.librarymanager.core.LibraryManagerException;
import com.github.nbena.librarymanager.gui.LibrarianModel;

public class ActionDeleteCopies extends ActionChangeCopiesNumber {

	public ActionDeleteCopies(LibrarianModel model) {
		super(model);
	}
	
	// TODO jmlify this
	@Override
	public void execute() throws SQLException, LibraryManagerException {
		
		int changed = model.deleteCopies(this.book, this.difference);

		if(difference < previousNumber){
			super.resultMessage = String.format("Modifica confermata: eliminate %d copie", changed);
		}
		
	}
}
