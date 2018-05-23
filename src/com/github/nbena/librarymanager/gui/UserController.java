package com.github.nbena.librarymanager.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import com.github.nbena.librarymanager.core.ConsultationReservation;
import com.github.nbena.librarymanager.core.Loan;
import com.github.nbena.librarymanager.core.LoanReservation;
import com.github.nbena.librarymanager.core.SeatReservation;
import com.github.nbena.librarymanager.gui.view.ReservationTableView;
import com.github.nbena.librarymanager.gui.view.UserView;
import com.github.nbena.librarymanager.gui.view.table.ConsultationReservationTableModel;
import com.github.nbena.librarymanager.gui.view.table.LoanReservationTableModel;
import com.github.nbena.librarymanager.gui.view.table.SeatReservationTableModel;

public class UserController {
	
	
	
	private UserView userView;
	private UserModel userModel;
	

	public UserController(UserModel userModel, UserView userView) {
		this.userView = userView;
		this.userModel = userModel;
		
		this.addListeners();
		
		this.userView.setVisible(true);
	}


	private void addListeners(){
		this.addBasicListeneres();
	}
	
	
	private void addBasicListeneres(){
		
		// new seat reservation
		this.userView.addActionListenerBtnNewSeatReservation(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
			
		});
		
		
		this.userView.addActionListenerBtnSearch(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		});
		
		this.userView.addActionListenerBtnViewConsultationReservation(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					List<ConsultationReservation> reservations = userModel.getConsultationReservation();
					ReservationTableView tableView = new ReservationTableView();
					tableView.setVisible(true);
					tableView.setAlwaysOnTop(true);
					tableView.setTableModel(new ConsultationReservationTableModel(reservations));
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
			
		});
		
		this.userView.addActionListenerBtnViewLoan(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					List<Loan> loans = userModel.getActiveLoan();
					
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
			
		});
		
		this.userView.addActionListenerBtnViewLoanReservation(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					List<LoanReservation> reservations = userModel.getLoanReservation();
					ReservationTableView tableView = new ReservationTableView();
					tableView.setVisible(true);
					tableView.setAlwaysOnTop(true);					
					tableView.setTableModel(new LoanReservationTableModel(reservations));
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
			
		});
		
		this.userView.addActionListenerBtnViewSeatReservation(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					List<SeatReservation> reservations = userModel.getSeatsReservations();
					ReservationTableView tableView = new ReservationTableView();
					tableView.setVisible(true);
					tableView.setAlwaysOnTop(true);
					tableView.setTableModel(new SeatReservationTableModel(reservations));					
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
			
		});
	}

}




