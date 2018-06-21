package com.github.nbena.librarymanager.gui.view;

import java.security.NoSuchAlgorithmException;

import com.github.nbena.librarymanager.core.InternalUser;
import com.github.nbena.librarymanager.core.User;
import com.github.nbena.librarymanager.utils.Hash;

@SuppressWarnings("serial")
public class RegisterUserView extends BasicUserView {
	
	public void setEditabled(boolean enabled){
		super.textFieldEmail.setEditable(enabled);
		super.textFieldName.setEditable(enabled);
		super.textFieldSurname.setEditable(enabled);
		super.passwordFieldPassword.setEditable(enabled);
	}
	
	/**
	 * getUser() creates a new User object from the given input, and it hashes
	 * the password too.
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public User getUser(){
		String hashedPassword = Hash.hash(new String(super.passwordFieldPassword.getPassword()));
		User u;
		if (super.rdbtnInternalUser.isSelected()){
			u = new InternalUser();
		}else{
			u = new User();
		}
		u.setEmail(super.textFieldEmail.getText());
		u.setName(super.textFieldName.getText());
		u.setSurname(super.textFieldSurname.getText());
		u.setHashedPassword(hashedPassword);
		return u;
	}
	
	public RegisterUserView(){
		super();
		this.setEditabled(true);
	}

}
