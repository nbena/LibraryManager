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

import java.sql.SQLException;
import java.util.List;

import com.github.nbena.librarymanager.core.Book;
import com.github.nbena.librarymanager.core.Consultation;
import com.github.nbena.librarymanager.core.Copy;
import com.github.nbena.librarymanager.core.InternalUser;
import com.github.nbena.librarymanager.core.Loan;
import com.github.nbena.librarymanager.core.ReservationException;
import com.github.nbena.librarymanager.core.Seat;
import com.github.nbena.librarymanager.core.User;
import com.github.nbena.librarymanager.man.LibraryManager;

public class LibrarianModel extends AbstractModel {
	
	
	public LibrarianModel(LibraryManager manager) {
		super(manager);
	}
	
	
	public User getUserFromEmail(String email) throws SQLException{
		User user = new User();
		user.setEmail(email);
		return super.manager.getUserFromMail(user);
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
	
	public void deliveryBook(User user, Copy copy) throws SQLException, ReservationException{
		super.manager.deliveryBook(user, copy);
	}
	
	public Loan loanNotReserved(User user, Copy copy) throws SQLException{
		return super.manager.loanNotReserved(user, copy);
	}
	
	public Loan loanReserved(InternalUser user, Copy copy) throws ReservationException, SQLException{
		return super.manager.loanReserved(user, copy);
	}
	
	public boolean renewLoan(Loan loan) throws SQLException{
		return super.manager.tryRenewLoan(loan);
	}
	
	public Seat startNotReservedConsultation(User user, Book book) throws SQLException, ReservationException{
		return super.manager.startNotReservedConsultation(user, book);
	}
	
	public Seat startReservedConsultation(InternalUser user, Book book) throws SQLException, ReservationException{
		return super.manager.startReservedConsultation(user, book);
	}
	
	public void deliveryConsultation(Consultation consultation) throws SQLException{
		super.manager.deliveryConsultation(consultation);
	}
	
	public List<User> users() throws SQLException{
		return super.manager.users();
	}

}
