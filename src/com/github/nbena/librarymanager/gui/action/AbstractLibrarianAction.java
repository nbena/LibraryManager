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

package com.github.nbena.librarymanager.gui.action;

import com.github.nbena.librarymanager.gui.LibrarianModel;

/**
 * Top-class for the LibrarianModel-based ActionPattern.
 * @author nicola
 *
 */
public abstract class AbstractLibrarianAction extends AbstractAction {
	
//	protected boolean ask;
//	protected String confirmationMessage;
//	protected String resultMessage;
	
	protected LibrarianModel model;
	// protected Function<Object, Object> function;
	// protected Object [] args;
	
	protected AbstractLibrarianAction(LibrarianModel model){
		this.model = model;
	}

//	@Override
//	public boolean askConfirmation() {
//		return this.ask;
//	}
//	
//	@Override
//	public String getConfirmationMessage(){
//		return this.confirmationMessage;
//	}
//	
//	@Override
//	public String getResultMessage(){
//		return this.resultMessage;
//	}
//	
//	public Object getResult(){
//		return null;
//	}
	
	
	

//	@Override
//	public abstract void setArgs(Object... args);

//	@Override
//	public void execute() throws SQLException {
//		this.function.apply(this.args);
//	}
	
	

}
