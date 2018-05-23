package com.github.nbena.librarymanager.gui.view.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.github.nbena.librarymanager.core.SeatReservation;

public class SeatReservationTableModel extends AbstractTableModel {
	
	private List<SeatReservation> items;
	
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
	public Object getValueAt(int rowIndex, int columnIndex) {
		SeatReservation reservation = this.items.get(rowIndex);
		Object value = null;
		switch(columnIndex){
		case 0:
			value = reservation.getSeat().getNumber(); break;
		case 1:
			value = reservation.getSeat().getTableNumber(); break;
		case 2:
			value = reservation.getReservationDate(); break;
		}
		return value;
	}

}
