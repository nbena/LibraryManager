package com.github.nbena.librarymanager.gui.view.turnstile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.github.nbena.librarymanager.core.User;
import com.github.nbena.librarymanager.gui.AbstractController;
import com.github.nbena.librarymanager.gui.StartupLogin;
import com.github.nbena.librarymanager.man.LibraryManager;
import com.github.nbena.librarymanager.utils.Hash;

public class TurnstileViewController {
	
	private LibraryManager manager;
	private TurnstileView view;
	
	private User getCredential() throws SQLException{
		StartupLogin login = new StartupLogin();
		String [] cred = login.getCredentials("Inserisci email e password");
		User user = null;
		if (cred != null){
			user = new User();
			user.setEmail(cred[0]);
			user.setHashedPassword(Hash.hash(cred[1]));
			user = manager.authenticateUserWithError(user);
		}else{
			user = null;
		}
		return user;
	}
	
	private User getUserOrError(){
		User user = null;
		try {
			user = this.getCredential();
			if (user == null){
				AbstractController.displayMessage(view,
						"Non è stato trovato nessun match",
						"Errore",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e) {
			AbstractController.displayError(view, e);
		}
		return user;
	}
	
	public TurnstileViewController(LibraryManager manager, TurnstileView view){
		this.manager = manager;
		this.view = view;
		
		this.view.setMainTitle("Tornello biblioteca");
			
		this.addEnterExitListeners();
		this.addPassListeners();
		
		this.view.setVisible(true);
	}
	
	private void addEnterExitListeners(){
		this.view.addActionListenerEnterButton(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				User user = getUserOrError();
				if(user!=null){
					
				}
				
			}
			
		});
		
		this.view.addActionListenerExitButton(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				User user = getUserOrError();
				if(user!=null){
					view.open();
				}
				
			}
			
		});
	}
	
	private void addPassListeners(){
		this.view.addActionListenerPassedButton(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				view.close();
				
			}
			
		});
	}

}
