package com.github.nbena.librarymanager.gui;

import com.github.nbena.librarymanager.gui.view.LibrarianView;

public class LibrarianController extends AbstractController {
	
	private LibrarianModel model;
	private LibrarianView view;
	
	
	public LibrarianController(LibrarianModel model, LibrarianView view) {
		this.model = model;
		this.view = view;
		
		this.view.setVisible(true);
	}
	
	
	
	

}
