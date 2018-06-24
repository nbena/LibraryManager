package com.github.nbena.librarymanager.gui.userint;

import com.github.nbena.librarymanager.gui.UserModel;

public abstract class AbstractDetailsWithModel extends AbstractDetails {
	
	protected UserModel model;
	
	public AbstractDetailsWithModel(UserModel model){
		this.model = model;
	}


}
