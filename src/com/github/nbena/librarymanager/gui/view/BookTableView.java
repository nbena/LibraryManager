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


package com.github.nbena.librarymanager.gui.view;

import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.github.nbena.librarymanager.gui.view.table.Popupable;

@SuppressWarnings("serial")
public class BookTableView extends AbstractTableView implements Popupable {

	private JMenuItem mntmDelete;
	private JMenuItem mntmIncrementCopiesNumber;
	private JMenuItem mntmDecrementCopiesNumber;
	
	public void addMenuItemDeleteListener(ActionListener listener){
		this.mntmDelete.addActionListener(listener);
	}
	
	public void addMenuItemIncrementCopiesNumberListener(ActionListener listener){
		this.mntmIncrementCopiesNumber.addActionListener(listener);
	}
	
	public void addMenuItemDecrementCopiesNumberListener(ActionListener listener){
		this.mntmDecrementCopiesNumber.addActionListener(listener);
	}
	
	public void setMenuItemDeleteEnabled(boolean enabled){
		this.mntmDelete.setEnabled(enabled);
	}
	
	public void setMenuItemIncrementCopiesNumberEnabled(boolean enabled){
		this.mntmIncrementCopiesNumber.setEnabled(enabled);
	}
	
	public void setMenuItemDecrementCopiesNumberEnabled(boolean enabled){
		this.mntmDecrementCopiesNumber.setEnabled(enabled);
	}
	
	public BookTableView(){
		super();
		
		this.mntmDelete = new JMenuItem("Elimina");
		super.menu.add(this.mntmDelete);
		
		this.mntmIncrementCopiesNumber = new JMenuItem("Incrementa numero copie");
		super.menu.add(this.mntmIncrementCopiesNumber);
		
		this.mntmDecrementCopiesNumber = new JMenuItem("Riduci numero copie");
		super.menu.add(this.mntmDecrementCopiesNumber);
	}
	
}
