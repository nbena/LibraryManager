package com.github.nbena.librarymanager.gui.view.turnstile;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class TurnstileView extends JDialog {
	
	private JButton btnExit;
	private JButton btnEnter;
	private JButton btnPassed;
	private JTextField textFieldStatus;
	private JLabel lblSemaphor;
	
	public void addActionListenerEnterButton(ActionListener listener){
		this.btnEnter.addActionListener(listener);
	}
	
	public void addActionListenerExitButton(ActionListener listener){
		this.btnExit.addActionListener(listener);
	}
	
	public void addActionListenerPassedButton(ActionListener listener){
		this.btnPassed.addActionListener(listener);
	}
	
	public void setTextFieldStatusText(String text){
		this.textFieldStatus.setText(text);
	}
	
	public void setSemapherColor(){
		
	}


	private final JPanel contentPanel = new JPanel();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			TurnstileView dialog = new TurnstileView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public TurnstileView() {
		setBounds(100, 100, 472, 411);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		btnEnter = new JButton("Entra");
		btnEnter.setBounds(37, 26, 98, 24);
		contentPanel.add(btnEnter);
		
		btnExit = new JButton("Esci");
		btnExit.setBounds(194, 26, 98, 24);
		contentPanel.add(btnExit);
		
		JLabel lblNewLabel = new JLabel("Stato:");
		lblNewLabel.setBounds(37, 84, 55, 14);
		contentPanel.add(lblNewLabel);
		
		textFieldStatus = new JTextField();
		textFieldStatus.setBounds(179, 82, 114, 18);
		contentPanel.add(textFieldStatus);
		textFieldStatus.setColumns(10);
		
		lblSemaphor = new JLabel("");
		lblSemaphor.setBounds(179, 129, 55, 14);
		contentPanel.add(lblSemaphor);
		
		btnPassed = new JButton("Sono passato");
		btnPassed.setBounds(124, 161, 129, 24);
		contentPanel.add(btnPassed);
	}
}
