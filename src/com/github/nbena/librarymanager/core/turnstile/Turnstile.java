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

import com.github.nbena.librarymanager.core.LibraryManagerException;
import com.github.nbena.librarymanager.core.Seat;
import com.github.nbena.librarymanager.core.User;
import com.github.nbena.librarymanager.man.LibraryManager;

public class Turnstile {
	
	private TurnstileController controller;
	private TurnstileState state;
	
	public Turnstile(LibraryManager manager){
		this.controller = new TurnstileController(manager);
		this.state = IdleState.IDLE_STATE;
	}
	
	
	//=======================================================
	// EVENTS
	public Seat userArrive(User user) throws Exception{
		return this.state.userEnter(this, user);
	}
	
	public void userPass() {
		this.state.userPass(this);
	}
	
	public void userExits(User user) throws Exception{
		this.state.userExit(this, user);
	}
	
	//=======================================================

	public TurnstileState getState() {
		return state;
	}

	public void setState(TurnstileState state) {
		this.state = state;
	}
	
	Seat sendRequestForUser(User user) throws SQLException, LibraryManagerException{
		return this.controller.sendRequestForUser(user);
	}
	
	
	public String showSeat(Seat seat){
		return this.controller.showSeat(seat);
	}
	
	public String showNoSeats(){
		return this.controller.showNoSeats();
	}
	
	void sendRequestForExit(User user) throws SQLException{
		this.controller.userExits(user);
	}
	
	

}
