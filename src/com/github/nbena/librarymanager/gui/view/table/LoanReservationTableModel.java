package com.github.nbena.librarymanager.gui.view.table;

import javax.swing.table.AbstractTableModel;

import com.github.nbena.librarymanager.core.LoanReservation;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("serial")
public class LoanReservationTableModel extends AbstractTableModel
 implements SelectableItem {
	
	private List<LoanReservation> items;
	private final String[] columns = { "Titolo", "Autori" };
	
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
	public String getColumnName(int col) {
		  return this.columns[col];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		LoanReservation reservation = this.items.get(rowIndex);
		Object value = null;
		switch(columnIndex){
		case 0:
			value = reservation.getCopy().getTitle(); break;
		case 1:
			value = Arrays.toString(reservation.getCopy().getAuthors()); break;
		}
		return value;
	}
	
	@Override
	public LoanReservation getSelectedItem(int row) {
		return this.items.get(row);
	}

}
