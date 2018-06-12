package com.github.nbena.librarymanager.gui.view.table;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.github.nbena.librarymanager.core.ConsultationReservation;

@SuppressWarnings("serial")
public class ConsultationReservationTableModel extends AbstractTableModel
 implements SelectableItem {

	
	private List<ConsultationReservation> items;
	private final String [] columns = {"Titolo", "Autori", "Tavolo:posto",
			"Data", "Data prenotazione"};
	
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
	public String getColumnName(int col) {
		  return this.columns[col];
	}	

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ConsultationReservation reservation = this.items.get(rowIndex);
		Object value = null;
		switch(columnIndex){
		case 0:
			value = reservation.getCopy().getTitle(); break;
		case 1:
			value = Arrays.toString(reservation.getCopy().getAuthors()); break;
		case 3:
			value = reservation.getSeat().toString(); break;
			// value = reservation.getSeat().getTableNumber(); break;
		case 4:
			value = reservation.getReservationDate(); break;
		case 5:
			value = reservation.getTimestamp(); break;
		}
		return value;
	}

	@Override
	public ConsultationReservation getSelectedItem(int row) {
		return this.items.get(row);
	}
}
