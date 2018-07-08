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

package com.github.nbena.librarymanager.gui.view.table;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.github.nbena.librarymanager.core.Book;

@SuppressWarnings("serial")
public class BookTableModel extends AbstractTableModel implements SelectableItem {


	private final String [] columns = {"Titolo", "Autori", "Anno", "Casa ed.", "Argomento"};
	private List<Book> items;
	
	public BookTableModel(List<Book> items) {
		this.items = items;
	}
	
	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return this.items.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		Book b = this.items.get(arg0);
		Object value = null;
		switch(arg1){
		case 0:
			value = b.getTitle(); break;
		case 1:
			value = Arrays.toString(b.getAuthors()); break;
		case 2:
			value = b.getYearOfPublishing(); break;
		case 3:
			value = b.getPublishingHouse(); break;
		case 4:
			value = b.getMainTopic(); break;
		}
		return value;
	}

	@Override
	public Object getSelectedItem(int row) {
		return items.get(row);
	}

}
