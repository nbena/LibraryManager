package com.github.nbena.librarymanager.core.turnstile;

import com.github.nbena.librarymanager.core.User;

public class OpenState implements TurnstileState {
	
	public static OpenState OPEN_STATE = new OpenState();
	
	public void userArrive(Turnstile turnstile, User user){}
	
	public void userPass(Turnstile turnstile){
		turnstile.setState(IdleState.IDLE_STATE);
	}
	
}
