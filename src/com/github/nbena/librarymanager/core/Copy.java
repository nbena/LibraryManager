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
