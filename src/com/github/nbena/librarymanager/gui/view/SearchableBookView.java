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
	
	public int getYear(){
		String year = super.textFieldYear.getText();
		int returnedYear = 0;
		if (!year.equals("")){
			returnedYear = Integer.parseInt(year);
		}
		return returnedYear;
	}
	
	public String getPhouse(){
		return super.textFieldPhouse.getText();
	}

	
	public SearchableBookView(){
		super();
		super.textFieldTitle.setEditable(true);
		super.textFieldAuthors.setEditable(true);
		super.textFieldTopic.setEditable(true);
		super.textFieldYear.setEditable(true);
		super.textFieldPhouse.setEditable(true);
		
		super.textFieldAuthors.setToolTipText("Separa gli autori con ';'");
	}

}
