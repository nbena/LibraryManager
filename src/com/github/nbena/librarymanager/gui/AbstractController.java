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

package com.github.nbena.librarymanager.gui;

import java.awt.Component;
import java.time.LocalDate;
import java.time.Month;

import javax.swing.JOptionPane;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.AbstractTableModel;

import com.github.nbena.librarymanager.gui.view.AbstractTableView;
import com.github.nbena.librarymanager.gui.view.SearchableBook;
import com.github.nbena.librarymanager.gui.view.table.Popupable;

public abstract class AbstractController {
	
	protected final String ASK_DATE_FOR_SEAT = "Scegli la data";
	protected final String SHOW_SEAT_RESERVATION = "Dettagli prenotazione:";
	
	protected LocalDate gotDate;
	
	
	// protected GenericTableView genericTableView;
	protected SearchableBook searchableBookView;
	
	/**
	 * <pre>askNumber</pre> asks the user to insert a number >= 0
	 * @param message the message to show
	 * @return the integer inserted. If some error occurs, an error message is show and
	 * Integer.MAX_VALUE is returned.
	 */
	protected int askNumber(Component view, String message){
		int difference = Integer.MAX_VALUE;

		String diffString = JOptionPane.showInputDialog(view, message,
				"Domanda", JOptionPane.QUESTION_MESSAGE);
		
		if (diffString != null && !diffString.trim().equals("")){
			try{
				difference = Integer.parseInt(diffString);
				
				if (difference <= 0){
					displayMessage(view, "Numbero inserito deve essere > 0",
							"Errore", JOptionPane.ERROR_MESSAGE);
					difference = Integer.MAX_VALUE;
				}
				
			}catch(NumberFormatException e){
				displayMessage(view, "Numero inserito non valido", "Errore",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
		return difference;
	}
	
	
	public static void displayMessage(Component parent, String message, String title, int messageType){
		if (messageType == Integer.MAX_VALUE){
			messageType = JOptionPane.INFORMATION_MESSAGE;
		}
		if (title == null || title.equals("")){
			title = "Info";
		}
		JOptionPane.showMessageDialog(parent, message, title, messageType);
	}
	
	public static void displayReservationOk(Component parent){
		String message = "Prenotazione confermata";
		String title = "Info";
		
		displayMessage(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void displayCancellationOk(Component parent){
		String message = "Cancellazione confermata";
		String title = "Info";
		
		displayMessage(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	public static void displayError(Component parent, Exception exception){
		exception.printStackTrace();
		JOptionPane.showMessageDialog(parent, exception.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void displayError(Component parent, String message){
		JOptionPane.showMessageDialog(parent, message, "Errore", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * displayError displays the content of <pre>message</pre>. This method is intended to
	 * be used when you want to display a message caused by an exception but you do not want to
	 * call <pre>exception.getMessage()</pre>.
	 * @param parent the parent component, can be <pre>null</pre>
	 * @param message	the message to show
	 * @param cause	the exception that have caused  the error, it'll be just printed to
	 * stdout using <pre>cause.printStackTrace()</pre>.
	 */
	public static void displayError(Component parent, String message, Exception cause){
		cause.printStackTrace();
		JOptionPane.showMessageDialog(parent, message, "Errore", JOptionPane.ERROR_MESSAGE);
	}
	
//	protected void showSeatReservationDetails(SeatReservation reservation){
//		SeatDateView seatDateView = new SeatDateView(
//				this.SHOW_SEAT_RESERVATION,
//				reservation.getReservationDate(),
//				reservation.getSeat()
//				);
//		
//		seatDateView.addActionListenerOkButton(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				seatDateView.setVisible(false);
//				seatDateView.dispose();
//			}
//			
//		});
//		
//		seatDateView.addActionListenerCancelButton(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				seatDateView.setVisible(false);
//				seatDateView.dispose();
//			}
//			
//		});
//	}
	
//	protected LocalDate askOrShowDateForReservation(LocalDate date, Seat seat){
//		SeatDateView seatDateView;
//		this.gotDate = null;
////		if(reservation != null){
////			
////		}else{
////			
////		}
//		seatDateView = new SeatDateView(this.ASK_DATE_FOR_SEAT, date, seat);
//		seatDateView.addActionListenerOkButton(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				int day = seatDateView.getDay();
//				int month = seatDateView.getMonth();
//				int year = seatDateView.getYear();
//				gotDate = LocalDate.of(year, Month.of(month), day);
//				
//				seatDateView.setVisible(false);
//				seatDateView.dispose();
//				
//			}
//			
//		});
		
//		seatDateView.addActionListenerCancelButton(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				
//				seatDateView.setVisible(false);
//				seatDateView.dispose();
//			}
//			
//		});
//		seatDateView.setAlwaysOnTop(true);
//		seatDateView.setVisible(true);
//		return gotDate;
//	}
	
	protected void displayNoItemsToShow(Component component){
		displayMessage(component, "Non ci sono elementi da mostrare", "", Integer.MAX_VALUE);
	}
	
	/**
	 * Shows up a new <pre>view</pre> using the given <pre>model</pre>.`
	 * @param tableModel	the TableMOdel that contains the data
	 * @param view	the view in which data will be shown	
	 * @param component	the parent component
	 * @param title	the title to set (it'll be the title of the <pre>view</pre>)
	 */
	protected void displayTableItems(AbstractTableModel tableModel,
			AbstractTableView view,
			Component component,
			String title
			){
	
		if(tableModel.getRowCount()>0){
			view.setTableModel(tableModel);
			view.setAlwaysOnTop(true);
			view.setMainTitle(title);
			view.setVisible(true);
		}else{
			this.displayNoItemsToShow(component);
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
				throw new NumberFormatException("La data non può essere minore di oggi");
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
	 * @throws NumberFormatException if the date is not valid (date.isBefore(today))
	 */
	protected static LocalDate datePicker(Component parent, String message)  throws NumberFormatException{
		LocalDate result = null;
		
		if (message == null){
			message = "Inserisci la data nel formato DD-MM-YYYY o DD/MM/YYYY";
		}
		
		String date = JOptionPane.showInputDialog(parent, message, "Input",
				JOptionPane.QUESTION_MESSAGE);
		
		result = parseDate(date);
		
		return result;
		
	}
	
	
	protected Object[] searchableBookViewResults(){
		String title = (this.searchableBookView.getTitle().equals("")) ? null : searchableBookView.getTitle();
		String [] authors = this.searchableBookView.getAuthors();
		if (authors.length == 0 || authors[0].equals("")){
			authors = null;
		}
		int year = this.searchableBookView.getYear();
		String topic = (this.searchableBookView.getTopic().equals("")) ? null : searchableBookView.getTopic();
		String phouse = (this.searchableBookView.getPhouse().equals("") ? null : searchableBookView.getPhouse());
		
		return new Object[]{
				title,
				authors,
				year,
				topic,
				phouse
		};
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
