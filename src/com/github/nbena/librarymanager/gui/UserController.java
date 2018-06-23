/*  LibraryManager
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
import com.github.nbena.librarymanager.gui.userint.ConsultationReservationDetails;
import com.github.nbena.librarymanager.gui.userint.Details;
import com.github.nbena.librarymanager.gui.userint.LoanDetails;
import com.github.nbena.librarymanager.gui.userint.LoanReservationDetails;
import com.github.nbena.librarymanager.gui.userint.SeatReservationDetails;
import com.github.nbena.librarymanager.gui.view.GenericTableView;
import com.github.nbena.librarymanager.gui.view.LoanView;
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
	
	private LoanView loanView;
	private Details details;
	
	private UserController controller;
	
	// private LocalDate gotDate;
	

	public UserController(UserModel userModel, UserView userView) {
		this.userView = userView;
		this.userModel = userModel;
		
		super.genericTableView = new GenericTableView();
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
		this.addListenersLoanView();
	}
	
	
	
	private void addSearchableViewListeners(){
		
		this.searchableBookView.addActionListenerOk(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
//				String title = (searchableBookView.getTitle().equals("")) ? null : searchableBookView.getTitle();
//				String [] authors = searchableBookView.getAuthors();
//				if (authors.length == 0 || authors[0].equals("")){
//					authors = null;
//				}
//				int year = searchableBookView.getYear();
//				String topic = (searchableBookView.getTopic().equals("")) ? null : searchableBookView.getTopic();
				
//				System.out.println(searchableBookView.getAuthors().length);
//				System.out.println(Arrays.toString(searchableBookView.getAuthors()));
//				System.out.println(Arrays.toString(authors));
				
				Object [] res = searchableBookViewResults();
				String title = (String) res[0];
				String [] authors = (String[]) res[1];
				int year = (int) res[2];
				String topic = (String) res[3];
				String phouse = (String) res[4];
				
				try {
					List<Copy> copies = userModel.search(title, authors, year, topic, phouse);
					displayTableItems(new CopyTableModel(copies), userView);
					genericTableView.setMenuItemCancelEnabled(false);
					genericTableView.setMenuItemDetailsEnabled(false);
				} catch (SQLException e) {
					displayError(userView, e);
				}
				
			}
			
		});
		
		this.searchableBookView.addActionListenerCancel(new ActionListener(){

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
				
				details.setItem(o);
				details.show();
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
										userModel.reserveConsultation((CopyForConsultation) item, date);
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

					}catch(SQLException | ReservationException | NumberFormatException e){
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
						/*SeatReservation reservation = */userModel.reserveSeat(date);
						
						
						// showSeatReservationDetails(reservation);
						displayReservationOk(userView);
						
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
					List<ConsultationReservation> reservations = userModel.getConsultationReservation();
					displayTableItems(new ConsultationReservationTableModel(reservations), userView);
					
					details = new ConsultationReservationDetails();
					
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
					List<Loan> loans = userModel.getActiveLoan();
					displayTableItems(new LoanTableModel(loans), userView);
					
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
					List<LoanReservation> reservations = userModel.getLoanReservation();
					displayTableItems(new LoanReservationTableModel(reservations), userView);
					
					details = new LoanReservationDetails();
					
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
					displayTableItems(new SeatReservationTableModel(reservations), userView);
					
					details = new SeatReservationDetails();
					
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
						"Conferma", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if(res==JOptionPane.OK_OPTION){
					try {
						userModel.deregister();
						userModel.close();
						userView.setVisible(false);
						userView.dispose();
					} catch (SQLException e) {
						displayError(userView, e);
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
	
	private Loan renewLoan(Loan loan) throws SQLException, ReservationException{
		return this.userModel.renewLoan(loan);
	}
	
	private void addListenersLoanView(){
		
		this.loanView.addActionListenerRenew(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0){
					
				Loan loan = (Loan) genericTableView.getSelectedItem();
				try {
					Loan renewed = renewLoan(loan);
					loanView.setLoan(renewed);
				} catch (SQLException | ReservationException e) {
						displayError(userView, e);
				}
			} 	
			
		});
		
		this.loanView.addActionListenerOk(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				loanView.setVisible(false);
			}
			
		});
		
		this.loanView.addActionListenerCancel(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				loanView.setVisible(false);
			}
			
		});
	}
	
}




