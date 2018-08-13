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

import com.github.nbena.librarymanager.core.Loan;
import com.github.nbena.librarymanager.core.ReservationException;
import com.github.nbena.librarymanager.gui.UserModel;

public class ActionRenewLoan extends AbstractUserAction {
	
	private Loan loan;

	public ActionRenewLoan(UserModel model) {
		super(model);
		
		super.ask = true;
		super.confirmationMessage = "Sei sicuro di voler rinnovare?";
		super.resultMessage = "Rinnovo confermato";
	}

	@Override
	public void setArgs(Object... args) {
		this.loan = (Loan) args[0];
	}

	@Override
	public void execute() throws SQLException, ReservationException {
		super.model.renewLoan(loan);
	}

}
