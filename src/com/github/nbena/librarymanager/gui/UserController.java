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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;
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
import com.github.nbena.librarymanager.gui.userint.BookDetails;
import com.github.nbena.librarymanager.gui.userint.ConsultationReservationDetails;
import com.github.nbena.librarymanager.gui.userint.Details;
import com.github.nbena.librarymanager.gui.userint.LoanDetails;
import com.github.nbena.librarymanager.gui.userint.LoanReservationDetails;
import com.github.nbena.librarymanager.gui.userint.SeatReservationDetails;
import com.github.nbena.librarymanager.gui.view.GenericUserTableView;
import com.github.nbena.librarymanager.gui.view.LoanView;
import com.github.nbena.librarymanager.gui.view.SearchableBookView;
import com.github.nbena.librarymanager.gui.view.UserView;
import com.github.nbena.librarymanager.gui.view.VisibleView;
import com.github.nbena.librarymanager.gui.view.table.ConsultationReservationTableModel;
import com.github.nbena.librarymanager.gui.view.table.CopyTableModel;
import com.github.nbena.librarymanager.gui.view.table.LoanReservationTableModel;
import com.github.nbena.librarymanager.gui.view.table.LoanTableModel;
import com.github.nbena.librarymanager.gui.view.table.SeatReservationTableModel;

public class UserController extends AbstractController {
	/**
	 * The action pattern is not followed HERE because user's actions are not
	 * 'atomic' as librarian's ones.
	 */
	
	private UserView userView;
	private UserModel userModel;
	
	private LoanView loanView;
	private Details details;
	
	private UserController controller;
	private GenericUserTableView genericTableView;
	
	private static final String TITLE_SEARCH_RESULTS = "Risultato ricerca";
	private static final String TITLE_VIEW_LOANS = "I tuoi prestiti";
	private static final String TITLE_VIEW_LOAN_RESERVATIONS = "Le tue prenotazioni";
	private static final String TITLE_VIEW_SEAT_RESERVATIONS = "Le tue prenotazioni per posti";
	private static final String TITLE_VIEW_CONSULTATION_RESERVATIONS = "Le tue prenotazioni per consultazioni";
	// private static final String TITLE_UNREGISTER = "Deregistrati";
	// private static final String RESERVE_SEAT = "Prenota posto";
	// private static final String TITLE_LOGOUT = "Logout";

	public UserController(UserModel userModel, UserView userView) {
		this.userView = userView;
		this.userModel = userModel;
		
		this.genericTableView = new GenericUserTableView();
		super.searchableBookView = new SearchableBookView();
		this.loanView = new LoanView();
		
		this.addListeners();
		
		this.userView.setVisible(true);
		
		this.controller = this;
	}
	
	public LoanView getLoanView(){
		return this.loanView;
	}


	private void addListeners(){
		this.addBasicListeneres();
		this.addGenericViewListeners();
		this.addSearchableViewListeners();
		// this.addListenersLoanView();
	}
	
	
	
	private void addSearchableViewListeners(){
		
		this.searchableBookView.addActionListenerOk(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
				Object [] res = searchableBookViewResults();
				String title = (String) res[0];
				String [] authors = (String[]) res[1];
				int year = (int) res[2];
				String topic = (String) res[3];
				String phouse = (String) res[4];
				
				try {
					List<Copy> copies = userModel.search(title, authors, year, topic, phouse);
			
					details = new BookDetails(controller);
					
					displayTableItems(new CopyTableModel(copies), genericTableView, userView,
							TITLE_SEARCH_RESULTS);
					genericTableView.setMenuItemReserveEnabled(true);
					genericTableView.setMenuItemCancelEnabled(false);
					genericTableView.setMenuItemDetailsEnabled(true);
				} catch (SQLException e) {
					displayError(userView, e);
				} catch(InvalidParameterException e){
					
				}
				
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
				
				details.setItem(o);
				details.show();
			}
			
		});
		
		// ok for seat
		this.genericTableView.addMenuItemCancelListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				unreserve((AbstractReservation) genericTableView.getSelectedItem(),
						genericTableView, true);
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
				
				reserve((Copy) genericTableView.getSelectedItem());
			}
			
		});
	}
	
	
	private void addBasicListeneres(){
		
		this.userView.addActionListenerLogout(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				int res = JOptionPane.showConfirmDialog(userView,
						"Sei sicuro di voler uscire?",
						"Domanda",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				
				if (res == JOptionPane.YES_OPTION){
					userModel.close();
					userView.setVisible(false);
					userView.dispose();
				}
			}
			
		});
		
		// new seat reservation
		
		// tested and it works
		this.userView.addActionListenerBtnNewSeatReservation(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				try{
					
				
				LocalDate date = datePicker(userView, null);
				if (date != null){
					
						userModel.reserveSeat(date);
						
						displayReservationOk(userView);

					}
				}
				catch(NumberFormatException e1){
					displayMessage(userView, "Errore nell'input", "Errore", JOptionPane.ERROR_MESSAGE);
				} catch (ReservationException | SQLException e) {
					displayMessage(userView, "Errore nella prenotazione", "Errore", JOptionPane.ERROR_MESSAGE);
				} 
			}
			
		});
		
		
		this.userView.addActionListenerSearch(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				searchableBookView.reset();
				searchableBookView.setVisible(true);				
			}
			
		});
		
		this.userView.addActionListenerViewConsultationReservation(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					List<ConsultationReservation> reservations = userModel.getConsultationReservations();
					displayTableItems(new ConsultationReservationTableModel(reservations),
							genericTableView,
							userView,
							TITLE_VIEW_CONSULTATION_RESERVATIONS
							);
					
					details = new ConsultationReservationDetails(controller);
					
					genericTableView.setMenuItemCancelEnabled(true);
					genericTableView.setMenuItemDetailsEnabled(true);
					genericTableView.setMenuItemReserveEnabled(false);
				} catch (SQLException e1) {
					displayError(userView, e1);
				}
			}
			
		});
		
		this.userView.addActionListenerViewLoan(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					List<Loan> loans = userModel.getActiveLoans();
					displayTableItems(new LoanTableModel(loans), genericTableView, userView,
							TITLE_VIEW_LOANS);
					
					details = new LoanDetails(controller);
					
					genericTableView.setMenuItemCancelEnabled(false);
					genericTableView.setMenuItemDetailsEnabled(true);
					genericTableView.setMenuItemReserveEnabled(false);
				} catch (SQLException e1) {
					displayError(userView, e1);
				}
			}
			
		});
		
		this.userView.addActionListenerViewLoanReservation(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					List<LoanReservation> reservations = userModel.getLoanReservations();
					displayTableItems(new LoanReservationTableModel(reservations),
							genericTableView, userView,
							TITLE_VIEW_LOAN_RESERVATIONS
							);
					
					details = new LoanReservationDetails(controller);
					
					genericTableView.setMenuItemCancelEnabled(true);
					genericTableView.setMenuItemDetailsEnabled(true);
					genericTableView.setMenuItemReserveEnabled(false);
				} catch (SQLException e1) {
					displayError(userView, e1);
				}
			}
			
		});
		
		
		// tested and works
		this.userView.addActionListenerViewSeatReservation(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					List<SeatReservation> reservations = userModel.getSeatsReservations();	
					displayTableItems(new SeatReservationTableModel(reservations),
							genericTableView, userView,
							TITLE_VIEW_SEAT_RESERVATIONS
							);
					
					details = new SeatReservationDetails(controller);
					
					genericTableView.setMenuItemCancelEnabled(true);
					genericTableView.setMenuItemDetailsEnabled(true);
					genericTableView.setMenuItemReserveEnabled(false);
				} catch (SQLException e1) {
					displayError(userView, e1);
				}
			}
			
		});
		
		this.userView.addActionListenerDeregister(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int res = JOptionPane.showConfirmDialog(userView, "Sei sicuro di voler deregistrarti?",
						"Conferma", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if(res==JOptionPane.YES_OPTION){
					try {
						userModel.deregister();
						userModel.close();
						userView.setVisible(false);
						userView.dispose();
					} catch (SQLException e) {
						if (e.getMessage().contains("loans before")){
							displayError(userView, "Devi restituire i tuoi prestiti/consultazioni prima");
						}else{
							displayError(userView, e);
						}
					}
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
	
	public boolean unreserve(AbstractReservation reservation, VisibleView view, boolean dispose){
		boolean ok = true;
		int res = JOptionPane.showConfirmDialog(userView,
				"Vuoi cancellare questa prenotazione?",
				"Conferma",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE
				);
		if (res == JOptionPane.YES_OPTION){
			try {
				userModel.cancelReservation(reservation);
				// TODO remove from the table
				
				AbstractController.displayCancellationOk(userView);
				
				// genericTableView.setVisible(false);
				view.setVisible(false);
				if (dispose){
					view.dispose();
				}
			} catch (SQLException e1) {
				displayError(userView, e1);
				ok = false;
			}
		}
		return ok;
	}
	
	public boolean reserve(Copy item){
		boolean ok = true;
		String message = "Vuoi prenotare questo libro?";
		if (item instanceof CopyForConsultation){
			message = "Vuoi prenotare una consultazione per questo libro?";
		}
		
		// asking confirm
		int res = JOptionPane.showConfirmDialog(userView, message, "Info", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (res == JOptionPane.YES_OPTION){
			try{
				message = null;
				if (item instanceof CopyForConsultation){
					// asking user for when he wants to consult
					LocalDate date = super.datePicker(userView,
							"Inserisci quando vuoi effettuare la consultazione "+
							"nel formato DD-MM-YYYY o DD/MM/YYYY");
					if (date!=null){
						ConsultationReservation consultation =
								userModel.reserveConsultation((CopyForConsultation) item, date);
						message = String.format("%s%d:%s", "Prenotazione effettuata con successo, " +
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

			}catch(SQLException | ReservationException | NumberFormatException e){
				displayError(userView, e);
				ok = false;
			}				
		}
		return ok;
	}
	
	public Loan renewLoan(Loan loan){
		
		Loan renewed = null;
		
		int res = JOptionPane.showConfirmDialog(userView, "Confermi di voler rinnovare?",
				"Conferma", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		if (res == JOptionPane.YES_OPTION){
			try {
				renewed = this.userModel.renewLoan(loan);

				AbstractController.displayMessage(this.userView, "Confermi di voler rinnovare?", "Info",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException | ReservationException e) {
				AbstractController.displayError(this.userView, e);
			}
			
		}
		return renewed;

	}
	
}




