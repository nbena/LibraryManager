package com.github.nbena.librarymanager.gui;

import java.awt.Component;

import javax.swing.JOptionPane;

public abstract class AbstractController {
	
	protected void displayMessage(Component parent, String message){
		JOptionPane.showMessageDialog(parent, message);
	}
	
	protected void displayError(Component parent, Exception exception){
		JOptionPane.showMessageDialog(parent, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}

}
