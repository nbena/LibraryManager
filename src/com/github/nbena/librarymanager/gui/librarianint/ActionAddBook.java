package com.github.nbena.librarymanager.gui.librarianint;

import java.sql.SQLException;

import com.github.nbena.librarymanager.core.Book;
import com.github.nbena.librarymanager.core.ReservationException;
import com.github.nbena.librarymanager.gui.LibrarianModel;

public class ActionAddBook extends AbstractAction implements Action {
	
	private /*@ spec_public @*/ Book book;
	
	public ActionAddBook(LibrarianModel model){
		super(model);
		super.ask = true;
		super.confirmationMessage = "Confermi l'aggiunta di questo libro?";
		super.resultMessage = "Libro aggiunto";
	}

	/*@
	 @ requires args.length == 5;
	 @ ensures this.book.getTitle().equals((String)args[0]);
	 @ ensures this.book.getAuthors().length == ((String[])args[1]).length;
	 @ ensures \forall int i; i >= 0 && i < (((String[])args[1]).length); (\exists int j; j>=0 && j < this.book.getAuthors().length; this.book.getAuthors()[j].equals(((String[])args[1])[i]));
	 @ ensures this.book.getYearOfPublishing() == (int)args[2];
	 @ ensures this.book.getMainTopic().equals((String)args[3]);
	 @ ensures this.book.getPublishingHouse().equals((String)args[4]);
	 @*/
	@Override
	public void setArgs(Object... args) {
		String title = (String) args[0];
		String [] authors = (String[]) args[1];
		int year = (int) args[2];
		String topic = (String) args[3];
		String phouse = (String) args[4];
		
		this.book = new Book(title, authors, year, topic, phouse);
		
	}

	/*@
	 @ requires this.book != null;
	 @*/
	@Override
	public void execute() throws SQLException, ReservationException {
		super.model.addBook(this.book);
	}

}
