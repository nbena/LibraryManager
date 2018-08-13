package com.github.nbena.librarymanager.gui.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.nbena.librarymanager.core.User;

import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
/**
 * This class is the basic view to show a user.
 * It is not editable
 * 
 * Other classes that want to provide editability should extend this.
 *
 * @author nicola
 *
 */
public class BasicUserView extends JDialog implements ResettableView, MainableView {

	protected final JPanel contentPanel = new JPanel();
	protected final ButtonGroup buttonGroup = new ButtonGroup();
	protected JPanel buttonPane;
	
	protected JRadioButton rdbtnInternalUser;
	protected JRadioButton rdbtnExternalUser;
	
	protected JButton btnOk;
	
	protected JLabel lblMain;
	protected JTextField textFieldEmail;
	protected JTextField textFieldName;
	protected JTextField textFieldSurname;
	protected JPasswordField passwordFieldPassword;
	
	public boolean isInternal(){
		return this.rdbtnInternalUser.isSelected();
	}
	
	public void addActionListenerOk(ActionListener listener){
		this.btnOk.addActionListener(listener);
	}
	
//	public void addActionListenerCancel(ActionListener listener){
//		this.btnCancel.addActionListener(listener);
//	}
	
	public void setMainLabel(String main){
		this.lblMain.setText(main);
	}
	
	public void setUser(User user){
		this.textFieldEmail.setText(user.getEmail());
		this.textFieldName.setText(user.getName());
		this.textFieldSurname.setText(user.getSurname());
		// this.passwordFieldPassword.setText(user.getHashedPassword());
	}
	
	@Override
	public void reset() {
		this.textFieldEmail.setText("");
		this.textFieldName.setText("");
		this.textFieldSurname.setText("");
	}
	
	@Override
	public void setMainTitle(String main) {
		this.lblMain.setText(main);
	}
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			BasicUserView dialog = new BasicUserView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public BasicUserView() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		buttonPane = new JPanel();
		buttonPane.setBounds(0, 222, 450, 46);
		contentPanel.add(buttonPane);
		buttonPane.setLayout(null);
		
		btnOk = new JButton("OK");
		btnOk.setBounds(199, 12, 51, 24);
		buttonPane.add(btnOk);
		
		JLabel lblUserMail = new JLabel("Email");
		lblUserMail.setBounds(12, 36, 80, 14);
		contentPanel.add(lblUserMail);
		
		JLabel lblName = new JLabel("Nome");
		lblName.setBounds(12, 62, 80, 14);
		contentPanel.add(lblName);
		
		JLabel lblSurname = new JLabel("Cognome");
		lblSurname.setBounds(12, 88, 80, 14);
		contentPanel.add(lblSurname);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(12, 117, 80, 14);
		contentPanel.add(lblPassword);
		
		lblMain = new JLabel("", SwingConstants.CENTER);
		lblMain.setBounds(12, 12, 426, 14);
		contentPanel.add(lblMain);
		
		rdbtnInternalUser = new JRadioButton("Interno");
		buttonGroup.add(rdbtnInternalUser);
		rdbtnInternalUser.setBounds(8, 147, 121, 22);
		contentPanel.add(rdbtnInternalUser);
		
		rdbtnExternalUser = new JRadioButton("Esterno");
		rdbtnExternalUser.setBounds(187, 147, 121, 22);
		contentPanel.add(rdbtnExternalUser);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(138, 34, 226, 18);
		contentPanel.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		textFieldName = new JTextField();
		textFieldName.setBounds(138, 60, 226, 18);
		contentPanel.add(textFieldName);
		textFieldName.setColumns(10);
		
		textFieldSurname = new JTextField();
		textFieldSurname.setBounds(138, 86, 226, 18);
		contentPanel.add(textFieldSurname);
		textFieldSurname.setColumns(10);
		
		passwordFieldPassword = new JPasswordField();
		passwordFieldPassword.setBounds(138, 115, 226, 18);
		contentPanel.add(passwordFieldPassword);
		
		this.textFieldEmail.setEditable(false);
		this.textFieldName.setEditable(false);
		this.textFieldSurname.setEditable(false);
		this.passwordFieldPassword.setEditable(true);
		
		this.rdbtnInternalUser.setSelected(true);
	}
}
