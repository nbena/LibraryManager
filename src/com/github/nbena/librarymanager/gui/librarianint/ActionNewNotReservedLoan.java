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

import com.github.nbena.librarymanager.core.Copy;
import com.github.nbena.librarymanager.core.LibraryManagerException;
import com.github.nbena.librarymanager.gui.LibrarianModel;

public class ActionNewNotReservedLoan extends AbstractActionWithUser {
	
	private Copy copy;
	
	public ActionNewNotReservedLoan(LibrarianModel model) {
		super(model);
		super.ask = true;
		super.confirmationMessage = "Confermi questo prestito?";
		super.resultMessage = "Prestito confermato";
	}

	@Override
	public void setArgs(Object... args) {
		this.copy = (Copy) args[0];
	}

	@Override
	public void execute() throws SQLException, LibraryManagerException {
		super.model.loanNotReserved(super.user, this.copy);

	}

}
