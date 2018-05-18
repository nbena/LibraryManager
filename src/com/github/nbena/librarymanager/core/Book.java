/*  LibraryManager
    Copyright (C) 2018 nbena

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
    */

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



