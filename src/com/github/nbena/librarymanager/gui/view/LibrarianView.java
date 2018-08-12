/*  LibraryManager
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
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;

@SuppressWarnings("serial")
public class LibrarianView extends JFrame {

	private JPanel contentPane;
	
	private JButton btnNewNotReservedLoan;
	private JButton btnNewUser;
	private JButton btnNewReservedLoan;
	private JButton btnDeliveryLoan;
	private JButton btnNewNotReservedConsultation;
	private JButton btnNewReservedConsultation;
	private JButton btnDeliveryConsultation;
	private JButton btnAddBook;
	private JButton btnDeleteBook;
	private JButton btnChangeCopiesNumber;
	private JButton btnViewConsultationsInProgress;
	private JButton btnViewLoansInLate;
	private JButton btnViewSeatsSituation;
	private JButton btnCleanup;
	
	
	public void addActionListenerNewNotReservedLoan(ActionListener listener){
		this.btnNewNotReservedLoan.addActionListener(listener);
	}
	
	public void addActionListenerAddUser(ActionListener listener){
		this.btnNewUser.addActionListener(listener);
	}
	
	public void addActionListenerNewReservedLoan(ActionListener listener){
		this.btnNewReservedLoan.addActionListener(listener);
	}
	
	public void addActionListenerDeliveryLoan(ActionListener listener){
		this.btnDeliveryLoan.addActionListener(listener);
	}
	
	public void addActionListenerNewNotReservedConsultation(ActionListener listener){
		this.btnNewNotReservedConsultation.addActionListener(listener);
	}
	
	public void addActionListenerNewReservedConsultation(ActionListener listener){
		this.btnNewReservedConsultation.addActionListener(listener);
	}
	
	public void addActionListenerDeliveryConsultation(ActionListener listener){
		this.btnDeliveryConsultation.addActionListener(listener);
	}
	
	public void addActionListenerAddBook(ActionListener listener){
		this.btnAddBook.addActionListener(listener);
	}
	
	public void addActionListenerDeleteBook(ActionListener listener){
		this.btnDeleteBook.addActionListener(listener);
	}
	
	public void addActionListenerChangeCopiesNumber(ActionListener listener){
		this.btnChangeCopiesNumber.addActionListener(listener);
	}
	
	public void addActionListenerViewConsultationsInProgress(ActionListener listener){
		this.btnViewConsultationsInProgress.addActionListener(listener);
	}
	
	public void addActionListenerViewLoansInLate(ActionListener listener){
		this.btnViewLoansInLate.addActionListener(listener);
	}
	
	public void addActionListenerViewSeatsSituation(ActionListener listener){
		this.btnViewSeatsSituation.addActionListener(listener);
	}
	
	public void addActionListenerCleanup(ActionListener listener){
		this.btnCleanup.addActionListener(listener);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LibrarianView frame = new LibrarianView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LibrarianView() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 428);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Utenti", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(12, 327, 271, 60);
		contentPane.add(panel);
		panel.setLayout(null);
		
		btnNewUser = new JButton("Registra utente");
		btnNewUser.setBounds(12, 26, 159, 24);
		panel.add(btnNewUser);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Prestiti", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(12, 12, 271, 186);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		btnNewNotReservedLoan = new JButton("Nuovo prestito non prenotato");
		btnNewNotReservedLoan.setBounds(12, 22, 226, 24);
		panel_1.add(btnNewNotReservedLoan);
		
		btnNewReservedLoan = new JButton("Nuovo prestito prenotato");
		btnNewReservedLoan.setBounds(12, 58, 226, 24);
		panel_1.add(btnNewReservedLoan);
		
		btnDeliveryLoan = new JButton("Registra consegna prestito");
		btnDeliveryLoan.setBounds(12, 102, 226, 24);
		panel_1.add(btnDeliveryLoan);
		
		btnViewLoansInLate = new JButton("Vedi prestiti in ritardo");
		btnViewLoansInLate.setBounds(12, 150, 226, 24);
		panel_1.add(btnViewLoansInLate);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Consultazioni", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(295, 12, 293, 208);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		btnNewNotReservedConsultation = new JButton("Nuova consultazione non prenotata");
		btnNewNotReservedConsultation.setBounds(12, 22, 269, 24);
		panel_2.add(btnNewNotReservedConsultation);
		
		btnNewReservedConsultation = new JButton("Nuova consultazione prenotata");
		btnNewReservedConsultation.setBounds(12, 73, 269, 24);
		panel_2.add(btnNewReservedConsultation);
		
		btnDeliveryConsultation = new JButton("Registra consegna consultazione");
		btnDeliveryConsultation.setBounds(12, 120, 269, 24);
		panel_2.add(btnDeliveryConsultation);
		
		btnViewConsultationsInProgress = new JButton("Vedi consultazioni in corso");
		btnViewConsultationsInProgress.setBounds(12, 170, 269, 24);
		panel_2.add(btnViewConsultationsInProgress);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Libri", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(295, 232, 293, 155);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		btnAddBook = new JButton("Aggiungi libro");
		btnAddBook.setBounds(12, 22, 185, 24);
		panel_3.add(btnAddBook);
		
		btnChangeCopiesNumber = new JButton("Modifica numero copie");
		btnChangeCopiesNumber.setBounds(12, 68, 187, 24);
		panel_3.add(btnChangeCopiesNumber);
		
		btnDeleteBook = new JButton("Rimuovi libro");
		btnDeleteBook.setBounds(12, 119, 185, 24);
		panel_3.add(btnDeleteBook);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Altro", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel_4.setBounds(12, 204, 271, 111);
		contentPane.add(panel_4);
		panel_4.setLayout(null);
		
		btnViewSeatsSituation = new JButton("Vedi situazione aula studio");
		btnViewSeatsSituation.setBounds(12, 28, 247, 24);
		panel_4.add(btnViewSeatsSituation);
		
		btnCleanup = new JButton("Pulisci il database");
		btnCleanup.setBounds(12, 64, 247, 24);
		panel_4.add(btnCleanup);
	}
}
