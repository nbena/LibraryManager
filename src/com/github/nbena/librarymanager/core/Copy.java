package com.github.nbena.librarymanager.core;

public class Copy extends Book {

	
	private int ID;
	private boolean reserved;
	

	public Copy(String title, String[] authors, int yearsOfPublishing, String mainTopic, String publishingHouse) {
		super(title, authors, yearsOfPublishing, mainTopic, publishingHouse);
		
		reserved = false;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}
	
	public String getState(){
		return null;
	}
	
	public void setState(String state){
		
	}
	
	

}
