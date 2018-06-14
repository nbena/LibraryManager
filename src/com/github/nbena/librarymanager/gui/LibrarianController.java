package com.github.nbena.librarymanager.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.github.nbena.librarymanager.core.User;
import com.github.nbena.librarymanager.gui.view.LibrarianView;
import com.github.nbena.librarymanager.gui.view.SearchableBookUserView;
import com.github.nbena.librarymanager.gui.view.SearchableBookUser;

public class LibrarianController extends AbstractController {
	
	private LibrarianModel model;
	private LibrarianView view;
	
	private void showWithUsersView(boolean withUsers) throws SQLException{
		if (withUsers){
			((SearchableBookUser)this.searchableBookView).setUsers(this.model.users());
		}
		this.searchableBookView.setVisible(true);
	}
	
	
	private User askUser() throws SQLException{
		String user = JOptionPane.showInputDialog(this.view,
				"Inserisci la mail dell'utente", "Utente", JOptionPane.QUESTION_MESSAGE);
		
		User asked = null;
		if (user!= null && !user.equals("")){
			asked = this.model.getUserFromEmail(user);
		}
		
		return asked;
	}
	
	
	public LibrarianController(LibrarianModel model, LibrarianView view) {
		this.model = model;
		this.view = view;
		
		this.searchableBookView = new SearchableBookUserView();
		
		this.addListeners();
		
		this.view.setVisible(true);
	}
	
	
	private void addListeners(){
		this.addListenersToMainView();
		this.addSearchableViewListeners();
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
				} catch (SQLException e) {
					displayError(view, e);
				}
			}
		});
		
		this.view.addActionListenerNewReservedLoan(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent arg0){
				try {
					User user = askUser();
					searchableBookView.reset();
					searchableBookView.setVisible(true);
				} catch (SQLException e) {
					displayError(view, e);
				}
			}
		});
		
		this.view.addActionListenerDeliveryLoan(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		});
		
		this.view.addActionListenerNewNotReservedConsultation(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					showWithUsersView(true);
				} catch (SQLException e1) {
					displayError(view, e1);
				}
			}
			
		});
		
		this.view.addActionListenerNewReservedConsultation(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		});
		
		this.view.addActionListenerDeliveryConsultation(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		});
		
		this.view.addActionListenerAddUser(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		});
		
		this.view.addActionListenerAddBook(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					showWithUsersView(false);
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
				} catch (SQLException e1) {
					displayError(view, e1);
				}
			}
			
		});
	}
	
	
	private void addSearchableViewListeners(){
		
		this.searchableBookView.addListenerOk(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				searchableBookView.setVisible(false);
				
				Object [] res = searchableBookViewResults();
				String title = (String) res[0];
				String [] authors = (String[]) res[1];
				int year = (int) res[2];
				String topic = (String) res[3];
				
			}
			
			
		});
		
		this.searchableBookView.addListenerCancel(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				searchableBookView.setVisible(false);
				
			}
			
		});
	}
	
	
	

}
