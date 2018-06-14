package com.github.nbena.librarymanager.gui.view;

import java.awt.event.ActionListener;

import com.github.nbena.librarymanager.core.Book;

public interface ViewableBook {
	
	public void setBook(Book book);
	
	public void setVisible(boolean visible);
	
	public void addListenerOk(ActionListener listener);
	
	public void addListenerCancel(ActionListener listener);

}
