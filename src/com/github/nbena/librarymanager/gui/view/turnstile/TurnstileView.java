package com.github.nbena.librarymanager.gui.view.turnstile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.nbena.librarymanager.gui.view.MainableView;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class TurnstileView extends JDialog implements MainableView{
	
	private JButton btnExit;
	private JButton btnEnter;
	private JButton btnPassed;
	private JTextField textFieldStatus;
	private JLabel lblSemaphor;
	private JLabel lblMain;
	
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
	
	private void setSemaphorColor(Color color){
		this.lblSemaphor.setBackground(color);
	}
	
	private void setSemaphorRed(){
		this.lblSemaphor.setText("STOP");
		this.setSemaphorColor(Color.RED);
	}
	
	private void setSemaphorGreen(){
		this.lblSemaphor.setText("PASSA");
		this.setSemaphorColor(Color.GREEN);
	}
	
	private void setPassedButtonEnabled(boolean enabled){
		this.btnPassed.setEnabled(enabled);
	}
	
	public void open(){
		this.setPassedButtonEnabled(true);
		this.setSemaphorGreen();
	}
	
	public void close(){
		this.setPassedButtonEnabled(false);
		this.setSemaphorRed();		
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
		setBounds(100, 100, 309, 210);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		btnEnter = new JButton("Entra");
		btnEnter.setBounds(12, 80, 98, 24);
		contentPanel.add(btnEnter);
		
		btnExit = new JButton("Esci");
		btnExit.setBounds(165, 80, 98, 24);
		contentPanel.add(btnExit);
		
		textFieldStatus = new JTextField();
		textFieldStatus.setEditable(false);
		textFieldStatus.setBounds(12, 50, 114, 18);
		contentPanel.add(textFieldStatus);
		textFieldStatus.setColumns(10);
		
		lblSemaphor = new JLabel("STOP");
		lblSemaphor.setBounds(179, 52, 55, 14);
		contentPanel.add(lblSemaphor);
		lblSemaphor.setOpaque(true);
		
		btnPassed = new JButton("Sono passato");
		btnPassed.setBounds(91, 125, 129, 24);
		contentPanel.add(btnPassed);
		
		btnPassed.setEnabled(false);
		
		lblMain = new JLabel("", SwingConstants.CENTER);
		lblMain.setBounds(12, 12, 285, 14);
		contentPanel.add(lblMain);
		
		this.setSemaphorRed();
		this.textFieldStatus.setText("Chiuso");
	}

	@Override
	public void setMainTitle(String main) {
		this.lblMain.setText(main);
	}
}
