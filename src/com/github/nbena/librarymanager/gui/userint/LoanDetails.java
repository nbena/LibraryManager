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


package com.github.nbena.librarymanager.gui.userint;

import com.github.nbena.librarymanager.gui.UserController;
import com.github.nbena.librarymanager.gui.view.LoanView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.github.nbena.librarymanager.core.Loan;

public class LoanDetails extends AbstractDetailsWithController implements Details {
	
	private LoanView view;

	public LoanDetails(UserController controller) {
		super(controller);
		
		this.view = new LoanView();
		
		this.view.addActionListenerRenew(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Loan l = controller.renewLoan((Loan) item);
				if (l!=null){
					item = l;
					view.setLoan((Loan) item);
				}
			}
			
		});
	}

	@Override
	public void show() {
		this.view.setVisible(true);
		this.view.setLoan((Loan) super.item);
	}
	

}
