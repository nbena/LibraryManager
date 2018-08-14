package com.github.nbena.librarymanager.gui.view;

import java.awt.Rectangle;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.github.nbena.librarymanager.core.AbstractReservationWithBook;

/**
 * Base class inherited by LoanReservationView and ConsultationReservationView
 * @author nicola
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractReservationWithBookView extends BasicBookView
	implements MainableView, ReservationView, VisibleView, DetailsViewable {
	
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
	
	@Override
	public void setMainTitle(String main){
		this.lblMain.setText(main);
	}
	
	
	@Override
	public void addActionListenerOk(ActionListener listener){
		this.btnOk.addActionListener(listener);
	}
	
	public AbstractReservationWithBookView(){
		super();
		
		JLabel lblTimestamp = new JLabel("Effettuata il ");
		lblTimestamp.setBounds(12, 171, 97, 14);
		super.contentPanel.add(lblTimestamp);
		
		this.textFieldTimestamp = new JTextField();
		this.textFieldTimestamp.setBounds(136, 171, 200, 18);
		super.contentPanel.add(textFieldTimestamp);
		this.textFieldTimestamp.setColumns(10);
		
		this.textFieldTimestamp.setEditable(false);
		
		this.btnCancelReservation = new JButton("Annulla prenotazione");
		this.btnCancelReservation.setBounds(230, 12, 160, 24);
		super.buttonPane.add(this.btnCancelReservation);
		
		super.setBounds(100, 100, 450, 301);
		
		Rectangle oldBounds = super.btnOk.getBounds();
		
		super.btnOk.setBounds(65, oldBounds.y,
			160/*oldBounds.width*/, oldBounds.height);
	
	}


}
