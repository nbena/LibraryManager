package com.github.nbena.librarymanager.gui.view.turnstile;

import com.github.nbena.librarymanager.man.LibraryManager;

public class TurnstileViewController {
	
	private LibraryManager manager;
	private TurnstileView view;
	
	public TurnstileViewController(LibraryManager manager, TurnstileView view){
		this.manager = manager;
		this.view = view;
		
		this.view.setMainTitle("Tornello biblioteca");
		this.view.setVisible(true);
	}

}
