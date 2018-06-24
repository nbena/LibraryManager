package com.github.nbena.librarymanager.gui.userint;

import com.github.nbena.librarymanager.gui.AbstractController;
import com.github.nbena.librarymanager.gui.UserModel;
import com.github.nbena.librarymanager.gui.view.LoanView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.github.nbena.librarymanager.core.Loan;
import com.github.nbena.librarymanager.core.ReservationException;

public class LoanDetails extends AbstractDetailsWithModel {
	
	private LoanView view;

	public LoanDetails(UserModel model) {
		super(model);
		
		this.view = new LoanView();
		
		this.view.addActionListenerRenew(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Loan loan = (Loan) item;
				try {
					System.out.println(loan);
					Loan renewed = model.renewLoan(loan);
					view.setLoan(renewed);
					System.out.println(renewed);
					AbstractController.displayMessage(view, "Confermao", "Info",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException | ReservationException e) {
					AbstractController.displayError(view, e);
				}
			}
			
		});
	}

	@Override
	public void show() {
//		LoanView view = ((UserController) super.controller).getLoanView();
//		view.setLoan((Loan) super.item);
//		view.setVisible(true);
		this.view.setVisible(true);
		this.view.setLoan((Loan) super.item);
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
