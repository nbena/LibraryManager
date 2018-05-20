package com.github.nbena.librarymanager.gui;

import java.sql.SQLException;

import com.github.nbena.librarymanager.core.Book;
import com.github.nbena.librarymanager.core.InternalUser;
import com.github.nbena.librarymanager.core.User;
import com.github.nbena.librarymanager.man.LibraryManager;

public class LibrarianModel extends AbstractModel {
	
	
	public LibrarianModel(LibraryManager manager) {
		super(manager);
	}

	public void createUser(String name, String surname, String email, String password, boolean internal) throws SQLException{
		User user;
		if (internal){
			user = new InternalUser(name, surname, password, email);
		}else{
			user = new User(name, surname, password, email);
		}
		user.hashPassword();
		
		super.manager.saveUser(user);
	}
	
	public void addBook(Book book) throws SQLException{
		super.manager.addBook(book);
	}
	
	public void deleteBook(Book book) throws SQLException{
		super.manager.deleteBook(book);
	}

}
