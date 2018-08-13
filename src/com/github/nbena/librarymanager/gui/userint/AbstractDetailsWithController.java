// TODO DEPRECATED THIS

package com.github.nbena.librarymanager.gui.userint;

import com.github.nbena.librarymanager.gui.UserController;

@Deprecated
public abstract class AbstractDetailsWithController extends AbstractDetails {
	
	protected UserController controller;
	
	public AbstractDetailsWithController(UserController controller){
		this.controller = controller;
	}

	
	

}
