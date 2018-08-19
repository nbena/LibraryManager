package com.github.nbena.librarymanager.gui.librarianint;

import java.sql.SQLException;

import com.github.nbena.librarymanager.core.LibraryManagerException;

public interface Action {
	
	public boolean askConfirmation();
	
	public void setArgs(Object... args);
	
	public void execute() throws SQLException, LibraryManagerException;
	
	public String getConfirmationMessage();
	
	public String getResultMessage();
	
	public Object getResult();

}
