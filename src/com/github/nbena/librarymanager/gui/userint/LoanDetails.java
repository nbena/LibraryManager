package com.github.nbena.librarymanager.gui.userint;

import com.github.nbena.librarymanager.gui.UserController;
import com.github.nbena.librarymanager.gui.view.LoanView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.github.nbena.librarymanager.core.Loan;

public class LoanDetails extends AbstractDetailsWithController implements Details {
	
	private LoanView view;

	public LoanDetails(UserController controller) {
		super(controller);
		
		this.view = new LoanView();
		
		this.view.addActionListenerRenew(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Loan l = controller.renewLoan((Loan) item);
				if (l!=null){
					item = l;
					view.setLoan((Loan) item);
				}
			}
			
		});
	}

	@Override
	public void show() {
		this.view.setVisible(true);
		this.view.setLoan((Loan) super.item);
	}
	

}
