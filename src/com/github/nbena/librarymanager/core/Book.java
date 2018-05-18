package com.github.nbena.librarymanager.core;

public class Book {
	
	private String title;
	private int ID;
	private String [] authors;
	private int yearsOfPublishing;
	private String mainTopic;
	public String publishingHouse;
	
	public Book(String title, String[] authors, int yearsOfPublishing, String mainTopic, String publishingHouse) {
		this.title = title;
		this.authors = authors;
		this.yearsOfPublishing = yearsOfPublishing;
		this.mainTopic = mainTopic;
		this.publishingHouse = publishingHouse;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String[] getAuthors() {
		return authors;
	}
	public void setAuthors(String[] authors) {
		this.authors = authors;
	}
	public int getYearsOfPublishing() {
		return yearsOfPublishing;
	}
	public void setYearsOfPublishing(int yearsOfPublishing) {
		this.yearsOfPublishing = yearsOfPublishing;
	}
	public String getMainTopic() {
		return mainTopic;
	}
	public void setMainTopic(String mainTopic) {
		this.mainTopic = mainTopic;
	}
	public String getPublishingHouse() {
		return publishingHouse;
	}
	public void setPublishingHouse(String publishingHouse) {
		this.publishingHouse = publishingHouse;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	

}



