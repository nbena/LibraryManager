package com.github.nbena.librarymanager.gui;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class StartupLogin {
	
	private JPanel panel;
	private JTextField email;
	private JPasswordField password;
	
	public StartupLogin(){
		
		this.email = new JTextField(25);
		this.password = new JPasswordField(25);
		
		this.panel = new JPanel();
		
		this.panel.add(new JLabel("Username: "));
		this.panel.add(email);
		
		this.panel.add(Box.createHorizontalStrut(15));
		
		this.panel.add(new JLabel("Password: "));
		this.panel.add(password);
		
		
	}
	
	public String[] getCredentials(){
		String [] result = null;
		int res = JOptionPane.showConfirmDialog(null, this.panel,
				"Inserisci le tue credenziali: ", JOptionPane.OK_CANCEL_OPTION);
		if (res == JOptionPane.OK_OPTION){
			result = new String[2];
			result[0] = this.email.getText();
			result[1] = new String(this.password.getPassword());
		}
		return result;
	}
	
	public void clear(){
		this.password.setText("");
	}

}
