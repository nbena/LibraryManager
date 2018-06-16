package com.github.nbena.librarymanager.gui.librarianint;

import java.sql.SQLException;

import com.github.nbena.librarymanager.core.ReservationException;
import com.github.nbena.librarymanager.gui.LibrarianModel;

public class ActionNewNotReservedLoan extends AbstractActionWithBookUser implements Action {

	
	// private FiveArgsFunction<User, String, String[], Integer, String> function;
	
//	protected User user;
//	protected String title;
//	protected String [] authors;
//	protected int year;
//	protected String topic;

	public ActionNewNotReservedLoan(LibrarianModel model){
		super(model);
		super.ask = true;
		super.confirmationMessage = "Confermi questo prestito?";
		super.resultMessage = "Prestito confermato";
		
//		this.function = (user, title, authors, year, mainTopic) -> {
//			return model.loanNotReserved(user, title, authors, year, mainTopic);
//		};
	}
	

//	/*
//	 *@ \requires args.length == 5;
//	 * (non-Javadoc)
//	 * @see com.github.nbena.librarymanager.gui.librarianint.Action#setArgs(java.lang.Object[])
//	 */
//	@Override
//	public void setArgs(Object... args) {
//		
//		this.user = (User) args[0];
//		this.title = (String) args[1];
//		this.authors = (String[]) args[2];
//		this.year = (int) args[3];
//		this.topic = (String) args[4];
//		
//	}

	@Override
	public void execute() throws SQLException, ReservationException {
		// this.function.apply(this.user, this.title, this.authors, this.year, this.topic);
		super.model.loanNotReserved(super.user, super.title,
				super.authors, super.year, super.topic);
	}
	
	

}
