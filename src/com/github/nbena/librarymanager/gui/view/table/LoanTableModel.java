package com.github.nbena.librarymanager.gui.view.table;
import com.github.nbena.librarymanager.core.Loan;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class LoanTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 5511851526455718521L;

	private List<Loan> loans;
	private final int columnCount = 4;
	
	public LoanTableModel(List<Loan> loans){
		this.loans = loans;
	}

	@Override
	public int getColumnCount() {
		return this.loans.size();
	}

	@Override
	public int getRowCount() {
		return this.columnCount;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		Loan loan = this.loans.get(arg0);
		Object value = null;
		switch(arg1){
		case 0:
			value = loan.getCopy().getTitle(); break;
		case 1:
			value = loan.getCopy().getAuthors(); break;
		case 2:
			value = loan.getStart(); break;
		case 3:
			value = loan.getEnd(); break;
		}
		return value;
	}

}
