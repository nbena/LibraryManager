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
import java.time.LocalDate;

import com.github.nbena.librarymanager.core.ConsultationReservation;
import com.github.nbena.librarymanager.core.Copy;
import com.github.nbena.librarymanager.core.CopyForConsultation;
import com.github.nbena.librarymanager.core.LibraryManagerException;
import com.github.nbena.librarymanager.gui.UserModel;

public class ActionReserve extends AbstractUserAction {
	
	private Copy copy;
	private LocalDate date;

	public ActionReserve(UserModel model) {
		super(model);
		
		super.ask = false;
		super.confirmationMessage = "";
		super.resultMessage = "Prenotazione confermata";
	}

	@Override
	public void setArgs(Object... args) {
		if(args.length == 1){
			this.copy = (Copy)args[0];
		}else if(args.length == 2){
			this.copy = (CopyForConsultation) args[0];
			this.date = (LocalDate) args[1];
		}
	}

	@Override
	public void execute() throws SQLException, LibraryManagerException {	
		if(this.date == null){
			super.model.reserveLoan(this.copy);
		}else{
			ConsultationReservation reservation =
					super.model.reserveConsultation((CopyForConsultation)
							this.copy, this.date);
			this.confirmationMessage = String.format("%s%d:%s", "Prenotazione effettuata con successo, " +
					"il tavolo:posto per te è ",
					reservation.getSeat().getTableNumber(),
					reservation.getSeat().getNumber());
		}
	}

}
