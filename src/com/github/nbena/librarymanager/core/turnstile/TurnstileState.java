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

package com.github.nbena.librarymanager.core.turnstile;

import com.github.nbena.librarymanager.core.Seat;
import com.github.nbena.librarymanager.core.User;

public interface TurnstileState {
	
	public Seat userEnter(Turnstile turnstile, User user) throws Exception;
	
	// this when the user effectively pass the turnstile
	public void userPass(Turnstile turnstile);
	
	public void userExit(Turnstile turnstile, User user) throws Exception;
	
}
