/*  LibraryManager a toy library manager
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
	
	String showSeat(Seat seat){
		return String.format("Il tuo posto è: tavolo: %d posto: %d\n", seat.getTableNumber(), seat.getNumber());
	}
	
	String showNoSeats(){
		return "No seats available";
	}
	
	Seat sendRequestForUser(User user) throws SQLException, ReservationException{
		return this.manager.getOrAssignSeat(user);
	}
	
	void userExits(User user) throws SQLException{
		this.manager.userExit(user);
	}

}
