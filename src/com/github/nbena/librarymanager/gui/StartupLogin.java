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
	
	public String[] getCredentials(String msg){
		if (msg == null){
			msg = "Inserisci le tue credenziali";
		}
		String [] result = null;
		int res = JOptionPane.showConfirmDialog(null, this.panel,
				msg, JOptionPane.OK_CANCEL_OPTION);
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
