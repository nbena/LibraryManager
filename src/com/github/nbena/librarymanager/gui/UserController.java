package com.github.nbena.librarymanager.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JOptionPane;

import com.github.nbena.librarymanager.core.AbstractReservation;
import com.github.nbena.librarymanager.core.ConsultationReservation;
import com.github.nbena.librarymanager.core.Copy;
import com.github.nbena.librarymanager.core.CopyForConsultation;
import com.github.nbena.librarymanager.core.Loan;
import com.github.nbena.librarymanager.core.LoanReservation;
import com.github.nbena.librarymanager.core.ReservationException;
import com.github.nbena.librarymanager.core.SeatReservation;
import com.github.nbena.librarymanager.gui.view.GenericTableView;
import com.github.nbena.librarymanager.gui.view.SearchableBookView;
import com.github.nbena.librarymanager.gui.view.UserView;
import com.github.nbena.librarymanager.gui.view.table.ConsultationReservationTableModel;
import com.github.nbena.librarymanager.gui.view.table.CopyTableModel;
import com.github.nbena.librarymanager.gui.view.table.LoanReservationTableModel;
import com.github.nbena.librarymanager.gui.view.table.LoanTableModel;
import com.github.nbena.librarymanager.gui.view.table.SeatReservationTableModel;

public class UserController extends AbstractController {
	
	
	private UserView userView;
	private UserModel userModel;
	
	// private LocalDate gotDate;
	

	public UserController(UserModel userModel, UserView userView) {
		this.userView = userView;
		this.userModel = userModel;
		
		super.genericTableView = new GenericTableView();
		super.searchableBookView = new SearchableBookView();
		
		this.addListeners();
		
		this.userView.setVisible(true);
	}


	private void addListeners(){
		this.addBasicListeneres();
		this.addGenericViewListeners();
		this.addSearchableViewListeners();
	}
	
	
	
	private void addSearchableViewListeners(){
		
		this.searchableBookView.addOkButtonListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String title = (searchableBookView.getTitle().equals("")) ? null : searchableBookView.getTitle();
				String [] authors = searchableBookView.getAuthors();
				if (authors.length == 0 || authors[0].equals("")){
					authors = null;
				}
				int year = searchableBookView.getYear();
				String topic = (searchableBookView.getTopic().equals("")) ? null : searchableBookView.getTopic();
				
//				System.out.println(searchableBookView.getAuthors().length);
//				System.out.println(Arrays.toString(searchableBookView.getAuthors()));
//				System.out.println(Arrays.toString(authors));
				
				try {
					List<Copy> copies = userModel.search(title, authors, year, topic);
					displayTableItems(new CopyTableModel(copies), userView);
					genericTableView.setMenuItemCancelEnabled(false);
					genericTableView.setMenuItemDetailsEnabled(false);
				} catch (SQLException e) {
					displayError(userView, e);
				}
				
			}
			
		});
		
		this.searchableBookView.addCancelButtonListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				searchableBookView.setVisible(false);
			}
			
			
		});
	}
	
	
	private void addGenericViewListeners(){
		
		super.addPopupListenerToTable(this.genericTableView);
		
		this.genericTableView.addMenuItemDetailsListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Object o = genericTableView.getSelectedItem();
				// TODO add a detail viewer
				System.out.println(o);
			}
			
		});
		
		// ok for seat
		this.genericTableView.addMenuItemCancelListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractReservation reservation = (AbstractReservation) genericTableView.getSelectedItem();
				
				int res = JOptionPane.showConfirmDialog(userView,
						"Vuoi cancellare questa prenotazione?",
						"Conferma",
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE
						);
				if (res == JOptionPane.OK_OPTION){
					try {
						userModel.cancelReservation(reservation);
						// TODO remove from the table
						displayMessage(userView, "Prenotazione eliminata con successo",
								null, Integer.MAX_VALUE);
						genericTableView.setVisible(false);
					} catch (SQLException e1) {
						displayError(userView, e1);
					}
				}
				// else do nothing
			}
			
		});
		
		// eclipse says that 'cancelling' is not correct while 'canceling' it is.
		// ...
		
		// ok for reserving a book, ok canceling it too.
		// ok for reserving a consultation reservation and canceling it!
		this.genericTableView.addMenuItemReserveListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Copy item = (Copy) genericTableView.getSelectedItem();
				String message = "Vuoi prenotare questo libro?";
				if (item instanceof CopyForConsultation){
					message = "Vuoi prenotare una consultazione per questo libro?";
				}
				
				// asking confirm
				int res = JOptionPane.showConfirmDialog(userView, message, "Info", 
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (res == JOptionPane.OK_OPTION){
					try{
						message = null;
						if (item instanceof CopyForConsultation){
							// asking user for when he wants to consult
							LocalDate date = datePicker(userView,
									"Indica quando vuoi effettuare la consultazione");
							if (date!=null){
								ConsultationReservation consultation =
										userModel.reserveConsultation(item, date);
								message = String.format("%s%d:%s", "Prenotazione effettuata con successo," +
										"il tavolo:posto per te è ",
										consultation.getSeat().getTableNumber(),
										consultation.getSeat().getNumber());
							}
						}else{
							userModel.reserveLoan(item);
							message = "Prenotazione effettuata con successo";
						}
						
						if (message != null){
							displayMessage(userView, message, null, Integer.MAX_VALUE);
						}

					}catch(SQLException | ReservationException e){
						displayError(userView, e);
					}				
				}
			}
			
		});
	}
	
	
	private void addBasicListeneres(){
		
		// new seat reservation
		
		// tested and it works
		this.userView.addActionListenerBtnNewSeatReservation(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				try{
					
				
				LocalDate date = datePicker(userView, null);
				if (date != null){
//					try {
						SeatReservation reservation = userModel.reserveSeat(date);
						showSeatReservationDetails(reservation);
//					} catch (ReservationException | SQLException e) {
//						displayError(userView, e);
//					}

				}
				}
				catch(NumberFormatException e1){
					displayMessage(userView, "Errore nell'input", "Errore", JOptionPane.ERROR_MESSAGE);
				} catch (ReservationException | SQLException e) {
					displayMessage(userView, "Errore nella prenotazione", "Errore", JOptionPane.ERROR_MESSAGE);
				} 
			}
			
		});
		
		
		this.userView.addActionListenerBtnSearch(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				searchableBookView.reset();
				searchableBookView.setVisible(true);				
			}
			
		});
		
		this.userView.addActionListenerBtnViewConsultationReservation(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					List<ConsultationReservation> reservations = userModel.getConsultationReservation();
					displayTableItems(new ConsultationReservationTableModel(reservations), userView);
					genericTableView.setMenuItemCancelEnabled(true);
					genericTableView.setMenuItemDetailsEnabled(true);
					genericTableView.setMenuItemReserveEnabled(false);
				} catch (SQLException e1) {
					displayError(userView, e1);
				}
			}
			
		});
		
		this.userView.addActionListenerBtnViewLoan(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					List<Loan> loans = userModel.getActiveLoan();
					displayTableItems(new LoanTableModel(loans), userView);
					genericTableView.setMenuItemCancelEnabled(false);
					genericTableView.setMenuItemDetailsEnabled(true);
					genericTableView.setMenuItemReserveEnabled(false);
				} catch (SQLException e1) {
					displayError(userView, e1);
				}
			}
			
		});
		
		this.userView.addActionListenerBtnViewLoanReservation(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					List<LoanReservation> reservations = userModel.getLoanReservation();
					displayTableItems(new LoanReservationTableModel(reservations), userView);
					genericTableView.setMenuItemCancelEnabled(true);
					genericTableView.setMenuItemDetailsEnabled(true);
					genericTableView.setMenuItemReserveEnabled(false);
				} catch (SQLException e1) {
					displayError(userView, e1);
				}
			}
			
		});
		
		
		// tested and works
		this.userView.addActionListenerBtnViewSeatReservation(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					List<SeatReservation> reservations = userModel.getSeatsReservations();	
					displayTableItems(new SeatReservationTableModel(reservations), userView);
					genericTableView.setMenuItemCancelEnabled(true);
					genericTableView.setMenuItemDetailsEnabled(true);
					genericTableView.setMenuItemReserveEnabled(false);
				} catch (SQLException e1) {
					displayError(userView, e1);
				}
			}
			
		});
		
//		this.genericTableView.addTableMouseListener(new MouseListener(){
//
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void mouseEntered(MouseEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void mouseExited(MouseEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void mousePressed(MouseEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void mouseReleased(MouseEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		});
//		
	}
	
}




