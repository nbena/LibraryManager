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

import javax.swing.JOptionPane;

import org.apache.commons.lang3.ArrayUtils;

import com.github.nbena.librarymanager.core.User;
import com.github.nbena.librarymanager.gui.librarianint.Action;
import com.github.nbena.librarymanager.gui.librarianint.ActionDeliveryLoan;
import com.github.nbena.librarymanager.gui.librarianint.ActionNewNotReservedConsultation;
import com.github.nbena.librarymanager.gui.librarianint.ActionNewNotReservedLoan;
import com.github.nbena.librarymanager.gui.librarianint.ActionNewReservedConsultation;
import com.github.nbena.librarymanager.gui.librarianint.ActionNewReservedLoan;
import com.github.nbena.librarymanager.gui.view.LibrarianView;
import com.github.nbena.librarymanager.gui.view.RegisterUserView;
import com.github.nbena.librarymanager.gui.view.SearchableBookUserView;
import com.github.nbena.librarymanager.gui.view.SearchableBookUser;
import com.github.nbena.librarymanager.core.ReservationException;

public class LibrarianController extends AbstractController {
	
	private LibrarianModel model;
	private LibrarianView view;
	
	private Action action;
	
	private RegisterUserView userView;
	
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
		
		this.addListeners();
		
		this.view.setVisible(true);
	}
	
	
	private void addListeners(){
		this.addListenersToMainView();
		this.addSearchableViewListeners();
		this.addUserViewListeners();
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
					showWithUsersView(true);
					// TODO this is different
				} catch (SQLException e1) {
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
		
		this.searchableBookView.addListenerCancel(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				searchableBookView.setVisible(false);
				
			}
			
		});
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
