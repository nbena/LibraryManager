package com.github.nbena.librarymanager.gui.view.table;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.github.nbena.librarymanager.core.BookCopiesNumber;

@SuppressWarnings("serial")
public class BookCopiesNumberTableModel extends AbstractTableModel implements SelectableItem {
	
	private final String [] columns = {"Titolo", "Autori", "Anno", "Casa ed.", "Argomento", "N copie"};
	private List<BookCopiesNumber> items;
	
	public BookCopiesNumberTableModel(List<BookCopiesNumber> items) {
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
		return this.columns[col];
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		BookCopiesNumber b = this.items.get(arg0);
		Object value = null;
		switch(arg1){
		case 0:
			value = b.getTitle(); break;
		case 1:
			value = Arrays.toString(b.getAuthors()); break;
		case 2:
			value = b.getYearOfPublishing(); break;
		case 3:
			value = b.getPublishingHouse(); break;
		case 4:
			value = b.getMainTopic(); break;
		case 5:
			value = b.getCopiesNumber();
		}
		return value;
	}

	@Override
	public Object getSelectedItem(int row) {
		return items.get(row);
	}
	
	@Override
	public int getItemsCount(){
		return this.items.size();
	}

}
