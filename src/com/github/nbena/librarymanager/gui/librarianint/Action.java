package com.github.nbena.librarymanager.gui.librarianint;

import java.sql.SQLException;

import com.github.nbena.librarymanager.core.ReservationException;

public interface Action {
	
	public boolean askConfirmation();
	
	public void setArgs(Object... args);
	
	public void execute() throws SQLException, ReservationException;
	
	public String getConfirmationMessage();
	
	public String getResultMessage();

}
