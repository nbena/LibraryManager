package com.github.nbena.librarymanager.gui.userint;

import com.github.nbena.librarymanager.gui.AbstractController;

public abstract class AbstractDetailsWithController extends AbstractDetails {
	
	protected AbstractController controller;
	
	public AbstractDetailsWithController(AbstractController controller){
		this.controller = controller;
	}

}
