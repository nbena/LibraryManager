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

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.github.nbena.librarymanager.core.Copy;
import com.github.nbena.librarymanager.gui.UserController;
import com.github.nbena.librarymanager.gui.action.ActionReserve;
import com.github.nbena.librarymanager.gui.view.BookReserveView;

public class BookDetails extends AbstractDetailsController  {

	// protected BookReserveView view;
	private Copy item;
	
	public BookDetails(ActionReserve action) {
		super(action);
		
		super.view = new BookReserveView();
		((BookReserveView) this.view).addActionListenerReserveButton(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent arg0) {

				
				boolean res = UserController.reserve(item, action, (Component) view);
				if(res){
					((Component) view).setVisible(false);
					((Window) view).dispose();
				}
			}
		});
		
		this.view.addActionListenerOk(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {

				((Component) view).setVisible(false);
				((Window) view).dispose();
				
			}
			
		});
	}

//	@Override
//	public void show() {
//		this.view.setVisible(true);
//		this.view.setBook((Book) super.item);
//	}
	
	@Override
	public void setItem(Object item){
		this.item = (Copy) item;
		this.view.setItem(item);
	}
	
	/**
	 * Specific to tell if the 'reserve' button could be
	 * enabled.
	 * @param enabled
	 */
	public void setReserveEnabled(boolean enabled){
		((BookReserveView) super.view).setButtonReserveEnabled(enabled);
	}

}
