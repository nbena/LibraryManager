package com.github.nbena.librarymanager.gui.view.table;

import javax.swing.table.AbstractTableModel;

import com.github.nbena.librarymanager.core.LoanReservation;

import java.util.List;

public class LoanReservationTableModel extends AbstractTableModel {
	
	private List<LoanReservation> items;
	
	public LoanReservationTableModel(List<LoanReservation> items){
		this.items = items;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return this.items.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		LoanReservation reservation = this.items.get(rowIndex);
		Object value = null;
		switch(columnIndex){
		case 0:
			value = reservation.getCopy().getTitle(); break;
		case 1:
			value = reservation.getCopy().getAuthors(); break;
		}
		return value;
	}

}
