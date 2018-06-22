package com.github.nbena.librarymanager.gui.userint;

import com.github.nbena.librarymanager.gui.AbstractController;
import com.github.nbena.librarymanager.gui.UserController;
import com.github.nbena.librarymanager.gui.view.LoanView;

import com.github.nbena.librarymanager.core.Loan;

public class LoanDetails extends AbstractDetailsWithController {
	

	public LoanDetails(AbstractController controller) {
		super(controller);
	}

	@Override
	public void show() {
		LoanView view = ((UserController) super.controller).getLoanView();
		view.setLoan((Loan) super.item);
		view.setVisible(true);
	}
	
//	protected LoanDetails(V controller) {
//		super(controller);
//	}
//
//
//	@Override
//	public void show() {
//		()
//	}

}
