package com.github.nbena.librarymanager.gui.view.table;
import com.github.nbena.librarymanager.core.Loan;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class LoanTableModel extends AbstractTableModel implements SelectableItem {

	private List<Loan> loans;
	private final int columnCount = 4;
	private final String[] columns = {"Titolo", "Autori", "Data inizio", "Data restituzione"};
	
	public LoanTableModel(List<Loan> loans){
		this.loans = loans;
	}

	@Override
	public int getColumnCount() {
		return this.columnCount;
	}

	@Override
	public int getRowCount() {
		return this.loans.size();
	}
	
	@Override
	public String getColumnName(int col) {
		  return this.columns[col];
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		Loan loan = this.loans.get(arg0);
		Object value = null;
		switch(arg1){
		case 0:
			value = loan.getCopy().getTitle(); break;
		case 1:
			value = Arrays.toString(loan.getCopy().getAuthors()); break;
		case 2:
			value = loan.getStart(); break;
		case 3:
			value = loan.getEnd(); break;
		}
		return value;
	}

	@Override
	public Loan getSelectedItem(int row) {
		return this.loans.get(row);
	}

}
