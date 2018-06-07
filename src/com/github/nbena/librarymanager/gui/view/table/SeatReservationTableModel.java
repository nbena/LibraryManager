package com.github.nbena.librarymanager.gui.view.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.github.nbena.librarymanager.core.SeatReservation;

@SuppressWarnings("serial")
public class SeatReservationTableModel extends AbstractTableModel {
	
	private List<SeatReservation> items;
	private final String[] columns = { "Tavolo", "Posto" };
	
	public SeatReservationTableModel(List<SeatReservation> items){
		this.items = items;
	}

	@Override
	public int getColumnCount() {
		return 3;
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
		SeatReservation reservation = this.items.get(rowIndex);
		Object value = null;
		switch(columnIndex){
		case 0:
			value = reservation.getSeat().getTableNumber(); break;		
		case 1:
			value = reservation.getSeat().getNumber(); break;
		case 2:
			value = reservation.getReservationDate(); break;
		}
		return value;
	}

}
