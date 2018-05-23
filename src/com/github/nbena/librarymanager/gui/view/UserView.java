package com.github.nbena.librarymanager.gui.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JToolBar;

public class UserView extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserView frame = new UserView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private JButton btnViewLoan;
	private JButton btnViewSeatReservation;
	private JButton btnViewLoanReservation;
	private JButton btnViewConsultationReservation;
	private JButton btnSearch;
	private JButton btnNewSeatReservation;
	
	
	public void addActionListenerBtnViewLoan(ActionListener listener){
		this.btnViewLoan.addActionListener(listener);
	}
	
	public void addActionListenerBtnViewSeatReservation(ActionListener listener){
		this.btnViewSeatReservation.addActionListener(listener);
	}
	
	public void addActionListenerBtnViewLoanReservation(ActionListener listener){
		this.btnViewLoanReservation.addActionListener(listener);
	}
	
	public void addActionListenerBtnViewConsultationReservation(ActionListener listener){
		this.btnViewConsultationReservation.addActionListener(listener);
	}
	
	public void addActionListenerBtnSearch(ActionListener listener){
		this.btnSearch.addActionListener(listener);
	}
	
	public void addActionListenerBtnNewSeatReservation(ActionListener listener){
		this.btnNewSeatReservation.addActionListener(listener);
	}

	/**
	 * Create the frame.
	 */
	public UserView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 533, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnViewLoan = new JButton("Vedi i tuoi prestiti");
		btnViewLoan.setBounds(17, 40, 171, 24);
		contentPane.add(btnViewLoan);
		
		btnViewSeatReservation = new JButton("Vedi prenotazioni posti");
		btnViewSeatReservation.setBounds(290, 40, 221, 24);
		contentPane.add(btnViewSeatReservation);
		
		btnViewLoanReservation = new JButton("Vedi prenotazioni libri");
		btnViewLoanReservation.setBounds(290, 86, 221, 24);
		contentPane.add(btnViewLoanReservation);
		
		btnViewConsultationReservation = new JButton("Vedi consultazioni prenotate");
		btnViewConsultationReservation.setBounds(290, 135, 221, 24);
		contentPane.add(btnViewConsultationReservation);
		
		btnSearch = new JButton("Cerca");
		btnSearch.setBounds(17, 86, 171, 24);
		contentPane.add(btnSearch);
		
		btnNewSeatReservation = new JButton("Prenota posto");
		btnNewSeatReservation.setBounds(17, 176, 171, 24);
		contentPane.add(btnNewSeatReservation);
	}
}
