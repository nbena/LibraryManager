package com.github.nbena.librarymanager.gui.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.github.nbena.librarymanager.core.AbstractReservationWithBook;

@SuppressWarnings("serial")
public class AbstractReservationWithBookView extends BasicBookView
	implements MainableView, ReservationView, VisibleView {
	
	protected JButton btnCancelReservation;
	protected JTextField textFieldTimestamp;
	
	public void setAbstractReservation(AbstractReservationWithBook reservation){
		super.setBook(reservation.getCopy());
		this.textFieldTimestamp.setText(reservation.getTimestamp().toString());
	}
	
	@Override
	public void addActionListenerCancelReservation(ActionListener listener) {
		this.btnCancelReservation.addActionListener(listener);
	}
	
	
	public AbstractReservationWithBookView(){
		super();
		
		JLabel lblTimestamp = new JLabel("Effettuata il ");
		lblTimestamp.setBounds(12, 175, 97, 14);
		super.contentPanel.add(lblTimestamp);
		
		this.textFieldTimestamp = new JTextField();
		this.textFieldTimestamp.setBounds(136, 175, 97, 18);
		super.contentPanel.add(textFieldTimestamp);
		this.textFieldTimestamp.setColumns(10);
		
		this.textFieldTimestamp.setEditable(false);
		
		this.btnCancelReservation = new JButton("Annulla prenotazione");
		this.btnCancelReservation.setBounds(226, 12, 73, 24);
		super.buttonPane.add(this.btnCancelReservation);
		
		super.setBounds(100, 100, 450, 301);
		
		super.addActionListenerOk(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		
//		super.addActionListenerCancel(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				setVisible(false);
//				dispose();
//			}
//			
//		});		
	}	

}
