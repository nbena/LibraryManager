package com.github.nbena.librarymanager.gui.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.border.TitledBorder;

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
	
	private JButton btnViewLoan;
	private JButton btnViewSeatReservation;
	private JButton btnViewLoanReservation;
	private JButton btnViewConsultationReservation;
	private JButton btnSearch;
	private JButton btnNewSeatReservation;
	private JButton btnDeregister;
	private JButton btnLogout;
	
	public void addActionListenerViewLoan(ActionListener listener){
		this.btnViewLoan.addActionListener(listener);
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

	/**
	 * Create the frame.
	 */
	public UserView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 511, 296);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Cerca", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(12, 12, 209, 66);
		contentPane.add(panel);
		panel.setLayout(null);
		
		btnSearch = new JButton("Cerca");
		btnSearch.setBounds(12, 30, 171, 24);
		panel.add(btnSearch);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Prenota posto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(12, 113, 209, 66);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		btnNewSeatReservation = new JButton("Prenota posto");
		btnNewSeatReservation.setBounds(12, 30, 171, 24);
		panel_1.add(btnNewSeatReservation);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "La tua situazione", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(233, 12, 259, 167);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		btnViewLoan = new JButton("Vedi i tuoi prestiti");
		btnViewLoan.setBounds(12, 25, 221, 24);
		panel_2.add(btnViewLoan);
		
		btnViewLoanReservation = new JButton("Vedi prenotazioni libri");
		btnViewLoanReservation.setBounds(12, 61, 221, 24);
		panel_2.add(btnViewLoanReservation);
		
		btnViewSeatReservation = new JButton("Vedi prenotazioni posti");
		btnViewSeatReservation.setBounds(12, 97, 221, 24);
		panel_2.add(btnViewSeatReservation);
		
		btnViewConsultationReservation = new JButton("Vedi consultazioni prenotate");
		btnViewConsultationReservation.setBounds(12, 130, 221, 24);
		panel_2.add(btnViewConsultationReservation);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Registrazione", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(12, 191, 480, 60);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		btnDeregister = new JButton("Annulla registrazione");
		btnDeregister.setBounds(238, 24, 221, 24);
		panel_3.add(btnDeregister);
		
		btnLogout = new JButton("Logout");
		btnLogout.setBounds(12, 24, 174, 24);
		panel_3.add(btnLogout);
	}
}
