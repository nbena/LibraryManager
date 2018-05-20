package com.github.nbena.librarymanager.gui;

import com.github.nbena.librarymanager.core.User;
import com.github.nbena.librarymanager.man.LibraryManager;

public abstract class AbstractModel {
	
	protected LibraryManager manager;
	protected User user;
	
	public AbstractModel(LibraryManager manager){
		this.manager = manager;
	}
	
	protected void setUser(User user){
		this.user = user;
	}
	
	

}
