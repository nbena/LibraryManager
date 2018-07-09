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

public class ActionDeleteBook extends AbstractAction {
	
	private Book book;

	public ActionDeleteBook(LibrarianModel model) {
		super(model);
		super.ask = true;
		super.confirmationMessage = "Confermi l'eliminazione di questo libro?";
		super.resultMessage = "Eliminazione confermata";
	}

	@Override
	public void setArgs(Object... args) {
		this.book = (Book) args[0];
	}

	@Override
	public void execute() throws SQLException, ReservationException {
		super.model.deleteBook(this.book);
	}

}