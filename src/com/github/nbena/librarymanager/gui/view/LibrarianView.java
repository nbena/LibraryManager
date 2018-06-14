package com.github.nbena.librarymanager.gui.view;

import java.awt.EventQueue;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

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
		
		btnNewNotReservedLoan = new JButton("Nuovo prestito non prenotato");
		btnNewNotReservedLoan.setBounds(12, 24, 226, 24);
		contentPane.add(btnNewNotReservedLoan);
		
		btnNewUser = new JButton("Registra utente");
		btnNewUser.setBounds(12, 184, 135, 24);
		contentPane.add(btnNewUser);
		
		btnNewReservedLoan = new JButton("Nuovo prestito prenotato");
		btnNewReservedLoan.setBounds(12, 68, 226, 24);
		contentPane.add(btnNewReservedLoan);
		
		btnDeliveryLoan = new JButton("Registra consegna prestito");
		btnDeliveryLoan.setBounds(12, 115, 226, 24);
		contentPane.add(btnDeliveryLoan);
		
		btnNewNotReservedConsultation = new JButton("Nuova consultazione non prenotata");
		btnNewNotReservedConsultation.setBounds(287, 24, 280, 24);
		contentPane.add(btnNewNotReservedConsultation);
		
		btnNewReservedConsultation = new JButton("Nuova consultazione prenotata");
		btnNewReservedConsultation.setBounds(287, 68, 280, 24);
		contentPane.add(btnNewReservedConsultation);
		
		btnDeliveryConsultation = new JButton("Registra consegna consultazione");
		btnDeliveryConsultation.setBounds(287, 114, 280, 24);
		contentPane.add(btnDeliveryConsultation);
		
		btnAddBook = new JButton("Aggiungi libro");
		btnAddBook.setBounds(168, 184, 135, 24);
		contentPane.add(btnAddBook);
		
		btnDeleteBook = new JButton("Rimuovi libro");
		btnDeleteBook.setBounds(168, 220, 135, 24);
		contentPane.add(btnDeleteBook);
		
		btnChangeCopiesNumber = new JButton("Modifica numero copie");
		btnChangeCopiesNumber.setBounds(326, 184, 187, 24);
		contentPane.add(btnChangeCopiesNumber);
	}
}
