package com.github.nbena.librarymanager.gui.librarianint;

import java.sql.SQLException;

import com.github.nbena.librarymanager.core.LibraryManagerException;
import com.github.nbena.librarymanager.gui.LibrarianModel;

public class ActionAddCopies extends ActionChangeCopiesNumber {
	
	private boolean forConsultation;

	public ActionAddCopies(LibrarianModel model) {
		super(model);
		
	}

	@Override
	public void setArgs(Object... args) {
		super.setArgs(args);
		this.forConsultation = (boolean) args[3];
	}

	@Override
	public void execute() throws SQLException, LibraryManagerException {
		model.addCopies(this.book, this.difference, this.forConsultation);	
	}

}
