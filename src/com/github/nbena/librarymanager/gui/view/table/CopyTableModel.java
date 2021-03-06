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

package com.github.nbena.librarymanager.gui.view.table;

import java.util.Arrays;

import javax.swing.table.AbstractTableModel;

import com.github.nbena.librarymanager.core.Copy;
import com.github.nbena.librarymanager.core.CopyForConsultation;

import java.util.List;

@SuppressWarnings("serial")
public class CopyTableModel extends AbstractTableModel
	implements SelectableItem {
	
	private List<Copy> items;
	private final String [] columns = { "Titolo", "Autori", "Anno", "Stato",
			"Consultabile"};
	
	public CopyTableModel(List<Copy> items){
		this.items = items;
	}
	
	@Override
	public int getColumnCount(){
		return 5;
	}
	
	@Override
	public int getRowCount(){
		return this.items.size();
	}
	
	@Override
	public String getColumnName(int col) {
		  return this.columns[col];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex){
		Copy copy = this.items.get(rowIndex);
		Object value = null;
		switch(columnIndex){
		case 0:
			value = copy.getTitle(); break;
		case 1:
			value = Arrays.toString(copy.getAuthors()); break;
		case 2:
			value = copy.getYearOfPublishing(); break;
		case 3:
			value = copy.getStatus(); break;
		case 4:
			if (copy instanceof CopyForConsultation){
				value = true;
			}else{
				value = false;
			}
			break;
		}
		return value;
	}
	
	@Override
	public Copy getSelectedItem(int row){
		return this.items.get(row);
	}
	
	@Override
	public int getItemsCount(){
		return this.items.size();
	}
	

}
