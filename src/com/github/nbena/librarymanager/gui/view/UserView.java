/*  LibraryManager a toy library manager
    Copyright (C) 2018 nbena

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
    */

package com.github.nbena.librarymanager.gui.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
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
	
	private JButton btnViewLoans;
	private JButton btnViewSeatReservation;
	private JButton btnViewLoanReservation;
	private JButton btnViewConsultationReservation;
	private JButton btnSearch;
	private JButton btnNewSeatReservation;
	private JButton btnDeregister;
	private JButton btnLogout;
	private JButton btnViewLoansInLate;
	
	public void addActionListenerViewLoan(ActionListener listener){
		this.btnViewLoans.addActionListener(listener);
	}
	
	public void addActionListenerViewSeatReservation(ActionListener listener){
		this.btnViewSeatReservation.addActionListener(listener);
	}
	
	public void addActionListenerViewLoanReservation(ActionListener listener){
		this.btnViewLoanReservation.addActionListener(listener);
	}
	
	public void addActionListenerViewConsultationReservation(ActionListener listener){
		this.btnViewConsultationReservation.addActionListener(listener);
	}
	
	public void addActionListenerSearch(ActionListener listener){
		this.btnSearch.addActionListener(listener);
	}
	
	public void addActionListenerBtnNewSeatReservation(ActionListener listener){
		this.btnNewSeatReservation.addActionListener(listener);
	}
	
	public void addActionListenerDeregister(ActionListener listener){
		this.btnDeregister.addActionListener(listener);
	}
	
	public void addActionListenerLogout(ActionListener listener){
		this.btnLogout.addActionListener(listener);
	}
	
	public void addActionListenerViewLoansInLate(ActionListener listener){
		this.btnViewLoansInLate.addActionListener(listener);
	}

	/**
	 * Create the frame.
	 */
	public UserView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 511, 259);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Cerca", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(12, 12, 225, 60);
		contentPane.add(panel);
		panel.setLayout(null);
		
		btnSearch = new JButton("Cerca");
		btnSearch.setBounds(12, 30, 171, 24);
		panel.add(btnSearch);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Prenota posto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(12, 69, 225, 60);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		btnNewSeatReservation = new JButton("Prenota posto");
		btnNewSeatReservation.setBounds(12, 30, 171, 24);
		panel_1.add(btnNewSeatReservation);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "La tua situazione", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(240, 12, 259, 214);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		btnViewLoans = new JButton("Vedi i tuoi prestiti");
		btnViewLoans.setBounds(12, 25, 221, 24);
		panel_2.add(btnViewLoans);
		
		btnViewLoanReservation = new JButton("Vedi prenotazioni libri");
		btnViewLoanReservation.setBounds(12, 99, 221, 24);
		panel_2.add(btnViewLoanReservation);
		
		btnViewSeatReservation = new JButton("Vedi prenotazioni posti");
		btnViewSeatReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnViewSeatReservation.setBounds(12, 135, 221, 24);
		panel_2.add(btnViewSeatReservation);
		
		btnViewConsultationReservation = new JButton("Vedi consultazioni prenotate");
		btnViewConsultationReservation.setBounds(12, 171, 221, 24);
		panel_2.add(btnViewConsultationReservation);
		
		btnViewLoansInLate = new JButton("Vedi i tuoi prestiti in ritardo");
		btnViewLoansInLate.setBounds(12, 61, 221, 24);
		panel_2.add(btnViewLoansInLate);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Registrazione", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(12, 126, 225, 100);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		btnDeregister = new JButton("Annulla registrazione");
		btnDeregister.setBounds(12, 64, 187, 24);
		panel_3.add(btnDeregister);
		
		btnLogout = new JButton("Logout");
		btnLogout.setBounds(12, 28, 187, 24);
		panel_3.add(btnLogout);
	}
}
