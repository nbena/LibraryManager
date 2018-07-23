package com.github.nbena.librarymanager.gui.view.table;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.github.nbena.librarymanager.core.Consultation;

@SuppressWarnings("serial")
public class ConsultationInProgressTableModel extends AbstractTableModel
	implements SelectableItem{
	
	
	private List<Consultation> items;
	private final String [] columns = {"Utente", "Titolo", "Autori"};
	
	public ConsultationInProgressTableModel(List<Consultation> items){
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
	public String getColumnName(int col){
		return this.columns[col];
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
