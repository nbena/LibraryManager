package com.github.nbena.librarymanager.gui.view;

public interface SearchableBook extends ViewableBook, ResettableView, MainableView {
	
	public String getTitle();
	
	public String[] getAuthors();
	
	public String getTopic();
	
	public int getYear();
	
	public String getPhouse();

}
