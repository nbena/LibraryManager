package com.github.nbena.librarymanager.core;

public class Seat {
	
	private int number;
	private int tableNumber;
	private boolean free;
	
	
	public Seat(int number, int tableNumber, boolean free) {
		super();
		this.number = number;
		this.tableNumber = tableNumber;
		this.free = free;
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getTableNumber() {
		return tableNumber;
	}
	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}
	public boolean isFree() {
		return free;
	}
	public void setFree(boolean free) {
		this.free = free;
	}
	
	
	

}
