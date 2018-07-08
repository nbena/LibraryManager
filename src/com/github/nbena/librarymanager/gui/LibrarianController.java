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
import com.github.nbena.librarymanager.gui.librarianint.ActionDeliveryConsultation;
import com.github.nbena.librarymanager.gui.librarianint.ActionDeliveryLoan;
import com.github.nbena.librarymanager.gui.librarianint.ActionNewNotReservedConsultation;
import com.github.nbena.librarymanager.gui.librarianint.ActionNewNotReservedLoan;
import com.github.nbena.librarymanager.gui.librarianint.ActionNewReservedConsultation;
import com.github.nbena.librarymanager.gui.librarianint.ActionNewReservedLoan;
import com.github.nbena.librarymanager.gui.view.ConsultationInProgressView;
import com.github.nbena.librarymanager.gui.view.LibrarianView;
import com.github.nbena.librarymanager.gui.view.RegisterUserView;
import com.github.nbena.librarymanager.gui.view.SearchableBookUserView;
import com.github.nbena.librarymanager.gui.view.table.ConsultationInProgressTableModel;
import com.github.nbena.librarymanager.gui.view.SearchableBookUser;
import com.github.nbena.librarymanager.core.Consultation;
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
	 * This pattern is only followed when there is a view with more possible actions,
	 * otherwise it is a unnecessary complication.
	 */
	
	private LibrarianModel model;
	private LibrarianView view;
	
	private Action action;
	
	private RegisterUserView userView;
	private ConsultationInProgressView consultationsView;
	
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
		this.consultationsView = new ConsultationInProgressView();
		
		this.addListeners();
		
		this.view.setVisible(true);
	}
	
	
	private void addListeners(){
		this.addListenersToMainView();
		this.addSearchableViewListeners();
		this.addUserViewListeners();
		this.addConsultationsListListeners();
	}
	
	
	private void addListenersToMainView(){
		
		this.view.addActionListenerNewNotReservedLoan(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent arg0){
				try {
//					User user = askUser();
//					searchableBookView.reset();
//					searchableBookView.setVisible(true);
					showWithUsersView(true);
					action = new ActionNewNotReservedLoan(model);
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
					showWithUsersView(true);
					// action = new ActionNewReservedLoan(model);
					action = new ActionNewReservedLoan(model);
				} catch (SQLException e) {
					displayError(view, e);
				}
			}
		});
		
		this.view.addActionListenerDeliveryLoan(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					showWithUsersView(true);
					action = new ActionDeliveryLoan(model);
				} catch (SQLException e1) {
					displayError(view, e1);
				}
			}
			
		});
		
		this.view.addActionListenerNewNotReservedConsultation(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					showWithUsersView(true);
					action = new ActionNewNotReservedConsultation(model);
				} catch (SQLException e1) {
					displayError(view, e1);
				}
			}
			
		});
		
		this.view.addActionListenerNewReservedConsultation(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					showWithUsersView(true);
					action = new ActionNewReservedConsultation(model);
				} catch (SQLException e1) {
					displayError(view, e1);
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
			}
			
		});
		
		this.view.addActionListenerAddBook(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					showWithUsersView(false);
					// TODO this is different
				} catch (SQLException e1) {
					displayError(view, e1);
				}
			}
			
		});
		
		this.view.addActionListenerDeleteBook(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					showWithUsersView(false);
					// TODO this is different
				} catch (SQLException e1) {
					displayError(view, e1);
				}
			}
			
		});
		
		this.view.addActionListenerChangeCopiesNumber(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					showWithUsersView(false);
					// TODO this is different
				} catch (SQLException e1) {
					displayError(view, e1);
				}
			}
			
		});
	}

	private User askUser() throws SQLException, ReservationException{
		User user = null;
		String email = JOptionPane.showInputDialog(view,
				"Inserisci la mail dell'uente", "Info", JOptionPane.QUESTION_MESSAGE);
		
		if (email != null && !email.equals("")){
			user = this.model.fillUser(email);
		}
		return user;
	}

	private void showConsultationsPerUser() throws SQLException, ReservationException{
		User user = askUser();

		if (user!=null){
			// now showing all the in progress consultation
			// for this user.

			List<Consultation> consultations = model.consultations(user);
			
//			if (consultations.size()>0){
//				consultationsView.setTableModel(new ConsultationInProgressTableModel(consultations));
//				consultationsView.setVisible(true);
//			}else{
//				super.displayNoItemsToShow(view);
//			}
			super.displayTableItems(new ConsultationInProgressTableModel(consultations),
					consultationsView, view);
		}
	}
	
	private void addConsultationsListListeners(){
		
		super.addPopupListenerToTable(this.consultationsView);
		
		this.consultationsView.setPopupEnabled(true);
		
		this.consultationsView.addMenuItemDeliveryListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Consultation arg = (Consultation) consultationsView.getSelectedItem();
				
				// initialize now to avoid warning.
				int ok = JOptionPane.OK_OPTION;
				if(action.askConfirmation()){
					ok = JOptionPane.showConfirmDialog(view, action.getConfirmationMessage(), "Info",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				}
				
				if (ok == JOptionPane.OK_OPTION){
					action.setArgs(arg);
					try {
						action.execute();
						displayMessage(view, action.getResultMessage(), null, 0);
						consultationsView.setVisible(false);
					} catch (SQLException | ReservationException e) {
						displayError(view, e);
					}
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
				Object [] args;
				
				if (isWithUser){
					User user = ((SearchableBookUserView)searchableBookView).getSelectedUser();
					args = ArrayUtils.addAll(new Object[]{user}, res);
				}else{
					args = res;
				}
				
				
				
				// set default to true so we can avoid a nesting level
				int userOk = JOptionPane.OK_OPTION;
				if (action.askConfirmation()){
					userOk = JOptionPane.showConfirmDialog(view, action.getConfirmationMessage(), "Info",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				}
				
				if (userOk == JOptionPane.OK_OPTION){
					action.setArgs(args);
					try{
						action.execute();
						displayMessage(view, action.getResultMessage(), null, 0);
					}catch(SQLException | ReservationException e){
						displayError(view, e);
					}
				}
			}
			
			
		});
		
//		this.searchableBookView.addActionListenerCancel(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				
//				searchableBookView.setVisible(false);
//				
//			}
//			
//		});
	}
	
	
	private void addUserViewListeners(){
		
		this.userView.addActionListenerOk(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				User user = userView.getUser();
				try {
					model.addUser(user);
					userView.setVisible(false);
					userView.reset();
				} catch (SQLException e) {
					displayError(view, e);
				}
				
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
	
	
	

}
