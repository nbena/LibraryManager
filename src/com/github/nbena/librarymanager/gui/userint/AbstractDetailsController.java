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


package com.github.nbena.librarymanager.gui.userint;

import com.github.nbena.librarymanager.gui.librarianint.Action;
import com.github.nbena.librarymanager.gui.view.DetailsViewable;

/**
 * Mini controller for calling DetailsViewable classes.
 * 
 * Important: to do the operations we call AbstractController.executeAction
 * WITHOUT passing the args, the args are passed before the invocation.
 * @author nicola
 *
 */
public abstract class AbstractDetailsController implements DetailsController {
	
	protected Action action;
	// children need to initialize this.
	protected DetailsViewable view;
	
	protected AbstractDetailsController(Action action){
		this.action = action;
	}

	// @Override
	public void setItem(Object item) {
		this.view.setItem(item);
		this.action.setArgs(new Object[]{item});
	}
	
	@Override
	public void setMainTitle(String title){
		this.view.setMainTitle(title);
	}
	
	@Override
	public void show(){
		this.view.setVisible(true);
	}

}
