package com.github.nbena.librarymanager.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Month;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import com.github.nbena.librarymanager.core.Seat;
import com.github.nbena.librarymanager.core.SeatReservation;
import com.github.nbena.librarymanager.gui.view.DatePickerView;
import com.github.nbena.librarymanager.gui.view.GenericTableView;
import com.github.nbena.librarymanager.gui.view.SeatDateView;

public abstract class AbstractController {
	
	protected final String ASK_DATE_FOR_SEAT = "Scegli la data";
	protected final String SHOW_SEAT_RESERVATION = "Dettagli prenotazione:";
	
	protected LocalDate gotDate;
	
	protected void displayMessage(Component parent, String message){
		JOptionPane.showMessageDialog(parent, message);
	}
	
	protected void displayError(Component parent, Exception exception){
		exception.printStackTrace();
		JOptionPane.showMessageDialog(parent, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	protected void showSeatReservationDetails(SeatReservation reservation){
		SeatDateView seatDateView = new SeatDateView(
				this.SHOW_SEAT_RESERVATION,
				reservation.getReservationDate(),
				reservation.getSeat()
				);
		
		seatDateView.addActionListenerOkButton(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				seatDateView.setVisible(false);
				seatDateView.dispose();
			}
			
		});
		
		seatDateView.addActionListenerCancelButton(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				seatDateView.setVisible(false);
				seatDateView.dispose();
			}
			
		});
	}
	
	protected LocalDate askOrShowDateForReservation(LocalDate date, Seat seat){
		SeatDateView seatDateView;
		this.gotDate = null;
//		if(reservation != null){
//			
//		}else{
//			
//		}
		seatDateView = new SeatDateView(this.ASK_DATE_FOR_SEAT, date, seat);
		seatDateView.addActionListenerOkButton(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int day = seatDateView.getDay();
				int month = seatDateView.getMonth();
				int year = seatDateView.getYear();
				gotDate = LocalDate.of(year, Month.of(month), day);
				
				seatDateView.setVisible(false);
				seatDateView.dispose();
				
			}
			
		});
		
		seatDateView.addActionListenerCancelButton(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				seatDateView.setVisible(false);
				seatDateView.dispose();
			}
			
		});
		seatDateView.setAlwaysOnTop(true);
		seatDateView.setVisible(true);
		return gotDate;
	}
	
	protected GenericTableView displayTableItems(AbstractTableModel tableModel, Component component){
		
		GenericTableView tableView = null;
		if(tableModel.getRowCount()>0){
			tableView = new GenericTableView();
			tableView.setTableModel(tableModel);
			tableView.setAlwaysOnTop(true);
			tableView.setVisible(true);		
		}else{
			this.displayMessage(component, "No items to show");
		}
		
		return tableView;
	}
	
	
	protected LocalDate datePicker(Component parent, String message, boolean editable){
		LocalDate result = null;
		if (message == null){
			message = "Inserisci la data";
		}
		DatePickerView pickerPanel = new DatePickerView(editable);
		// pickerPanel.setVisible(true);
//		int res = JOptionPane.showConfirmDialog(parent, pickerPanel, "Inserisci la data",
//				JOptionPane.OK_CANCEL_OPTION);
		JOptionPane pane = new JOptionPane(pickerPanel, JOptionPane.OK_CANCEL_OPTION);
		JDialog dialog = pane.createDialog(parent, message);
		dialog.setSize(new Dimension(300, 300));
		dialog.setVisible(true);
		Object value = pane.getValue();
		if (value != null){
		// if (res == JOptionPane.OK_OPTION){
			try{
				int day = pickerPanel.getDay();
				int month = pickerPanel.getMonth();
				int year = pickerPanel.getYear();
				
				result = LocalDate.of(year, Month.of(month), day);
			}catch(NumberFormatException e){
				e.printStackTrace();
			}
		}
		return result;
	}
	

}
