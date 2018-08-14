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

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.github.nbena.librarymanager.core.ConsultationReservation;

@SuppressWarnings("serial")
public class ConsultationReservationView extends AbstractReservationWithBookView {

	protected JTextField textFieldReservationDate;
	protected JTextField textFieldSeatNumber;
	protected JTextField textFieldTableNumber;
	
	
	private void setConsultationReservation(ConsultationReservation reservation){
		super.setAbstractReservation(reservation);
		
		this.textFieldReservationDate.setText(reservation.getReservationDate().toString());
		this.textFieldSeatNumber.setText(Integer.toString(reservation.getSeat().getNumber()));
		this.textFieldTableNumber.setText(Integer.toString(reservation.getSeat().getTableNumber()));
	}
	
	public static void main(String []  args){
		ConsultationReservationView view = new ConsultationReservationView();
		view.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		view.setVisible(true);
	}
	
	public ConsultationReservationView(){
		super();
		
		JLabel lblReservationDate = new JLabel("Per il giorno");
		lblReservationDate.setBounds(12, 196, 97, 14);
		super.contentPanel.add(lblReservationDate);
		
		JLabel lblSeatNumber = new JLabel("Posto");
		lblSeatNumber.setBounds(12, 221, 97, 14);
		super.contentPanel.add(lblSeatNumber);
		
		JLabel lblTableNumber = new JLabel("Tavolo");
		lblTableNumber.setBounds(12, 246, 97, 14);
		super.contentPanel.add(lblTableNumber);		
		
		this.textFieldReservationDate = new JTextField();
		this.textFieldReservationDate.setBounds(136, 196, 97, 18);
		this.textFieldReservationDate.setColumns(10);
		
		this.textFieldSeatNumber = new JTextField();
		this.textFieldSeatNumber.setBounds(136, 221, 97, 18);
		this.textFieldSeatNumber.setColumns(10);
		
		this.textFieldTableNumber = new JTextField();
		this.textFieldTableNumber.setBounds(136, 246, 97, 18);
		this.textFieldTableNumber.setColumns(10);	
		
		super.contentPanel.add(this.textFieldReservationDate);
		super.contentPanel.add(this.textFieldSeatNumber);
		super.contentPanel.add(this.textFieldTableNumber);
		
		this.textFieldReservationDate.setEditable(false);
		this.textFieldSeatNumber.setEditable(false);
		this.textFieldTableNumber.setEditable(false);
		
		super.setBounds(100, 100, 450, 351);
		
		super.buttonPane.setBounds(0, 280, 450, 45);
		
//		super.btnOk.setBounds(156, 275, 97, 18);
//		super.btnCancelReservation.setBounds(186, 275, 97, 18);
	}

	@Override
	public void setItem(Object item) {
		this.setConsultationReservation((ConsultationReservation) item);
	}


}
