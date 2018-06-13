package com.github.nbena.librarymanager.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Month;

import javax.swing.JOptionPane;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.AbstractTableModel;

import com.github.nbena.librarymanager.core.Seat;
import com.github.nbena.librarymanager.core.SeatReservation;
import com.github.nbena.librarymanager.gui.view.GenericTableView;
import com.github.nbena.librarymanager.gui.view.SearchableBookView;
import com.github.nbena.librarymanager.gui.view.SeatDateView;
import com.github.nbena.librarymanager.gui.view.table.Popupable;

public abstract class AbstractController {
	
	protected final String ASK_DATE_FOR_SEAT = "Scegli la data";
	protected final String SHOW_SEAT_RESERVATION = "Dettagli prenotazione:";
	
	protected LocalDate gotDate;
	
	
	protected GenericTableView genericTableView;
	protected SearchableBookView searchableBookView;
	
	
	protected void displayMessage(Component parent, String message, String title, int messageType){
		if (messageType == Integer.MAX_VALUE){
			messageType = JOptionPane.INFORMATION_MESSAGE;
		}
		if (title == null || title.equals("")){
			title = "Info";
		}
		JOptionPane.showMessageDialog(parent, message, title, messageType);
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
	
	protected void displayTableItems(AbstractTableModel tableModel, Component component){
	
		if(tableModel.getRowCount()>0){
			this.genericTableView.setTableModel(tableModel);
			this.genericTableView.setAlwaysOnTop(true);
			this.genericTableView.setVisible(true);		
		}else{
			this.displayMessage(component, "Non ci sono elementi da mostrare", "", Integer.MAX_VALUE);
		}
	}
	
	
//	protected LocalDate datePicker(Component parent, String message, boolean editable){
//		LocalDate result = null;
//		if (message == null){
//			message = "Inserisci la data";
//		}
//		DatePickerView pickerPanel = new DatePickerView(editable);
//		// pickerPanel.setVisible(true);
////		int res = JOptionPane.showConfirmDialog(parent, pickerPanel, "Inserisci la data",
////				JOptionPane.OK_CANCEL_OPTION);
//		JOptionPane pane = new JOptionPane(pickerPanel, JOptionPane.QUESTION_MESSAGE,
//				JOptionPane.OK_OPTION);
//		JDialog dialog = pane.createDialog(parent, message);
//		dialog.setSize(new Dimension(300, 300));
//		dialog.setVisible(true);
//		Object value = pane.getValue();
//		if (value != null){
//		// if (res == JOptionPane.OK_OPTION){
//				int day = pickerPanel.getDay();
//				int month = pickerPanel.getMonth();
//				int year = pickerPanel.getYear();
//				
//				result = LocalDate.of(year, Month.of(month), day);
//		}
//		return result;
//	}
	
	protected static LocalDate parseDate(String date){
		LocalDate result = null;
		if (date != null && date != ""){
			if (!date.contains("/") && !date.contains("-")){
				throw new NumberFormatException();
			}
			
			String splitchar = "/";
			if (date.contains("-")){
				splitchar = "-";
			}
			
			String [] tokens = date.split(splitchar);
			if (tokens.length != 3){
				throw new NumberFormatException();
			}
			
			int year = Integer.parseInt(tokens[2]);
			int month = Integer.parseInt(tokens[1]);
			int day = Integer.parseInt(tokens[0]);
			
			result = LocalDate.of(year, Month.of(month), day);
			
			if (result.isBefore(LocalDate.now())){
				throw new NumberFormatException("The date must be greater than today");
			}
		}	
		return result;
	}
	
	/**
	 * Asks user for a date,ì. User will be prompted with a JOptionPane
	 * with a basic input in which he can 'freely' types.
	 * @param parent	the parent component
	 * @param message	the message to display, can be null. If so
	 * 					it's set to "Inserisci la data nel formato
	 * 					DD-MM-YYYY o DD/MM/YYYY"
	 * @return the date that the suer inserted, null if the inserted date is ''
	 * @throws NumberFormatException if the date is not valid
	 */
	protected static LocalDate datePicker(Component parent, String message)  throws NumberFormatException{
		LocalDate result = null;
		
		if (message == null){
			message = "Inserisci la data nel formato DD-MM-YYYY o DD/MM/YYYY";
		}
		
		String date = JOptionPane.showInputDialog(parent, message, "Info",
				JOptionPane.QUESTION_MESSAGE);
		
		result = parseDate(date);
		
		return result;
		
	}
	
	
	protected void addPopupListenerToTable(Popupable popuable){
		popuable.addPopupListener(new PopupMenuListener(){

			@Override
			public void popupMenuCanceled(PopupMenuEvent arg0) {
				
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
				
			}

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				popuable.setSelectedRowToPopup();
			}
			
		});
	}
	

}
