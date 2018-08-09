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

import javax.swing.table.AbstractTableModel;

import com.github.nbena.librarymanager.core.Study;

import java.util.List;

@SuppressWarnings("serial")
public class StudyTableModel extends AbstractTableModel
implements SelectableItem {
	
	private List<Study> items;
	private static final String [] columns = {
			"Tavolo", "Posto", "Mail utente" };
	
	public StudyTableModel(List<Study> items){
		this.items = items;
	}

	@Override
	public int getColumnCount() {
		return StudyTableModel.columns.length;
	}

	@Override
	public int getRowCount() {
		return this.items.size();
	}
	
	@Override
	public String getColumnName(int col) {
		  return StudyTableModel.columns[col];
	}


	@Override
	public Object getValueAt(int arg0, int arg1) {
		Study study = this.items.get(arg0);
		Object value = null;
		switch(arg1){
		case 0:
			value = study.getSeat().getTableNumber(); break;
		case 1:
			value = study.getSeat().getNumber(); break;
		case 2:
			value = study.getUser().getEmail(); break;
		}
		return value;
	}

	@Override
	public Object getSelectedItem(int row) {
		return this.items.get(row);
	}

	@Override
	public int getItemsCount() {
		return this.items.size();
	}

}
