package com.github.nbena.librarymanager.gui.userint;

import com.github.nbena.librarymanager.core.Book;
import com.github.nbena.librarymanager.gui.view.BasicBookView;

public class BookDetails extends AbstractDetails implements Details {

	@Override
	public void show() {
		BasicBookView view = new BasicBookView();
		view.setBook((Book) super.item);
		view.setVisible(true);
	}

}
