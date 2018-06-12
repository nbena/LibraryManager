package com.github.nbena.librarymanager.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JOptionPane;

import com.github.nbena.librarymanager.core.ConsultationReservation;
import com.github.nbena.librarymanager.core.Loan;
import com.github.nbena.librarymanager.core.LoanReservation;
import com.github.nbena.librarymanager.core.SeatReservation;
import com.github.nbena.librarymanager.gui.view.GenericTableView;
import com.github.nbena.librarymanager.gui.view.UserView;
import com.github.nbena.librarymanager.gui.view.table.ConsultationReservationTableModel;
import com.github.nbena.librarymanager.gui.view.table.LoanReservationTableModel;
import com.github.nbena.librarymanager.gui.view.table.LoanTableModel;
import com.github.nbena.librarymanager.gui.view.table.SeatReservationTableModel;

public class UserController extends AbstractController {
	
	
	private UserView userView;
	private UserModel userModel;
	
	// private LocalDate gotDate;
	

	public UserController(UserModel userModel, UserView userView) {
		this.userView = userView;
		this.userModel = userModel;
		
		super.genericTableView = new GenericTableView();
		
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
				
				try{
					
				
				LocalDate date = datePicker(userView, null, true);
				if (date != null){
//					try {
//						SeatReservation reservation = userModel.reserveSeat(date);
//						showSeatReservationDetails(reservation);
//					} catch (ReservationException | SQLException e) {
//						displayError(userView, e);
//					}
					System.out.println(date.toString());
				}
				}
				catch(NumberFormatException e1){
					displayMessage(userView, "Errore nell'input", "Errore", JOptionPane.ERROR_MESSAGE);
				}
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
					displayTableItems(new ConsultationReservationTableModel(reservations), userView);
					genericTableView.setMenuItemCancelEnabled(true);
					genericTableView.setMenuItemDetailsEnabled(true);
				} catch (SQLException e1) {
					displayError(userView, e1);
				}
			}
			
		});
		
		this.userView.addActionListenerBtnViewLoan(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					List<Loan> loans = userModel.getActiveLoan();
					displayTableItems(new LoanTableModel(loans), userView);
					genericTableView.setMenuItemCancelEnabled(true);
					genericTableView.setMenuItemDetailsEnabled(true);
				} catch (SQLException e1) {
					displayError(userView, e1);
				}
			}
			
		});
		
		this.userView.addActionListenerBtnViewLoanReservation(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					List<LoanReservation> reservations = userModel.getLoanReservation();
					displayTableItems(new LoanReservationTableModel(reservations), userView);
					genericTableView.setMenuItemCancelEnabled(true);
					genericTableView.setMenuItemDetailsEnabled(true);
				} catch (SQLException e1) {
					displayError(userView, e1);
				}
			}
			
		});
		
		this.userView.addActionListenerBtnViewSeatReservation(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					List<SeatReservation> reservations = userModel.getSeatsReservations();	
					displayTableItems(new SeatReservationTableModel(reservations), userView);
					genericTableView.setMenuItemCancelEnabled(true);
					genericTableView.setMenuItemDetailsEnabled(true);					
				} catch (SQLException e1) {
					displayError(userView, e1);
				}
			}
			
		});
		
//		this.genericTableView.addTableMouseListener(new MouseListener(){
//
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void mouseEntered(MouseEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void mouseExited(MouseEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void mousePressed(MouseEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void mouseReleased(MouseEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		});
//		
	}
	
}




