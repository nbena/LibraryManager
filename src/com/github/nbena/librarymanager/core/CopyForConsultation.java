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

public class CopyForConsultation extends Copy {

	private boolean inConsultation;
	
	public CopyForConsultation(String title, String[] authors, int yearsOfPublishing, String mainTopic, String ID) {
		super(title, authors, yearsOfPublishing, mainTopic, ID);
		this.inConsultation = false;
	}

	public boolean isInConsultation() {
		return inConsultation;
	}

	public void setInConsultation(boolean inConsultation) {
		this.inConsultation = inConsultation;
	}
	
	public static CopyForConsultation create(Copy c){
		CopyForConsultation copy = new CopyForConsultation(
				c.getTitle(),
				c.getAuthors(),
				c.getYearOfPublishing(),
				c.getMainTopic(),
				c.getPublishingHouse()
				);
		return copy;
	}

}
