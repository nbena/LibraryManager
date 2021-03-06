package com.github.nbena.librarymanager.gui.action;

import java.sql.SQLException;

import com.github.nbena.librarymanager.core.LibraryManagerException;
import com.github.nbena.librarymanager.core.User;
import com.github.nbena.librarymanager.gui.LibrarianModel;

public class ActionAddUser extends AbstractLibrarianAction {
	
	private User user;
	
	public ActionAddUser(LibrarianModel model) {
		super(model);
		super.ask = true;
		super.confirmationMessage = "Confermi l'aggiunta dell'utente?";
		super.resultMessage = "Aggiunta completata";
	}

	@Override
	public void setArgs(Object... args) {
		this.user = (User) args[0];
	}

	@Override
	public void execute() throws SQLException, LibraryManagerException {
		super.model.addUser(this.user);
	}

}
