package com.github.nbena.librarymanager.gui.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class LibrarianView extends JFrame {

	private JPanel contentPane;

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
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewNotReservedLoan = new JButton("Nuovo prestito non prenotato");
		btnNewNotReservedLoan.setBounds(12, 24, 226, 24);
		contentPane.add(btnNewNotReservedLoan);
		
		JButton btnNewUser = new JButton("Registra utente");
		btnNewUser.setBounds(12, 184, 135, 24);
		contentPane.add(btnNewUser);
		
		JButton btnReservedLoan = new JButton("Nuovo prestito prenotato");
		btnReservedLoan.setBounds(12, 68, 226, 24);
		contentPane.add(btnReservedLoan);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(184, 114, 25, -2);
		contentPane.add(lblNewLabel);
		
		JButton btnDeliveryLoan = new JButton("Registra consegna prestito");
		btnDeliveryLoan.setBounds(12, 115, 226, 24);
		contentPane.add(btnDeliveryLoan);
		
		JButton btnNewNotReservedConsultation = new JButton("Nuova consultazione non prenotata");
		btnNewNotReservedConsultation.setBounds(287, 24, 280, 24);
		contentPane.add(btnNewNotReservedConsultation);
		
		JButton btnNewReservedConsultation = new JButton("Nuova consultazione prenotata");
		btnNewReservedConsultation.setBounds(287, 68, 280, 24);
		contentPane.add(btnNewReservedConsultation);
		
		JButton btnDeliveryConsultation = new JButton("Registra consegna consultazione");
		btnDeliveryConsultation.setBounds(287, 114, 280, 24);
		contentPane.add(btnDeliveryConsultation);
		
		JButton btnAddBook = new JButton("Aggiungi libro");
		btnAddBook.setBounds(168, 184, 135, 24);
		contentPane.add(btnAddBook);
		
		JButton btnDeleteBook = new JButton("Rimuovi libro");
		btnDeleteBook.setBounds(168, 220, 135, 24);
		contentPane.add(btnDeleteBook);
		
		JButton btnChangeCopiesNumber = new JButton("Modifica numero copie");
		btnChangeCopiesNumber.setBounds(326, 184, 187, 24);
		contentPane.add(btnChangeCopiesNumber);
	}
}
