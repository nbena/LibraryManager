package com.github.nbena.librarymanager.gui.librarianint;

import com.github.nbena.librarymanager.core.User;
import com.github.nbena.librarymanager.gui.LibrarianModel;

public abstract class AbstractActionWithBookUser extends AbstractAction {
	
	protected User user;
	protected String title;
	protected String [] authors;
	protected int year;
	protected String topic;
	
	protected AbstractActionWithBookUser(LibrarianModel model) {
		super(model);
	}
	
	@Override
	public void setArgs(Object... args) {
		
		this.user = (User) args[0];
		this.title = (String) args[1];
		this.authors = (String[]) args[2];
		this.year = (int) args[3];
		this.topic = (String) args[4];
		
	}

}
