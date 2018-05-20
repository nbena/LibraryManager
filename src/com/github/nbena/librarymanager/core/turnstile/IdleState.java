package com.github.nbena.librarymanager.core.turnstile;

import com.github.nbena.librarymanager.core.Seat;
import com.github.nbena.librarymanager.core.User;

public class IdleState implements TurnstileState {
	
	public static IdleState IDLE_STATE = new IdleState();
	
	public void userArrive(Turnstile turnstile, User user){
		Seat seat = turnstile.sendRequestForUser(user);
		// turnstile.setState(RequestState.REQUEST_STATE);
		
		if (seat != null){
			turnstile.showSeat(seat);
			turnstile.setState(OpenState.OPEN_STATE);
		}else{
			turnstile.showNoSeats();
		}
	}
	
	public void userPass(Turnstile turnstile){}

}
