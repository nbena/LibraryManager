package com.github.nbena.librarymanager.gui.librarianint;

import com.github.nbena.librarymanager.gui.LibrarianModel;

public abstract class AbstractAction implements Action {
	
	protected boolean ask;
	protected String confirmationMessage;
	protected String resultMessage;
	
	protected LibrarianModel model;
	// protected Function<Object, Object> function;
	// protected Object [] args;

	@Override
	public boolean askConfirmation() {
		return this.ask;
	}
	
	@Override
	public String getConfirmationMessage(){
		return this.confirmationMessage;
	}
	
	@Override
	public String getResultMessage(){
		return this.resultMessage;
	}
	
	

//	@Override
//	public abstract void setArgs(Object... args);

//	@Override
//	public void execute() throws SQLException {
//		this.function.apply(this.args);
//	}
	
	

}
