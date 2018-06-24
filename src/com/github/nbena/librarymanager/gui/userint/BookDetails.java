package com.github.nbena.librarymanager.gui.userint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.github.nbena.librarymanager.core.Book;
import com.github.nbena.librarymanager.core.Copy;
import com.github.nbena.librarymanager.gui.UserController;
import com.github.nbena.librarymanager.gui.view.BookReserveView;

public class BookDetails extends AbstractDetailsWithController implements Details {

	protected BookReserveView view;
	
	public BookDetails(UserController controller) {
		super(controller);
		
		this.view = new BookReserveView();
		this.view.addActionListenerReserveButton(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.reserve((Copy) item);
			}
		});
	}

	@Override
	public void show() {
		this.view.setVisible(true);
		this.view.setBook((Book) super.item);
	}

}
