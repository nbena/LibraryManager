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

package com.github.nbena.librarymanager.gui.view;


import com.github.nbena.librarymanager.core.LoanReservation;

@SuppressWarnings("serial")
public class LoanReservationView extends AbstractReservationWithBookView implements DetailsViewable {


	private void setLoanReservation(LoanReservation reservation){
		super.setAbstractReservation(reservation);
		
	}

	@Override
	public void setItem(Object item) {
		this.setLoanReservation((LoanReservation) item);
	}
	


//	private JTextField textFieldTimestamp;
//	private JButton btnCancelReservation;
//	
//	public void addActionListenerCancelReservation(ActionListener listener){
//		this.btnCancelReservation.addActionListener(listener);
//	}
//	
//	@Override
//	public void setItem(Object item) {
//		// TODO Auto-generated method stub
//
//	}
//	
//	public LoanReservationView(){
//		
//		JLabel lblTimestamp = new JLabel("Prenotato il");
//		lblTimestamp.setBounds(12, 175, 97, 14);
//		super.contentPanel.add(lblTimestamp);
//		
//		textFieldTimestamp = new JTextField();
//		this.textFieldTimestamp.setBounds(136, 175, 97, 14);
//		super.contentPanel.add(this.textFieldTimestamp);
//		
//		this.btnCancelReservation = new JButton("Cancella prenotazione");
//	}

}
