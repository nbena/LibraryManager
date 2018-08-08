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
	
	public Seat() {
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
	public /*@ pure @*/ boolean isFree() {
		return free;
	}
	public void setFree(boolean free) {
		this.free = free;
	}
	
	public boolean equals(Seat s){
		return (s != null && s.getNumber() == this.number
				&& s.getTableNumber() == this.tableNumber);
	}
	
	public String toString(){
		return Integer.toString(this.tableNumber) + ":"
				+ Integer.toString(this.number);
	}
	
	public String toExtendedString(){
		return this.toString() + "; free=" + Boolean.toString(this.free);
	}
	
	
	

}
