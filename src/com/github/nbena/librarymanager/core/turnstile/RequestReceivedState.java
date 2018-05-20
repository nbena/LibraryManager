package com.github.nbena.librarymanager.core.turnstile;

import com.github.nbena.librarymanager.core.Seat;
import com.github.nbena.librarymanager.core.User;

public class RequestReceivedState implements TurnstileState {
	
	public static TurnstileState REQUEST_RECEIVED_STATE =
			new RequestReceivedState();
	
	public void userArrive(Turnstile turnstile, User user){
		
	}
	
	public void userPass(Turnstile turnstile){
		turnstile.setState(IdleState.IDLE_STATE);
	}

}
