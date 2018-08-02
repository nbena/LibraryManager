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
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.ArrayUtils;

import com.github.nbena.librarymanager.core.User;
import com.github.nbena.librarymanager.gui.librarianint.Action;
import com.github.nbena.librarymanager.gui.librarianint.ActionAddBook;
import com.github.nbena.librarymanager.gui.librarianint.ActionAddCopies;
import com.github.nbena.librarymanager.gui.librarianint.ActionAddUser;
import com.github.nbena.librarymanager.gui.librarianint.ActionDeleteBook;
import com.github.nbena.librarymanager.gui.librarianint.ActionDeleteCopies;
import com.github.nbena.librarymanager.gui.librarianint.ActionDeliveryConsultation;
import com.github.nbena.librarymanager.gui.librarianint.ActionDeliveryLoan;
import com.github.nbena.librarymanager.gui.librarianint.ActionNewNotReservedConsultation;
import com.github.nbena.librarymanager.gui.librarianint.ActionNewNotReservedLoan;
import com.github.nbena.librarymanager.gui.librarianint.ActionNewReservedConsultation;
import com.github.nbena.librarymanager.gui.librarianint.ActionNewReservedLoan;
import com.github.nbena.librarymanager.gui.librarianint.ActionSendMail;
import com.github.nbena.librarymanager.gui.view.LibrarianConsultationsTableView;
import com.github.nbena.librarymanager.gui.view.BookTableView;
import com.github.nbena.librarymanager.gui.view.LibrarianView;
import com.github.nbena.librarymanager.gui.view.LoansInLateView;
import com.github.nbena.librarymanager.gui.view.RegisterUserView;
import com.github.nbena.librarymanager.gui.view.SearchableBookUserView;
import com.github.nbena.librarymanager.gui.view.table.BookCopiesNumberTableModel;
import com.github.nbena.librarymanager.gui.view.table.ConsultationInProgressTableModel;
import com.github.nbena.librarymanager.gui.view.table.ConsultationReservationTableModel;
import com.github.nbena.librarymanager.gui.view.table.LoansInLateTableModel;
import com.github.nbena.librarymanager.gui.view.SearchableBookUser;
import com.github.nbena.librarymanager.core.Book;
import com.github.nbena.librarymanager.core.BookCopiesNumber;
import com.github.nbena.librarymanager.core.Consultation;
import com.github.nbena.librarymanager.core.ConsultationReservation;
import com.github.nbena.librarymanager.core.InternalUser;
import com.github.nbena.librarymanager.core.Loan;
import com.github.nbena.librarymanager.core.ReservationException;

public class LibrarianController extends AbstractController {
	
	/*
	 * How the ACTION PATTERN WORKS:
	 * -	a main controller (this)
	 * -	a 'sub view' that appears the same but it does different actions
	 * 		basing on the clicked button that have triggered that view opening.
	 * -	we have an 'Action' interface that offers the following: setParams, execute
	 * 		-	internally each implementation register a different function to be executed
	 * 			when a call to implementation.execute() happens
	 * -	the onclick button of the sub view triggers the action.execute
	 * -	the onclick buttons of the main view (the ones that onclick make the sub view to open)
	 * 		instances the concrete implementation of the action.
	 * 
	 * This pattern is followed EVERYWHERE HERE because it allows me to write
	 * more consistent code. 
	 */
	
	private LibrarianModel model;
	private LibrarianView view;
	
	private Action action;
	
	private RegisterUserView userView;
	private LibrarianConsultationsTableView consultationsView;
	private LoansInLateView loansInLateView;
	private BookTableView bookView;
	
	private boolean isWithUser = false;
	
	private void showWithUsersView(boolean withUsers) throws SQLException{
		if (withUsers){
			((SearchableBookUser)this.searchableBookView).setUsers(this.model.users());
		}else{
			((SearchableBookUser)this.searchableBookView).setUserPanelEnabled(false);
		}
		this.isWithUser = withUsers;
		this.searchableBookView.setVisible(true);
	}
	
	
//	private User askUser() throws SQLException{
//		String user = JOptionPane.showInputDialog(this.view,
//				"Inserisci la mail dell'utente", "Utente", JOptionPane.QUESTION_MESSAGE);
//		
//		User asked = null;
//		if (user!= null && !user.equals("")){
//			asked = this.model.getUserFromEmail(user);
//		}
//		
//		return asked;
//	}
	
	
	public LibrarianController(LibrarianModel model, LibrarianView view) {
		this.model = model;
		this.view = view;
		
		this.searchableBookView = new SearchableBookUserView();
		this.userView = new RegisterUserView();
		this.consultationsView = new LibrarianConsultationsTableView();
		this.loansInLateView = new LoansInLateView();
		this.bookView = new BookTableView();
		
		this.addListeners();
		
		this.view.setVisible(true);
	}
	
	
	private void addListeners(){
		this.addListenersToMainView();
		this.addSearchableViewListeners();
		this.addUserViewListeners();
		this.addConsultationsListListeners();
		this.addLateLoansListeners();
		this.addBookViewListeners();
	}
	
	
	private void addListenersToMainView(){
		
		this.view.addActionListenerNewNotReservedLoan(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent arg0){
				try {
//					User user = askUser();
//					searchableBookView.reset();
//					searchableBookView.setVisible(true);
					action = new ActionNewNotReservedLoan(model);
					showWithUsersView(true);
				} catch (SQLException e) {
					displayError(view, e);
				}
			}
		});
		
		this.view.addActionListenerNewReservedLoan(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent arg0){
				try {
//					User user = askUser();
//					searchableBookView.reset();
//					searchableBookView.setVisible(true);
					action = new ActionNewReservedLoan(model);
					showWithUsersView(true);
					// action = new ActionNewReservedLoan(model);
				} catch (SQLException e) {
					displayError(view, e);
				}
			}
		});
		
		this.view.addActionListenerDeliveryLoan(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					action = new ActionDeliveryLoan(model);
					showWithUsersView(true);
				} catch (SQLException e1) {
					displayError(view, e1);
				}
			}
			
		});
		
		this.view.addActionListenerNewNotReservedConsultation(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					action = new ActionNewNotReservedConsultation(model);
					showWithUsersView(true);
				} catch (SQLException e1) {
					displayError(view, e1);
				}
			}
			
		});
		
		this.view.addActionListenerNewReservedConsultation(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					action = new ActionNewReservedConsultation(model);
					showConsultationsReservationPerUser();
				} catch (SQLException | ReservationException e1) {
					displayError(view, e1);
				} catch (ClassCastException e1){
					ReservationException ex = new ReservationException("Qualcosa è andato storto", e1);
					displayError(view, ex);
				}
			}
			
		});
		
		this.view.addActionListenerDeliveryConsultation(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					action = new ActionDeliveryConsultation(model);
					showConsultationsPerUser();
					
				} catch (SQLException e1) {
					displayError(view, e1);
				} catch (ReservationException e1) {
					displayError(view, e1);
				}
			}
			
		});
		
		this.view.addActionListenerAddUser(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				userView.setVisible(true);
				action = new ActionAddUser(model);
			}
			
		});
		
		this.view.addActionListenerAddBook(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					action = new ActionAddBook(model);
					showWithUsersView(false);
				} catch (SQLException e1) {
					displayError(view, e1);
				}
			}
			
		});
		
		this.view.addActionListenerDeleteBook(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				try{
					action = new ActionDeleteBook(model);
					List<BookCopiesNumber> books = model.getDeletableBooks();
//					bookView.setMenuItemIncrementCopiesNumberEnabled(false);
//					bookView.setMenuItemDecrementCopiesNumberEnabled(false);
//					bookView.setMenuItemDeleteEnabled(true);
//					displayTableItems(new BookCopiesNumberTableModel(books), bookView, view);
					displayBooks(action, books);
				}catch(SQLException e1){
					displayError(view, e1);
				}
			}
			
		});
		
		this.view.addActionListenerChangeCopiesNumber(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					/**
					 * We use the deleteBookView as a more generic BookView
					 * that lets us to change copies number too.
					 */
					// here the real action is decided then	

//					List<BookCopiesNumber> books = model.books();
//					bookView.setMenuItemIncrementCopiesNumberEnabled(true);
//					bookView.setMenuItemDeleteEnabled(false);
//					displayTableItems(new BookCopiesNumberTableModel(books), bookView, view);
					
					// AddAcopies so the method know which MenuItem enables.
					// it is a fake 'action' because the action attribute of 
					// model is actually changed when the menuItem is clicked.
					// we use AddCopie just because the method displayBooks:
					// <pre>
					// if (action instanceof ActionAddCopies || action instanceof ActionDeleteCopies)
					// menItemIncrement.enabled
					// menuItemDecrement.enabled
					// </pre>
					displayBooks(new ActionAddCopies(model), null);
				} catch (SQLException e1) {
					displayError(view, e1);
				}
			}
			
		});
		
		this.view.addActionListenerViewConsultationsInProgress(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
		
				try {
					List<Consultation> consultations = model.consultations(null);
					displayTableItems(new ConsultationInProgressTableModel(consultations),
							consultationsView, view);
				} catch (SQLException e) {
					displayError(view, e);
				}
			}
			
		});
		
		this.view.addActionListenerViewLoansInLate(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				try{
					List<Loan> loans = model.getLoansInLate();
					displayTableItems(new LoansInLateTableModel(loans),
							loansInLateView, view);
					action = new ActionSendMail(model);
				}catch(SQLException e1){
					displayError(view, e1);
				}
				
			}
			
		});
	}

	private String askUser() {
		return JOptionPane.showInputDialog(view,
				"Inserisci la mail dell'uente", "Info", JOptionPane.QUESTION_MESSAGE);
	}
	
	private User fillUser(String email) throws SQLException, ReservationException{
		User u = null;
		/* need to check it's not null because it's called afeter askUser() */
		if (email != null && (email != "" && ! email.trim().equals(""))){
			u = this.model.getUser(email);
		}
		return u;
	}
	
	private User askAndFillUser() throws SQLException, ReservationException{
		String email = this.askUser();
		return this.fillUser(email);
	}
	
	/**
	 * This method fetches the list of BookCopiesNumber and displays them.
	 * The <pre>MenuItem</pre>(s) are properly enabled basing on the current
	 * action.
	 * @param action the Action you want to do. We do not use the attribute because
	 * in some cases the action is set after then a call to this function.
	 * @param books if null, a list will be loaded with <pre>model.books()</pre>
	 * @throws SQLException
	 */
	private void displayBooks(Action action, List<BookCopiesNumber> books) throws SQLException{
		if (books==null){
			books = this.model.books();
		}
		boolean incrementEnabled = false;
		boolean decrementEnabled = false;
		boolean deleteEnabled = false;
		if(action instanceof ActionAddCopies || action instanceof ActionDeleteCopies){
			incrementEnabled = true;
			decrementEnabled = true;
		}
		
		if (action instanceof ActionDeleteBook){
			deleteEnabled = true;
		}
		
		this.bookView.setMenuItemIncrementCopiesNumberEnabled(incrementEnabled);
		this.bookView.setMenuItemDecrementCopiesNumberEnabled(decrementEnabled);
		this.bookView.setMenuItemDeleteEnabled(deleteEnabled);
		
		super.displayTableItems(new BookCopiesNumberTableModel(books),
				bookView, view);
	}

	private void showConsultationsPerUser() throws SQLException, ReservationException{
		User user = askAndFillUser();

		if (user!=null){
			// now showing all the in progress consultation
			// for this user.

			List<Consultation> consultations = model.consultations(user);
			consultationsView.setMenuItemDeliveryEnabled(true);
			consultationsView.setMenuItemStartEnabled(false);
			super.displayTableItems(new ConsultationInProgressTableModel(consultations),
					consultationsView, view);
		}
	}
	
	/**
	 * Ask the librarian to insert the user email, then it fills up
	 * the user object, then it gets all the consultations reservations
	 * that that user has for today.
	 * @throws SQLException
	 * @throws ReservationException
	 * @throws ClassCastException if the user is not internal
	 */
	private void showConsultationsReservationPerUser() throws SQLException, ReservationException, ClassCastException{ 
		
		InternalUser user = (InternalUser) askAndFillUser();
		if(user!=null){
			List<ConsultationReservation> reservations = model.getConsultationReservationsByUserToday(user);
			
			consultationsView.setMenuItemStartEnabled(true);
			consultationsView.setMenuItemDeliveryEnabled(false);
			
			super.displayTableItems(new ConsultationReservationTableModel(reservations),
					consultationsView, view);
		}
	}
	
	
	// TODO WHY CONSULTATION IN PROGRESS IS SHOWN EVEN IF IT'S FINISHED.
	// ahah done
	private void addConsultationsListListeners(){
		
		super.addPopupListenerToTable(this.consultationsView);
		
		this.consultationsView.setPopupEnabled(true);
		
		this.consultationsView.addMenuItemDeliveryListener(new ActionListener(){

			// TODO set consultation delivery from the main button.
			// done
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// this is set here.
				action = new ActionDeliveryConsultation(model);
				
				Consultation arg = (Consultation) consultationsView.getSelectedItem();

				askConfirmationAndExecuteAction(arg);
				
			}
			
		});
		
		this.consultationsView.addMenuItemStartListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// action is set previously.
				
				ConsultationReservation arg = (ConsultationReservation) consultationsView.getSelectedItem();
				askConfirmationAndExecuteAction(arg);
			}
			
		});
	}
	
	
	private void addSearchableViewListeners(){
		
		this.searchableBookView.addActionListenerOk(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				searchableBookView.setVisible(false);
				
				Object [] res = searchableBookViewResults();
				Object [] args;
				
				if (isWithUser){
					User user = ((SearchableBookUserView)searchableBookView).getSelectedUser();
					args = ArrayUtils.addAll(new Object[]{user}, res);
				}else{
					args = res;
				}
				
				askConfirmationAndExecuteAction(args);
			}
			
			
		});
		
	}
	
	private void addLateLoansListeners(){
		
		super.addPopupListenerToTable(this.loansInLateView);
		
		this.loansInLateView.addMenuItemSendMailListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Loan loan = (Loan) loansInLateView.getSelectedItem();
				askConfirmationAndExecuteAction(loan);
			}
			
		});
	}
	
	private int askActionConfirmation(){
		return JOptionPane.showConfirmDialog(view, this.action.getConfirmationMessage(), "Conferma",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
	}
	
	private void showActionResult(){
		JOptionPane.showMessageDialog(view, this.action.getResultMessage(), "Info",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	private boolean[] askConfirmationAndExecuteAction(Object... args){
		boolean thrown = false;
		int ok = JOptionPane.OK_OPTION;
		if (action.askConfirmation()){
			ok = this.askActionConfirmation();
		}
		
		if (ok == JOptionPane.OK_OPTION){
			try {
				action.setArgs(args);
				action.execute();
				this.showActionResult();
			} catch (SQLException | ReservationException e) {
				displayError(view, e);
				thrown = true;
			}
		}
		return new boolean[]{ok == JOptionPane.OK_OPTION, thrown};
	}
	
	
	private void addUserViewListeners(){
		
		this.userView.addActionListenerOk(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				User user = userView.getUser();
				askConfirmationAndExecuteAction(user);
			}
			
		});
		
		this.userView.addActionListenerCancel(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				userView.setVisible(false);
				userView.reset();
			}
			
		});
	}
	
	
	private void addBookViewListeners(){
		
		super.addPopupListenerToTable(bookView);
		
		/**
		 * Listener called when the table 'Delete book' shows up
		 * and user right click -> delete.
		 */
		this.bookView.addMenuItemDeleteListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Book book = (Book) bookView.getSelectedItem();
				boolean [] done = askConfirmationAndExecuteAction(book);

				if (done[1] == true){
					bookView.setVisible(false);
				}
				
				if (done[0] && done[1] == false){
					/*
					 * After a book has been deleted, we fetch the
					 * remaining list of available-to-delete books.
					 */
					try {
						List<BookCopiesNumber> books = model.getDeletableBooks();
						if(books.size() > 0){
							// If there are books to show
							bookView.setTableModel(new BookCopiesNumberTableModel(books));
						}else{
							// else set visibility to false cause
							// there's nothing to show.
							bookView.setVisible(false);
							displayMessage(view, "Non ci sono altri libri cancellabili",
									"Info", JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (SQLException e) {
						displayError(view, e);
					}
					
				}
				
			}
			
		});
		
		
		this.bookView.addMenuItemIncrementCopiesNumberListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Book book = (Book) bookView.getSelectedItem();
				int itemsCount = bookView.getItemsCount();
				int difference = 0;
				
				boolean doAction = false;
				boolean forConsultation = false;
				
				difference = askNumber(view, "Inserisci il numero di copie che vuoi aggiungere");
				if(difference != Integer.MAX_VALUE){
					doAction = true;
				}
				
				// asking if they are for consultation
				int res = JOptionPane.showConfirmDialog(view, "Le copie sono per consultazione?",
						"Domanda", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				
				forConsultation = res == JOptionPane.YES_OPTION;
				
				Object [] args = {book, itemsCount, difference, forConsultation};
				
				action = new ActionAddCopies(model);
				
				if(doAction){
					boolean [] done = askConfirmationAndExecuteAction(args);
					
					// refreshing the list
					if (done[0] && !done[1]){
						
						try {
							displayBooks(action, null);
						} catch (SQLException e) {
							displayError(view, "Non è stato possibile refreshare la tabella", e);
							bookView.setVisible(false);
						}
						
					}
				}

			}
			
		});
		
		this.bookView.addMenuItemDecrementCopiesNumberListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Book book = (Book) bookView.getSelectedItem();
				int itemsCount = bookView.getItemsCount();
				int difference = 0;
				
				boolean doAction = false;
				
				difference = askNumber(view, "Inserisci il numero di copie che vuoi rimuovere");
				if(difference != Integer.MAX_VALUE){
					doAction = true;
				}
				
				Object [] args = {book, itemsCount, difference};
				
				action = new ActionDeleteCopies(model);
				
				if(doAction){
					// refreshing the list
					boolean [] done = askConfirmationAndExecuteAction(args);
					if (done[0] && !done[1]){
						
						try {
							// books with a copies number of zero are no longer shown.
							displayBooks(action, null);
						} catch (SQLException e) {
							displayError(view, "Non è stato possibile refreshare la tabella", e);
							bookView.setVisible(false);
						}
						
					}
				}

			}
			
		});
	}
	
	
	

}
