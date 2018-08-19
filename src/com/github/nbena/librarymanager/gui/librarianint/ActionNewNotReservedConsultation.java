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

import com.github.nbena.librarymanager.core.CopyForConsultation;
import com.github.nbena.librarymanager.core.LibraryManagerException;
import com.github.nbena.librarymanager.core.Seat;
import com.github.nbena.librarymanager.gui.LibrarianModel;

public class ActionNewNotReservedConsultation extends AbstractActionWithUser {
	
	private CopyForConsultation copy;
	private Seat seat;

	public ActionNewNotReservedConsultation(LibrarianModel model) {
		super(model);
		super.ask = true;
		super.confirmationMessage = "Confermi l'inizio della consultazione?";
		super.resultMessage = "Consultazione confermata";	
	}

	@Override
	public void setArgs(Object... args) {
		this.copy = (CopyForConsultation) args[0];
	}

	@Override
	public void execute() throws SQLException, LibraryManagerException {
		this.seat = super.model.startNotReservedConsultation(super.user, this.copy);
	}
	
	@Override
	public Seat getResult(){
		return this.seat;
	}

}
