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

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.github.nbena.librarymanager.core.Loan;

@SuppressWarnings("serial")
public class LoanView extends BasicBookView implements DetailsViewable {
	
	private JTextField textFieldStart;
	private JTextField textFieldEnd;
	private JTextField textFieldRenewable;
	
	private JButton btnRenew;
	private JLabel lblRenew;
	private JLabel lblLate;
	
	
	public void setItem(Object item){
		Loan l = (Loan) item;
		super.setBook(l.getCopy());
		this.textFieldStart.setText(l.getStart().toString());
		this.textFieldEnd.setText(l.getEnd().toString());
		this.textFieldRenewable.setText(Boolean.toString(l.isRenewAvailable()));
		
		this.btnRenew.setEnabled(l.isRenewAvailable());
		
		if(l.isInLate()){
			this.lblLate.setText(IN_LATE);
			this.lblLate.setBackground(Color.RED);
		}else{
			this.lblLate.setVisible(false);
		}
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
	
	public void setRenewEnabled(boolean enabled){
		this.btnRenew.setEnabled(enabled);
	}
	
	@Override
	public void reset(){
		super.reset();
		this.textFieldStart.setText("");
		this.textFieldEnd.setText("");
		this.textFieldRenewable.setText("");
	}	
	
	private static final String IN_LATE = "In ritardo";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LoanView dialog = new LoanView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public LoanView() {
		super();
		buttonPane.setLocation(12, 238);
		
		JLabel lblStart = new JLabel("Inizio");
		lblStart.setBounds(12, 171, 97, 14);
		super.contentPanel.add(lblStart);
		
		this.textFieldStart = new JTextField();
		this.textFieldStart.setBounds(136, 171, 97, 18);
		this.textFieldStart.setColumns(10);
		
		JLabel lblEnd = new JLabel("Max fine");
		lblEnd.setBounds(12, 196, 97, 14);
		super.contentPanel.add(lblEnd);
		
		this.textFieldEnd = new JTextField();
		this.textFieldEnd.setBounds(136, 196, 97, 18);
		this.textFieldEnd.setColumns(10);
		
		lblRenew = new JLabel("Rinnovabile?");
		lblRenew.setBounds(12, 221, 97, 14);
		super.contentPanel.add(lblRenew);
		
		this.textFieldRenewable = new JTextField();
		this.textFieldRenewable.setBounds(136, 221, 97, 18);
		this.textFieldRenewable.setColumns(10);	
		
		this.lblLate = new JLabel("");
		this.lblLate.setBounds(340, 221, 97, 18);
		this.lblLate.setOpaque(true);
		this.contentPanel.add(this.lblLate);	
		
		super.contentPanel.add(this.textFieldStart);
		super.contentPanel.add(this.textFieldEnd);
		super.contentPanel.add(this.textFieldRenewable);
		
		this.btnRenew = new JButton("Rinnova");
		this.btnRenew.setBounds(240, 221, 97 , 18);
		
		super.contentPanel.add(this.btnRenew);
		
		this.textFieldStart.setEditable(false);
		this.textFieldEnd.setEditable(false);
		this.textFieldRenewable.setEditable(false);
		
		super.setBounds(100, 100, 460, 330);
		
		Rectangle old = super.buttonPane.getBounds();
		
		super.buttonPane.setBounds(old.x, old.y+20, old.width, old.height);
		
//		super.addActionListenerOk(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				setVisible(false);
//				dispose();
//			}
//			
//		});
		
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
