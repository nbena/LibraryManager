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


import com.github.nbena.librarymanager.core.User;
import com.github.nbena.librarymanager.gui.librarianint.AbstractActionWithUser;
import com.github.nbena.librarymanager.gui.librarianint.Action;
import com.github.nbena.librarymanager.gui.librarianint.ActionAddBook;
import com.github.nbena.librarymanager.gui.librarianint.ActionAddCopies;
import com.github.nbena.librarymanager.gui.librarianint.ActionAddUser;
import com.github.nbena.librarymanager.gui.librarianint.ActionCleanup;
import com.github.nbena.librarymanager.gui.librarianint.ActionDeleteBook;
import com.github.nbena.librarymanager.gui.librarianint.ActionDeleteCopies;
import com.github.nbena.librarymanager.gui.librarianint.ActionDeliveryConsultation;
import com.github.nbena.librarymanager.gui.librarianint.ActionDeliveryLoan;
import com.github.nbena.librarymanager.gui.librarianint.ActionGetAvailableCopiesForConsultation;
import com.github.nbena.librarymanager.gui.librarianint.ActionGetAvailableCopiesForLoan;
import com.github.nbena.librarymanager.gui.librarianint.ActionNewNotReservedConsultation;
import com.github.nbena.librarymanager.gui.librarianint.ActionNewNotReservedLoan;
import com.github.nbena.librarymanager.gui.librarianint.ActionNewReservedConsultation;
import com.github.nbena.librarymanager.gui.librarianint.ActionNewReservedLoan;
import com.github.nbena.librarymanager.gui.librarianint.ActionSendMail;
import com.github.nbena.librarymanager.gui.view.LibrarianGenericTableView;
import com.github.nbena.librarymanager.gui.view.BookTableView;
import com.github.nbena.librarymanager.gui.view.LibrarianView;
import com.github.nbena.librarymanager.gui.view.LoansInLateView;
import com.github.nbena.librarymanager.gui.view.RegisterUserView;
import com.github.nbena.librarymanager.gui.view.SearchableBookView;
import com.github.nbena.librarymanager.gui.view.SeatsSitutationTableView;
import com.github.nbena.librarymanager.gui.view.table.BookCopiesNumberTableModel;
import com.github.nbena.librarymanager.gui.view.table.BookTableModel;
import com.github.nbena.librarymanager.gui.view.table.ConsultationsInProgressTableModel;
import com.github.nbena.librarymanager.gui.view.table.ConsultationReservationTableModel;
import com.github.nbena.librarymanager.gui.view.table.ConsultationsInProgressByUserTableModel;
import com.github.nbena.librarymanager.gui.view.table.LoanReservationTableModel;
import com.github.nbena.librarymanager.gui.view.table.LoanTableModel;
import com.github.nbena.librarymanager.gui.view.table.LoansInLateTableModel;
import com.github.nbena.librarymanager.gui.view.table.StudyTableModel;
import com.github.nbena.librarymanager.core.Book;
import com.github.nbena.librarymanager.core.BookCopiesNumber;
import com.github.nbena.librarymanager.core.Consultation;
import com.github.nbena.librarymanager.core.ConsultationReservation;
import com.github.nbena.librarymanager.core.Copy;
import com.github.nbena.librarymanager.core.CopyForConsultation;
import com.github.nbena.librarymanager.core.InternalUser;
import com.github.nbena.librarymanager.core.Loan;
import com.github.nbena.librarymanager.core.LoanReservation;
import com.github.nbena.librarymanager.core.LibraryManagerException;
import com.github.nbena.librarymanager.core.Study;

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
	private LibrarianGenericTableView genericBooksListView;
	private LoansInLateView loansInLateView;
	private BookTableView bookView;
	
	// private boolean isWithUser = false;
	
	private static final String TITLE_NEW_NOT_RESERVED_LOAN = "Nuovo prestito non prenotato";
	// private static final String TITLE_NEW_RESERVED_LOAN = "Nuovo prestito prenotato";
	// private static final String TITLE_REGISTER_LOAN_DELIVERY = "Registra consegna prestito";
	private static final String TITLE_VIEW_LOANS_IN_LATE = "Prestiti in ritardo";
	private static final String TITLE_REGISTER_USER = "Nuovo utente";
	private static final String TITLE_NEW_NOT_RESERVED_CONSULTATION = "Nuova consultazione non prenotata";
	// private static final String TITLE_NEW_RESERVED_CONSULTATION = "Nuova consultazione prenotata";
	private static final String TITLE_VIEW_CONSULTATION_RESERVATIONS = "Consultazioni prenotate per utente";
	// private static final String TITLE_REGISTER_CONSULTATION_DELIVERY = "Registra consegna consultazione";
	private static final String TITLE_VIEW_CONSULTATIONS_IN_PROGRESS = "Consultazioni in corso";
	private static final String TITLE_ADD_BOOK = "Nuovo libro";
	private static final String TITLE_CHANGE_COPIES_NUMBER = "Modifica numero copie";
	private static final String TITLE_DELETE_BOOK = "Elimina libro";
	private static final String TITLE_VIEW_LOAN_RESERVATIONS = "Prestiti prenotati";
	private static final String TITLE_VIEW_LOANS = "Prestiti in corso";
	
	private static final String TITLE_AVAILABLE_COPIES_LOANS = "Copie per prestito";
	private static final String TITLE_AVAILABLE_COPIES_CONSULTATIONS = "Copie per consultazioni";
	
	private static final String TITLE_VIEW_SEATS_SITUTATION = "Situazione aula studio";
	
	
//	private void showWithUsersView(/*boolean withUsers, */String title) throws SQLException{
//		// if (withUsers){
//		//	((SearchableBookUser)this.searchableBookView).setUsers(this.model.users());
//		// }else{
//			((SearchableBookUser)this.searchableBookView).setUserPanelEnabled(false);
//		// }
//		this.isWithUser = /*withUsers;*/false;
//		this.searchableBookView.setMainTitle(title);
//		this.searchableBookView.setVisible(true);
//	}
	
	/**
	 * This method replaces the old showWithUserView with a better flow:
	 * 	- the librarian insert the user email
	 *  - then it inserts the book
	 * @param title
	 */
	private void showAskUserBook(String title){
		User u = null;
		
		try {
			u = askAndFillUser();
		} catch (SQLException | LibraryManagerException e1) {
			displayError(view, e1);
		}
		
		if(u != null){
			((AbstractActionWithUser) action).setUser(u);
			/*searchableBookView = new SearchableBookView();*/
			this.searchableBookView.reset();
			this.searchableBookView.setMainTitle(title);
			this.searchableBookView.setVisible(true);
		}
	}
	
	
	
	public LibrarianController(LibrarianModel model, LibrarianView view) {
		this.model = model;
		this.view = view;
		
		// this.searchableBookView = new SearchableBookUserView();
		this.searchableBookView = new SearchableBookView();
		this.userView = new RegisterUserView();
		this.genericBooksListView = new LibrarianGenericTableView();
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
					
				action = new ActionGetAvailableCopiesForLoan(model);
				showAskUserBook(TITLE_NEW_NOT_RESERVED_LOAN);
			}
		});
		
		this.view.addActionListenerNewReservedLoan(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent arg0){
				try {

					action = new ActionNewReservedLoan(model);
					// showWithUsersView(true, TITLE_NEW_RESERVED_LOAN);
					showLoanReservationsPerUser();
				} catch (SQLException | LibraryManagerException e) {
					displayError(view, e);
				} catch (ClassCastException e1){
					LibraryManagerException ex = new LibraryManagerException("Qualcosa è andato storto", e1);
					displayError(view, ex);
				}
			}
		});
		
		this.view.addActionListenerDeliveryLoan(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					action = new ActionDeliveryLoan(model);
					// showWithUsersView(true, TITLE_REGISTER_LOAN_DELIVERY);
					showLoansInProgressPerUser();
				} catch (SQLException | LibraryManagerException e1) {
					displayError(view, e1);
				}catch (ClassCastException e1){
					LibraryManagerException ex = new LibraryManagerException("Qualcosa è andato storto", e1);
					displayError(view, ex);
				}
			}
			
		});
		
		this.view.addActionListenerNewNotReservedConsultation(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
					
				action = new ActionGetAvailableCopiesForConsultation(model);
				showAskUserBook(TITLE_NEW_NOT_RESERVED_CONSULTATION);
			}
			
		});
		
		this.view.addActionListenerNewReservedConsultation(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					action = new ActionNewReservedConsultation(model);
					showConsultationsReservationPerUser();
				} catch (SQLException | LibraryManagerException e1) {
					displayError(view, e1);
				} catch (ClassCastException e1){
					LibraryManagerException ex = new LibraryManagerException("Qualcosa è andato storto", e1);
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
				} catch (LibraryManagerException e1) {
					displayError(view, e1);
				}
			}
			
		});
		
		this.view.addActionListenerAddUser(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				userView.setVisible(true);
				userView.setMainTitle(TITLE_REGISTER_USER);
				action = new ActionAddUser(model);
			}
			
		});
		
		this.view.addActionListenerAddBook(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
//				try {
					action = new ActionAddBook(model);
//					showWithUsersView(/*false, */TITLE_ADD_BOOK);
					searchableBookView = new SearchableBookView();
					searchableBookView.setMainTitle(TITLE_ADD_BOOK);
					searchableBookView.setVisible(true);
//				} catch (SQLException e1) {
//					displayError(view, e1);
//				}
			}
			
		});
		
		this.view.addActionListenerDeleteBook(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				try{
					action = new ActionDeleteBook(model);
					List<BookCopiesNumber> books = model.getDeletableBooks();
					displayBooks(action, books, TITLE_DELETE_BOOK);
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
					displayBooks(new ActionAddCopies(model), null, TITLE_CHANGE_COPIES_NUMBER);
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
					
					genericBooksListView.setItemsToConsultation();
					
					genericBooksListView.setMenuItemDeliveryEnabled(false);
					genericBooksListView.setMenuItemStartEnabled(false);
					
					displayTableItems(new ConsultationsInProgressTableModel(consultations),
							genericBooksListView, view, TITLE_VIEW_CONSULTATIONS_IN_PROGRESS);
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
							loansInLateView, view, TITLE_VIEW_LOANS_IN_LATE);
					action = new ActionSendMail(model);
				}catch(SQLException e1){
					displayError(view, e1);
				}
				
			}
			
		});
		
		this.view.addActionListenerViewSeatsSituation(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				showSeatsSituation();
			}
			
		});
		
		this.view.addActionListenerCleanup(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				action = new ActionCleanup(model);
				askConfirmationAndExecuteAction(action, view, new Object[]{});
			}
			
		});
	}
	
	private void showSeatsSituation(){
		try{
			List<Study> studies = model.getStudies();
			
			// this.genericBooksListView.setMenuItemDeliveryEnabled(false);
			// this.genericBooksListView.setMenuItemStartEnabled(false);
			SeatsSitutationTableView tableView = new SeatsSitutationTableView();
			
			displayTableItems(new StudyTableModel(studies),
					/* stil using this table view... */
					tableView, view, TITLE_VIEW_SEATS_SITUTATION);
		}catch(SQLException e1){
			displayError(view, e1);
		}
	}

	private String askUser() {
		return JOptionPane.showInputDialog(view,
				"Inserisci la mail dell'uente", "Info", JOptionPane.QUESTION_MESSAGE);
	}
	
	private User fillUser(String email) throws SQLException, LibraryManagerException{
		User u = null;
		/* need to check it's not null because it's called afeter askUser() */
		if (email != null && (email != "" && ! email.trim().equals(""))){
			u = this.model.getUser(email);
		}
		return u;
	}
	
	private User askAndFillUser() throws SQLException, LibraryManagerException{
		String email = this.askUser();
		return this.fillUser(email);
	}
	
	/**
	 * This method fetches the list of BookCopiesNumber and displays them.
	 * The <pre>MenuItem</pre>(s) are properly enabled basing on the current
	 * action.
	 * 
	 * At the end it shows up <pre>this.bookView</pre>.
	 * @param action the Action you want to do. We do not use the attribute because
	 * in some cases the action is set after then a call to this function.
	 * @param books if null, a list will be loaded with <pre>model.books()</pre>
	 * @param title	the title to set to <pre>bookView</pre>
	 * @throws SQLException
	 */
	private void displayBooks(Action action, List<BookCopiesNumber> books, String title) throws SQLException{
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
				bookView, view, title);
	}

	private void showConsultationsPerUser() throws SQLException, LibraryManagerException{
		User user = askAndFillUser();

		if (user!=null){
			List<Consultation> consultations = model.consultations(user);
			
			genericBooksListView.setItemsToConsultation();
			
			genericBooksListView.setMenuItemDeliveryEnabled(true);
			genericBooksListView.setMenuItemStartEnabled(false);
			
			super.displayTableItems(new ConsultationsInProgressByUserTableModel(consultations),
					genericBooksListView, view, TITLE_VIEW_CONSULTATIONS_IN_PROGRESS);
		}
	}
	
	/**
	 * Ask the librarian to insert the user email, then it fills up
	 * the user object, then it gets all the consultations reservations
	 * that that user has for today.
	 * @throws SQLException
	 * @throws LibraryManagerException
	 * @throws ClassCastException if the user is not internal
	 */
	private void showConsultationsReservationPerUser() throws SQLException, LibraryManagerException, ClassCastException{ 
		
		InternalUser user = (InternalUser) askAndFillUser();
		if(user!=null){
			List<ConsultationReservation> reservations = model.getConsultationReservationsByUserToday(user);
			
			genericBooksListView.setItemsToConsultation();
			
			genericBooksListView.setMenuItemStartEnabled(true);
			genericBooksListView.setMenuItemDeliveryEnabled(false);
			
			super.displayTableItems(new ConsultationReservationTableModel(reservations),
					genericBooksListView, view, TITLE_VIEW_CONSULTATION_RESERVATIONS);
		}
	}
	
	private void showLoanReservationsPerUser() throws SQLException, LibraryManagerException{
		InternalUser user = (InternalUser) askAndFillUser();
		if (user!=null){
			List<LoanReservation> reservations = model.getLoanReservationsByUser(user);
			
			genericBooksListView.setItemsToLoan();
			
			genericBooksListView.setMenuItemStartEnabled(true);
			genericBooksListView.setMenuItemDeliveryEnabled(false);
			
			super.displayTableItems(new LoanReservationTableModel(reservations),
					genericBooksListView, view, TITLE_VIEW_LOAN_RESERVATIONS);
		}
	}
	
	private void showLoansInProgressPerUser() throws SQLException, LibraryManagerException{
		InternalUser user = (InternalUser) askAndFillUser();
		if (user!=null){
			List<Loan> loans = model.getLoansInProgressByUser(user);
			
			genericBooksListView.setItemsToLoan();
			
			genericBooksListView.setMenuItemDeliveryEnabled(true);
			genericBooksListView.setMenuItemStartEnabled(false);
			
			super.displayTableItems(new LoanTableModel(loans),
					genericBooksListView, view, TITLE_VIEW_LOANS);
		}
	}
	
	
	
	
	/**
	 * Listeners to out generic view used to start a new reserved consultation/loan
	 * and delivery consultation/loan.
	 */
	private void addConsultationsListListeners(){
		
		super.addPopupListenerToTable(this.genericBooksListView);
		
		this.genericBooksListView.setPopupEnabled(true);
		
		this.genericBooksListView.addMenuItemDeliveryListener(new ActionListener(){


			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// this is set here.
				Object arg = genericBooksListView.getSelectedItem();
				// action = new ActionDeliveryConsultation(model);
				
				// Consultation arg = (Consultation) consultationsView.getSelectedItem();

				AbstractController.askConfirmationAndExecuteAction(
						action, view, arg);
				
			}
			
		});
		
		/**
		 * 
		 */
		this.genericBooksListView.addMenuItemStartListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// action is set previously.
				// Object arg = null;
//				if (action instanceof ActionNewReservedConsultation){
//					arg = (ConsultationReservation) consultationsView.getSelectedItem();				
//				}else if (action instanceof ActionNewReservedLoan){
//					arg = (LoanReservation) consultationsView.getSelectedItem();
//				}
				Object arg = genericBooksListView.getSelectedItem();
				boolean [] done = AbstractController.askConfirmationAndExecuteAction(
						action, view, arg);
				if (done[0]){
					genericBooksListView.setVisible(false);
				}
			}
			
		});
	}
	
	
	private void addSearchableViewListeners(){
		
		this.searchableBookView.addActionListenerOk(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				searchableBookView.setVisible(false);
				
				Object [] res = searchableBookViewResults();
				// Object [] args;
				
				// if (isWithUser){
				//	User user = ((SearchableBookUserView)searchableBookView).getSelectedUser();
				//	args = ArrayUtils.addAll(new Object[]{user}, res);
				// }else{
				//	args = res;
				// }
				
				action.setArgs(res);
				try {
					action.execute();
					User old = ((AbstractActionWithUser) action).getUser();
					
					if(action instanceof ActionGetAvailableCopiesForLoan){
						@SuppressWarnings("unchecked")
						List<Copy> result = (List<Copy>) action.getResult();
						action = new ActionNewNotReservedLoan(model);
						((ActionNewNotReservedLoan) action).setUser(old);
						showAvailableCopiesForLoan(result);
					}else if(action instanceof ActionGetAvailableCopiesForConsultation){
						@SuppressWarnings("unchecked")
						List<CopyForConsultation> result = (List<CopyForConsultation>) action.getResult();
						action = new ActionNewNotReservedConsultation(model);
						((ActionNewNotReservedConsultation) action).setUser(old);
						showAvailableCopiesForConsultation(result);
					}
					
				} catch (SQLException | LibraryManagerException e) {
					displayError(view, e);
				}
				
				
				// askConfirmationAndExecuteAction(args);
			}
			
			
		});
		
	}
	
	private void showAvailableCopiesForConsultation(List<CopyForConsultation> copies){
		this.genericBooksListView.setItemsToLoan();
		
		this.genericBooksListView.setMenuItemDeliveryEnabled(false);
		this.genericBooksListView.setMenuItemStartEnabled(true);
		
		this.genericBooksListView.setItemsToConsultation();
		
		super.displayTableItems(new BookTableModel(copies),
				genericBooksListView, view, TITLE_AVAILABLE_COPIES_CONSULTATIONS);
	}
	
	private void showAvailableCopiesForLoan(List<Copy> copies){
		this.genericBooksListView.setItemsToLoan();
		
		this.genericBooksListView.setMenuItemDeliveryEnabled(false);
		this.genericBooksListView.setMenuItemStartEnabled(true);
		
		this.genericBooksListView.setItemsToLoan();
		
		super.displayTableItems(new BookTableModel(copies),
				genericBooksListView, view, TITLE_AVAILABLE_COPIES_LOANS);
	}
	
	private void addLateLoansListeners(){
		
		super.addPopupListenerToTable(this.loansInLateView);
		
		this.loansInLateView.addMenuItemSendMailListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Loan loan = (Loan) loansInLateView.getSelectedItem();
				AbstractController.askConfirmationAndExecuteAction(action, view, loan);
			}
			
		});
	}
	
//	public static int askActionConfirmation(Action action, JComponent view){
//		return JOptionPane.showConfirmDialog(view, action.getConfirmationMessage(), "Conferma",
//				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
//	}
//	
//	public static void showActionResult(Action action, JComponent view){
//		JOptionPane.showMessageDialog(view, action.getResultMessage(), "Info",
//				JOptionPane.INFORMATION_MESSAGE);
//	}
	
//	private boolean[] askConfirmationAndExecuteAction(Object... args){
//		boolean thrown = false;
//		int ok = JOptionPane.OK_OPTION;
//		if (action.askConfirmation()){
//			ok = this.askActionConfirmation();
//		}
//		
//		if (ok == JOptionPane.OK_OPTION){
//			try {
//				if (args != null){
//					action.setArgs(args);
//				}
//				action.execute();
//				this.showActionResult();
//			} catch (SQLException | ReservationException e) {
//				displayError(view, e);
//				thrown = true;
//			}
//		}
//		return new boolean[]{ok == JOptionPane.OK_OPTION, thrown};
//	}
	
	
	private void addUserViewListeners(){
		
		this.userView.addActionListenerOk(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				User user = userView.getUser();
				if (user != null){
					boolean [] done = AbstractController.askConfirmationAndExecuteAction(
							action, userView, user);
					if(done[0]){
						userView.setVisible(false);
					}
					userView.reset();	
				}
			}
			
		});
		
//		this.userView.addActionListenerCancel(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				userView.setVisible(false);
//				userView.reset();
//			}
//			
//		});
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
				boolean [] done = AbstractController.askConfirmationAndExecuteAction(
						action, view, book);

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
					boolean [] done = AbstractController.askConfirmationAndExecuteAction(
							action, view, args);
					
					// refreshing the list
					if (done[0] && !done[1]){
						
						try {
							displayBooks(action, null, TITLE_CHANGE_COPIES_NUMBER);
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
					boolean [] done = AbstractController.askConfirmationAndExecuteAction(
							action, view, args);
					if (done[0] && !done[1]){
						
						try {
							// books with a copies number of zero are no longer shown.
							displayBooks(action, null, TITLE_CHANGE_COPIES_NUMBER);
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
