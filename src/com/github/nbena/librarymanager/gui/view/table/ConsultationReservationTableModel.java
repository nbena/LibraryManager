package com.github.nbena.librarymanager.gui.view.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.github.nbena.librarymanager.core.ConsultationReservation;

public class ConsultationReservationTableModel extends AbstractTableModel {

	
	private List<ConsultationReservation> items;
	
	public ConsultationReservationTableModel(List<ConsultationReservation> items){
		this.items = items;
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public int getRowCount() {
		return this.items.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ConsultationReservation reservation = this.items.get(rowIndex);
		Object value = null;
		switch(columnIndex){
		case 0:
			value = reservation.getCopy().getTitle(); break;
		case 1:
			value = reservation.getCopy().getAuthors(); break;
		case 3:
			value = reservation.getSeat().getTableNumber(); break;
		case 4:
			value = reservation.getReservationDate(); break;
		case 5:
			value = reservation.getReservationDate(); break;
		}
		return value;
	}
}
