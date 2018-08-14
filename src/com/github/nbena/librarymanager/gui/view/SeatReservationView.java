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

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.nbena.librarymanager.core.SeatReservation;

import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SeatReservationView extends JDialog implements DetailsViewable, ReservationView {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldTimestamp;
	private JTextField textFieldReservationDate;
	private JTextField textFieldSeatNumber;
	private JTextField textFieldTableNumber;
	private JButton btnCancelReservation;
	private JLabel lblMain;
	private JButton okButton;
	
	private void setSeatReservation(SeatReservation reservation){
		this.textFieldTimestamp.setText(reservation.getTimestamp().toString());
		this.textFieldReservationDate.setText(reservation.getReservationDate().toString());
		this.textFieldSeatNumber.setText(Integer.toString(reservation.getSeat().getNumber()));
		this.textFieldTableNumber.setText(Integer.toString(reservation.getSeat().getTableNumber()));
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SeatReservationView dialog = new SeatReservationView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SeatReservationView() {
		setBounds(100, 100, 356, 249);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 177, 370, 43);
		contentPanel.add(buttonPane);
		buttonPane.setLayout(null);
		
		okButton = new JButton("OK");
		okButton.setBounds(10, 12, 160, 24);
		buttonPane.add(okButton);
		
		btnCancelReservation = new JButton("Annulla prenotazione");
		btnCancelReservation.setBounds(180, 12, 160, 24);
		buttonPane.add(btnCancelReservation);
			
		lblMain = new JLabel("");
		lblMain.setBounds(12, 12, 416, 14);
		contentPanel.add(lblMain);
		
		JLabel lblTimestamp = new JLabel("Effettuata il");
		lblTimestamp.setBounds(12, 50, 103, 14);
		contentPanel.add(lblTimestamp);
		
		JLabel lblReservationDate = new JLabel("Per il giorno");
		lblReservationDate.setBounds(12, 76, 103, 14);
		contentPanel.add(lblReservationDate);
		
		JLabel lblSeatNumber = new JLabel("Posto");
		lblSeatNumber.setBounds(12, 102, 103, 14);
		contentPanel.add(lblSeatNumber);
		
		JLabel lblSeatTable = new JLabel("Tavolo");
		lblSeatTable.setBounds(12, 128, 103, 14);
		contentPanel.add(lblSeatTable);
		
		textFieldTimestamp = new JTextField();
		textFieldTimestamp.setBounds(152, 48, 113, 18);
		contentPanel.add(textFieldTimestamp);
		textFieldTimestamp.setColumns(10);
		
		textFieldReservationDate = new JTextField();
		textFieldReservationDate.setBounds(151, 74, 114, 18);
		contentPanel.add(textFieldReservationDate);
		textFieldReservationDate.setColumns(10);
		
		textFieldSeatNumber = new JTextField();
		textFieldSeatNumber.setBounds(152, 100, 113, 18);
		contentPanel.add(textFieldSeatNumber);
		textFieldSeatNumber.setColumns(10);
		
		textFieldTableNumber = new JTextField();
		textFieldTableNumber.setBounds(151, 126, 114, 18);
		contentPanel.add(textFieldTableNumber);
		textFieldTableNumber.setColumns(10);
		
		this.textFieldTimestamp.setEditable(false);
		this.textFieldReservationDate.setEditable(false);
		this.textFieldSeatNumber.setEditable(false);
		this.textFieldTableNumber.setEditable(false);
	}

	@Override
	public void setMainTitle(String main) {
		this.lblMain.setText(main);
	}

	public void addActionListenerCancelReservation(ActionListener listener) {
		this.btnCancelReservation.addActionListener(listener);
	}

	@Override
	public void addActionListenerOk(ActionListener listener) {
		this.okButton.addActionListener(listener);
	}

	@Override
	public void setItem(Object item) {
		this.setSeatReservation((SeatReservation) item);
	}
}
