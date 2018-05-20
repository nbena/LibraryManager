package com.github.nbena.librarymanager.core.turnstile;

import com.github.nbena.librarymanager.core.Seat;
import com.github.nbena.librarymanager.core.User;
import com.github.nbena.librarymanager.man.LibraryManager;

public class Turnstile {
	
	private TurnstileController controller;
	private TurnstileState state;
	
	public Turnstile(LibraryManager manager){
		this.controller = new TurnstileController(manager);
	}
	
	public void userArrive(User user){
		this.state.userArrive(this, user);
	}
	
	public void userPass(){
		this.state.userPass(this);
	}

	public TurnstileState getState() {
		return state;
	}

	public void setState(TurnstileState state) {
		this.state = state;
	}
	
	Seat sendRequestForUser(User user){
		return this.controller.sendRequestForUser(user);
	}
	
	
	public String showSeat(Seat seat){
		return this.controller.showSeat(seat);
	}
	
	public String showNoSeats(){
		return this.controller.showNoSeats();
	}
	
	

}
