package com.github.nbena.librarymanager.gui.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.github.nbena.librarymanager.core.Loan;

@SuppressWarnings("serial")
public class LoanView extends BasicBookView implements MainableView, ResettableView {
	
	private JTextField textFieldStart;
	private JTextField textFieldEnd;
	private JTextField textFieldRenewable;
	
	private JButton btnRenew;
	private JLabel lblRenew;
	
	
	public void setLoan(Loan l){
		super.setBook(l.getCopy());
		this.textFieldStart.setText(l.getStart().toString());
		this.textFieldEnd.setText(l.getEnd().toString());
		this.textFieldRenewable.setText(Boolean.toString(l.isRenewAvailable()));
	}
	
	public void setRenewsVisible(boolean visible){
		this.btnRenew.setEnabled(visible);
		this.btnRenew.setVisible(visible);
		
		this.lblRenew.setEnabled(visible);
		this.lblRenew.setVisible(visible);
		
		this.textFieldRenewable.setEnabled(visible);
		this.textFieldRenewable.setVisible(visible);
	}
	
	public void addActionListenerRenew(ActionListener listener){
		this.btnRenew.addActionListener(listener);
	}
	
	@Override
	public void reset(){
		super.reset();
		this.textFieldStart.setText("");
		this.textFieldEnd.setText("");
		this.textFieldRenewable.setText("");
	}	
	

	public LoanView() {
		super();
		buttonPane.setLocation(12, 270);
		
		JLabel lblStart = new JLabel("Inizio");
		lblStart.setBounds(12, 175, 97, 14);
		super.contentPanel.add(lblStart);
		
		JLabel lblEnd = new JLabel("Fine");
		lblEnd.setBounds(12, 195, 97, 14);
		super.contentPanel.add(lblEnd);
		
		lblRenew = new JLabel("Rinnovabile?");
		lblRenew.setBounds(12, 215, 97, 14);
		super.contentPanel.add(lblRenew);
		
		
		this.textFieldStart = new JTextField();
		this.textFieldStart.setBounds(136, 175, 97, 18);
		this.textFieldStart.setColumns(10);
		
		this.textFieldEnd = new JTextField();
		this.textFieldEnd.setBounds(136, 195, 97, 18);
		this.textFieldEnd.setColumns(10);
		
		this.textFieldRenewable = new JTextField();
		this.textFieldRenewable.setBounds(136, 215, 97, 18);
		this.textFieldRenewable.setColumns(10);		
		
		super.contentPanel.add(this.textFieldStart);
		super.contentPanel.add(this.textFieldEnd);
		super.contentPanel.add(this.textFieldRenewable);
		
		this.btnRenew = new JButton("Rinnova");
		this.btnRenew.setBounds(240, 213, 97 , 18);
		
		super.contentPanel.add(this.btnRenew);
		
		this.textFieldStart.setEditable(false);
		this.textFieldEnd.setEditable(false);
		this.textFieldRenewable.setEditable(false);
		
		super.setBounds(100, 100, 450, 351);
		
		super.addActionListenerOk(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
			
		});
		
//		super.addActionListenerCancel(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				setVisible(false);
//				dispose();
//			}
//			
//		});		
		
	}
	
}
