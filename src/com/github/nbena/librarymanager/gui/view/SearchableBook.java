package com.github.nbena.librarymanager.gui.view;

public interface SearchableBook extends ViewableBook, ResettableView {
	
	public String getTitle();
	
	public String[] getAuthors();
	
	public String getTopic();
	
	public int getYear();

}
