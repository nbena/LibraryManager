package com.github.nbena.librarymanager.gui.view;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.github.nbena.librarymanager.core.ConsultationReservation;

@SuppressWarnings("serial")
public class ConsultationReservationView extends AbstractReservationWithBookView {

	protected JTextField textFieldReservationDate;
	protected JTextField textFieldSeatNumber;
	protected JTextField textFieldTableNumber;
	
	@Override
	public void reset(){
		super.reset();
		this.textFieldReservationDate.setText("");
	}
	
	public void setConsultationReservation(ConsultationReservation reservation){
		super.setAbstractReservation(reservation);
		
		this.textFieldReservationDate.setText(reservation.getReservationDate().toString());
		this.textFieldSeatNumber.setText(Integer.toString(reservation.getSeat().getNumber()));
		this.textFieldTableNumber.setText(Integer.toString(reservation.getSeat().getTableNumber()));
	}
	
	public ConsultationReservationView(){
		super();
		
		JLabel lblReservationDate = new JLabel("Per il giorno");
		lblReservationDate.setBounds(12, 175, 97, 14);
		super.contentPanel.add(lblReservationDate);
		
		JLabel lblSeatNumber = new JLabel("Posto");
		lblSeatNumber.setBounds(12, 195, 97, 14);
		super.contentPanel.add(lblSeatNumber);
		
		JLabel lblTableNumber = new JLabel("Posto");
		lblTableNumber.setBounds(12, 195, 97, 14);
		super.contentPanel.add(lblTableNumber);		
		
		this.textFieldReservationDate = new JTextField();
		this.textFieldReservationDate.setBounds(136, 175, 97, 18);
		this.textFieldReservationDate.setColumns(10);
		
		this.textFieldSeatNumber = new JTextField();
		this.textFieldSeatNumber.setBounds(136, 195, 97, 18);
		this.textFieldSeatNumber.setColumns(10);
		
		this.textFieldTableNumber = new JTextField();
		this.textFieldTableNumber.setBounds(136, 215, 97, 18);
		this.textFieldTableNumber.setColumns(10);	
		
		super.contentPanel.add(this.textFieldReservationDate);
		super.contentPanel.add(this.textFieldSeatNumber);
		super.contentPanel.add(this.textFieldTableNumber);
		
		this.textFieldReservationDate.setEditable(false);
		this.textFieldSeatNumber.setEditable(false);
		this.textFieldTableNumber.setEditable(false);
		
		super.setBounds(100, 100, 450, 331);
	}
}
