package com.github.nbena.librarymanager.gui.view;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.awt.Color;
import java.awt.Dimension;
import java.text.NumberFormat;

import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;

public class DatePickerView extends JPanel {
	
	private JTextField formattedTextFieldDay;
	private JTextField formattedTextFieldMonth;
	private JTextField  formattedTextFieldYear;
	
	
	public int getDay(){
		return Integer.parseInt(this.formattedTextFieldDay.getText());
	}
	
	public int getMonth(){
		return Integer.parseInt(this.formattedTextFieldMonth.getText());
	}

	
	public int getYear(){
		return Integer.parseInt(this.formattedTextFieldYear.getText());
	}
	
	public static void main(String [] args){
		DatePickerView panel = new DatePickerView(true);
		// panel.setMinimumSize(new Dimension(1000, 1000));
		JOptionPane option = new JOptionPane(panel,
				JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		// option.setMinimumSize(new Dimension(500, 500));
		// option.setSize(new Dimension(500, 500));
		JDialog dialog = option.createDialog(null, "ciao");
		dialog.setSize(new Dimension(500,500));
		dialog.setVisible(true);
		
		
//		JOptionPane.showConfirmDialog(null, panel,
//				"It works?", JOptionPane.OK_CANCEL_OPTION);
	}


	/**
	 * Create the panel.
	 */
	public DatePickerView(boolean editable) {
		super();
		setLayout(null);
		
		JLabel lblDay = new JLabel("Giorno:");
		lblDay.setBounds(12, 31, 55, 14);
		add(lblDay);
		
		JLabel lblMonth = new JLabel("Mese");
		lblMonth.setBounds(12, 71, 55, 14);
		add(lblMonth);
		
		JLabel lblYear = new JLabel("Anno");
		lblYear.setBounds(12, 114, 55, 14);
		add(lblYear);
//		
//		NumberFormatter dayFormatter = new NumberFormatter();
//		dayFormatter.setFormat(NumberFormat.getInstance());
//		dayFormatter.setValueClass(Integer.class);
//		dayFormatter.setMinimum(1);
//		dayFormatter.setMaximum(31);
//		dayFormatter.setAllowsInvalid(false);
//		
//		NumberFormatter monthFormatter = new NumberFormatter();
//		monthFormatter.setFormat(NumberFormat.getInstance());
//		monthFormatter.setValueClass(Integer.class);
//		monthFormatter.setMinimum(1);
//		monthFormatter.setMaximum(12);
//		monthFormatter.setAllowsInvalid(false);
//		
//		NumberFormatter yearFormatter = new NumberFormatter();
//		yearFormatter.setFormat(NumberFormat.getNumberInstance());
//		yearFormatter.setValueClass(Integer.class);
//		yearFormatter.setMinimum(1970);
//		yearFormatter.setMaximum(2050);
//		yearFormatter.setAllowsInvalid(false);		
		
		formattedTextFieldDay = new JTextField(/*dayFormatter*/);
		formattedTextFieldDay.setBounds(117, 29, 96, 18);
		add(formattedTextFieldDay);
		
		formattedTextFieldMonth = new JTextField(/*monthFormatter*/);
		formattedTextFieldMonth.setBounds(117, 69, 96, 18);
		add(formattedTextFieldMonth);
		
		formattedTextFieldYear = new JTextField(/*yearFormatter*/);
		formattedTextFieldYear.setBounds(117, 109, 96, 18);
		add(formattedTextFieldYear);
		
		formattedTextFieldDay.setEditable(editable);
		formattedTextFieldMonth.setEditable(editable);
		formattedTextFieldYear.setEditable(editable);
		
//		formattedTextFieldDay.setBackground(Color.WHITE);
//		formattedTextFieldMonth.setBackground(Color.WHITE);
//		formattedTextFieldYear.setBackground(Color.WHITE);


	}
}
