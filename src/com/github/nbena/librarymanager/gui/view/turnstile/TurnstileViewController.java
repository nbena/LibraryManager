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

package com.github.nbena.librarymanager.gui.view.turnstile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.github.nbena.librarymanager.core.Seat;
import com.github.nbena.librarymanager.core.User;
import com.github.nbena.librarymanager.core.turnstile.Turnstile;
import com.github.nbena.librarymanager.gui.AbstractController;
import com.github.nbena.librarymanager.gui.StartupLogin;
import com.github.nbena.librarymanager.man.LibraryManager;
import com.github.nbena.librarymanager.utils.Hash;

public class TurnstileViewController {
	
	private LibraryManager manager;
	private TurnstileView view;
	
	private Turnstile turnstile;
	
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
	
	private void showSeatOrNothing(Seat seat){
		if(seat != null){
			AbstractController.displayMessage(view,
					turnstile.showSeat(seat),
					"Messagggio",
					JOptionPane.INFORMATION_MESSAGE
					);
			this.view.open();
		}else{
			AbstractController.displayMessage(view,
					turnstile.showNoSeats(),
					"Messaggio",
					JOptionPane.INFORMATION_MESSAGE
					);
		}
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
		
		this.turnstile = new Turnstile(this.manager);
			
		this.addEnterExitListeners();
		this.addPassListeners();
		
		this.view.setVisible(true);
	}
	
	private void setMainButtonEnabled(boolean enabled){
		view.setButtonExitEnabled(enabled);
		view.setButtonEnterEnabled(enabled);
	}
	
	// user arrives with no reservations: works.
	private void addEnterExitListeners(){
		/**
		 * when user pushed 'enter' or 'exit' they're all disabled
		 * till it passes
		 */
		this.view.addActionListenerEnterButton(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setMainButtonEnabled(false);

				User user = getUserOrError();
				if(user!=null){
					try {
						Seat seat = turnstile.userArrive(user);
						showSeatOrNothing(seat);
					} catch (Exception e) {
						AbstractController.displayError(view, e);
						setMainButtonEnabled(true);
					}
				}else{
					setMainButtonEnabled(true);
				}
				
			}
			
		});
		
		
		this.view.addActionListenerExitButton(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				setMainButtonEnabled(false);

				User user = getUserOrError();
				if(user!=null){
					view.open();
				}else{
					setMainButtonEnabled(true);
				}
			}
			
		});
	}
	
	private void addPassListeners(){
		this.view.addActionListenerPassedButton(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				view.close();
				turnstile.userPass();

				setMainButtonEnabled(true);
			}
			
		});
	}

}
