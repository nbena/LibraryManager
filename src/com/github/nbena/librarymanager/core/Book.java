/*  LibraryManager a toy library manager
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

import java.util.Arrays;

public class Book implements IDble {
	
	protected String title;
	protected int ID;
	protected String [] authors;
	protected int yearOfPublishing;
	protected String mainTopic;
	protected String publishingHouse;
	
	public Book(String title, String[] authors, int yearOfPublishing, String mainTopic, String publishingHouse) {
		this.title = title;
		this.authors = authors;
		this.yearOfPublishing = yearOfPublishing;
		this.mainTopic = mainTopic;
		this.publishingHouse = publishingHouse;
	}
	
	public Book() {
	}

	public /*@ pure @*/ String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public /*@ pure @*/ String[] getAuthors() {
		return authors;
	}
	public void setAuthors(String[] authors) {
		this.authors = authors;
	}
	public /*@ pure @*/ int getYearOfPublishing() {
		return yearOfPublishing;
	}
	public void setYearOfPublishing(int yearOfPublishing) {
		this.yearOfPublishing = yearOfPublishing;
	}
	public /*@ pure @*/ String getMainTopic() {
		return mainTopic;
	}
	public void setMainTopic(String mainTopic) {
		this.mainTopic = mainTopic;
	}
	public /*@ pure @*/ String getPublishingHouse() {
		return publishingHouse;
	}
	public void setPublishingHouse(String publishingHouse) {
		this.publishingHouse = publishingHouse;
	}

	public /*@ pure @*/ int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
	public /*@ pure @*/ String toString(){
		String r = "Book, title: "+title+ ", authors "+
				Arrays.toString(authors);
			r += "ID: "+Integer.toString(ID);
		return r;
	}

}



