package com.github.nbena.librarymanager.core.turnstile;

import com.github.nbena.librarymanager.core.User;

public interface TurnstileState {
	
	public void userArrive(Turnstile turnstile, User user);
	
	public void userPass(Turnstile turnstile);
	
}
