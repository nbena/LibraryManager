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

import com.github.nbena.librarymanager.core.Consultation;

/**
 * Shows user, title, authors
 * @author nicola
 *
 */

@SuppressWarnings("serial")
public class ConsultationsInProgressTableModel extends AbstractTableModel
	implements SelectableItem{
	
	
	private List<Consultation> items;
	private static final String [] columns = {"Utente", "Titolo", "Autori"};
	
	public ConsultationsInProgressTableModel(List<Consultation> items){
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
	public String getColumnName(int col){
		return columns[col];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Consultation consultation = this.items.get(rowIndex);
		Object value = null;
		switch(columnIndex){
		case 0:
			value = consultation.getUser().getEmail(); break;
		case 1:
			value = consultation.getCopy().getTitle(); break;
		case 2:
			value = Arrays.toString(consultation.getCopy().getAuthors()); break;
		}
		return value;
	}

	@Override
	public Consultation getSelectedItem(int row) {
		return this.items.get(row);
	}

	@Override
	public int getItemsCount(){
		return this.items.size();
	}
}
