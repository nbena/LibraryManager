package com.github.nbena.librarymanager.gui.view;

@SuppressWarnings("serial")
public class SearchableBookView extends BasicBookView {
	
	
	public String getTitle(){
		return super.textFieldTitle.getText();
	}
	
	public String[] getAuthors(){
		return super.textFieldAuthors.getText().split(";");
	}
	
	public String getTopic(){
		return super.textFieldTopic.getText();
	}
	
	public int year(){
		return Integer.parseInt(super.textFieldYear.getText());
	}

	
	public SearchableBookView(){
		super();
		super.textFieldTitle.setEditable(true);
		super.textFieldAuthors.setEditable(true);
		super.textFieldTopic.setEditable(true);
		super.textFieldYear.setEditable(true);
	}

}
