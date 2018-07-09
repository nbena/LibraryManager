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

package com.github.nbena.librarymanager.gui.librarianint;

import java.sql.SQLException;

import com.github.nbena.librarymanager.core.Book;
import com.github.nbena.librarymanager.core.ReservationException;
import com.github.nbena.librarymanager.gui.LibrarianModel;

public class ActionChangeCopiesNumber extends AbstractAction {
	
	private Book book;
	private int previousNumber;
	private int newNumber;
	private boolean forConsultation;

	public ActionChangeCopiesNumber(LibrarianModel model) {
		super(model);
		super.ask = true;
		super.confirmationMessage = "Confermi la modifica del numero di copie? Ricordati che "+
				"se vuoi diminuirne il numero, può darsi che la riduzione sarà minore rispetto "+
				"a quella desiderata";
		super.resultMessage = "Modifica confermata";
	}

	@Override
	public void setArgs(Object... args) {
		this.book = (Book) args[0];
		this.previousNumber = (int) args[1];
		this.newNumber = (int) args[2];
		this.forConsultation = (boolean) args[3];
	}

	// TODO jmlify this
	@Override
	public void execute() throws SQLException, ReservationException {
		
		if(newNumber == previousNumber){
			throw new ReservationException("Il nuovo numero è uguale al primo");
		}else if(newNumber < previousNumber){
			model.deleteCopies(this.book, this.newNumber);
		}else{
			model.addCopies(this.book, this.newNumber, this.forConsultation);	
		}
		
	}

}
