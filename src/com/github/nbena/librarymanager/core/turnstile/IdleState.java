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

public class IdleState implements TurnstileState {
	
	public static IdleState IDLE_STATE = new IdleState();
	
	@Override
	public Seat userEnter(Turnstile turnstile, User user) throws SQLException, LibraryManagerException{
		Seat seat = turnstile.sendRequestForUser(user);
		// turnstile.setState(RequestState.REQUEST_STATE);
		
		if (seat != null){
			// turnstile.showSeat(seat);
			turnstile.setState(OpenState.OPEN_STATE);
		}
//		}else{
//			turnstile.showNoSeats();
//		}
		return seat;
	}
	
	@Override
	public void userPass(Turnstile turnstile){}

	@Override
	public void userExit(Turnstile turnstile, User user) throws Exception {
		turnstile.sendRequestForExit(user);
		
		turnstile.setState(OpenState.OPEN_STATE);
	}
	
	


}
