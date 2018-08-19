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

import com.github.nbena.librarymanager.gui.AbstractController;
import com.github.nbena.librarymanager.gui.action.ActionRenewLoan;
import com.github.nbena.librarymanager.gui.view.LoanView;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoanDetails extends AbstractDetailsController {
	
	public LoanDetails(ActionRenewLoan action){
		super(action);
		
		super.view = new LoanView();
		
		super.view.addActionListenerOk(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				((Dialog) view).setVisible(false);
				((Window) view).dispose();
				
			}
			
		});
		
		((LoanView) super.view).addActionListenerRenew(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				AbstractController.askConfirmationAndExecuteAction(action,
						(Component) view, new Object[]{});
				
				((Dialog) view).setVisible(false);
				((Window) view).dispose();
			}
			
		});
	}
	
	public void setRenewEnabled(boolean enabled){
		((LoanView) this.view).setRenewEnabled(enabled);
	}
	

//	@Override
//	public void show() {
//		this.view.setVisible(true);
//		this.view.setLoan((Loan) super.item);
//	}
	

}
