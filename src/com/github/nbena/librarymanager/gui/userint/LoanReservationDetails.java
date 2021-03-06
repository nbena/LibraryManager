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

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.github.nbena.librarymanager.gui.AbstractController;
import com.github.nbena.librarymanager.gui.action.ActionCancelReservation;
import com.github.nbena.librarymanager.gui.view.AbstractReservationWithBookView;
import com.github.nbena.librarymanager.gui.view.DetailsViewable;
import com.github.nbena.librarymanager.gui.view.LoanReservationView;

public class LoanReservationDetails extends AbstractDetailsController implements DetailsController {
	

	public LoanReservationDetails(ActionCancelReservation action) {
		super(action);
		
		this.view = (DetailsViewable) new LoanReservationView();
		((AbstractReservationWithBookView) this.view).addActionListenerCancelReservation(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				
				boolean [] res = AbstractController.askConfirmationAndExecuteAction(
						action, (Component) view, new Object[]{});
				if (res[0]){
					((Component) view).setVisible(false);
					((Window) view).dispose();
				}
			}
		});
		
		this.view.addActionListenerOk(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				((Component) view).setVisible(false);
				((Window) view).dispose();
			}
			
		});
	}

}
