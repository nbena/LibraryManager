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

package com.github.nbena.librarymanager.man;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.github.nbena.librarymanager.core.InternalUser;
import com.github.nbena.librarymanager.core.ReservationException;
import com.github.nbena.librarymanager.core.Seat;
import com.github.nbena.librarymanager.core.SeatReservation;
import com.github.nbena.librarymanager.core.User;

public class LibraryManager {
	
	private DbManager dbManager;
	
	public LibraryManager(String uri, String username, String password) throws ClassNotFoundException, SQLException{
		this.dbManager = new DbManager(uri, username, password);
	}
	
	public void saveUser(User user) throws SQLException{
		this.dbManager.addUser(user);
	}
	
	
	public void printBadge(User user){
		
	}
	
	public boolean authenticateUser(String hashedPassword){
		
		boolean authenticated = true;
		try {
			authenticated = this.dbManager.authenticateUser(hashedPassword);
		} catch (SQLException e) {
			authenticated = false;
		}
		
		return authenticated;
	}
	
	
	public SeatReservation tryReserveSeat(InternalUser user, LocalDate date) throws ReservationException{
		try{
			List<Seat> seats = this.dbManager.getAvailableSeats(date);
			if (seats.size() > 0){
				SeatReservation reservation = new SeatReservation(user, date, seats.get(0));
				this.dbManager.addSeatReservation(reservation);
				return reservation;
			}else{
				throw new ReservationException("No seats available");
			}			
		}catch(SQLException e){
			throw new ReservationException(e);
		}
	}
	
	
	

}
