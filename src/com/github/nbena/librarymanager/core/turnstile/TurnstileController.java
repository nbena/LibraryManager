package com.github.nbena.librarymanager.core.turnstile;

import java.sql.SQLException;

import com.github.nbena.librarymanager.core.ReservationException;
import com.github.nbena.librarymanager.core.Seat;
import com.github.nbena.librarymanager.core.User;
import com.github.nbena.librarymanager.man.LibraryManager;

public class TurnstileController {
	
	private LibraryManager manager;
	

	TurnstileController(LibraryManager manager) {
		this.manager = manager;
	}

	void enablePass(){}
	
	void showSeat(Seat seat){}
	
	Seat sendRequestForUser(User user){
		
		Seat seat = null;
		try {
			seat = this.manager.getOrAssignSeat(user);
		} catch (SQLException | ReservationException e) {
			
		}
		return seat;
	}

}
