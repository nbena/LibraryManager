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

package com.github.nbena.librarymanager.gui.action;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.github.nbena.librarymanager.core.CopyForConsultation;
import com.github.nbena.librarymanager.core.LibraryManagerException;
import com.github.nbena.librarymanager.gui.LibrarianModel;

public class ActionGetAvailableCopiesForConsultation extends AbstractActionWithBookUser {

	List<CopyForConsultation>  result;
	
	public ActionGetAvailableCopiesForConsultation(LibrarianModel model) {
		super(model);
	}

	@Override
	public void execute() throws SQLException, LibraryManagerException {
		this.result = this.model.getAvailableCopiesForConsultation(
				LocalDate.now(),
				super.title,
				super.authors, super.year, super.topic, super.phouse);
	}
	
	@Override
	public List<CopyForConsultation> getResult(){
		return this.result;
	}
	

}
