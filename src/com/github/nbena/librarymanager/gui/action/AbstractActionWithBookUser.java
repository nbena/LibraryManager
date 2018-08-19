package com.github.nbena.librarymanager.gui.action;

import com.github.nbena.librarymanager.gui.LibrarianModel;

public abstract class AbstractActionWithBookUser extends AbstractActionWithUser {
	
	
	protected String title;
	protected String [] authors;
	protected int year;
	protected String topic;
	protected String phouse;
	
	protected AbstractActionWithBookUser(LibrarianModel model) {
		super(model);
	}
	
	/*@
	 @ requires args.length == 5;
	 @*/
	@Override
	public void setArgs(Object... args) {
		
		// this.user = (User) args[0];
		this.title = (String) args[0];
		this.authors = (String[]) args[1];
		this.year = (int) args[2];
		this.topic = (String) args[3];
		this.phouse  = (String) args[4];
		
	}

}
