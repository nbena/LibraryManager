package com.github.nbena.librarymanager.gui.view;
import javax.swing.JLabel;

import com.github.nbena.librarymanager.core.User;

import java.util.List;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class SearchableBookUserView extends SearchableBookView implements SearchableBookUser {
	
	
	private JComboBox<User> comboBoxUser;
	private JLabel lblUser;
	
	public User getSelectedUser(){
		return (User) this.comboBoxUser.getSelectedItem();
	}
	
	public void setUsers(List<User> users){
		for (User u: users){
			this.comboBoxUser.addItem(u);
		}
	}
	
	public void setUserPanelEnabled(boolean enabled){
		this.comboBoxUser.setEnabled(enabled);
		this.comboBoxUser.setVisible(enabled);
		
		this.lblUser.setVisible(enabled);
	}
	
	
	public SearchableBookUserView() {
		
		super.setBounds(100, 100, 450, 261);
		
		buttonPane.setLocation(0, 186);
		
		lblUser = new JLabel("Utente");
		lblUser.setBounds(12, 150, 55, 14);
		contentPanel.add(lblUser);
		
		comboBoxUser = new JComboBox<User>();
		comboBoxUser.setBounds(136, 151, 292, 23);
		contentPanel.add(comboBoxUser);
	}
}
