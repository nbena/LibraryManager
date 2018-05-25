package com.github.nbena.librarymanager.gui.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;

@SuppressWarnings("serial")
public class SeatDateView extends JDialog {
	
	private JFormattedTextField formattedTextFieldDay;
	private JFormattedTextField formattedTextFieldMonth;
	private JFormattedTextField formattedTextFieldYear;
	
	public int getDay(){
		return Integer.parseInt(this.formattedTextFieldDay.getText());
	}
	
	public int getMonth(){
		return Integer.parseInt(this.formattedTextFieldMonth.getText());
	}
	
	public int getYear(){
		return Integer.parseInt(this.formattedTextFieldYear.getText());
	}

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			AskDateView dialog = new AskDateView();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public SeatDateView(boolean showSeat, boolean editable, int seatNumber, int tableNumber) {
		setBounds(100, 100, 235, 201);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblDay = new JLabel("Giorno");
		lblDay.setBounds(12, 12, 55, 14);
		contentPanel.add(lblDay);
		
		JLabel lblMonth = new JLabel("Mese");
		lblMonth.setBounds(12, 43, 55, 14);
		contentPanel.add(lblMonth);
		
		JLabel lblYear = new JLabel("Anno");
		lblYear.setBounds(12, 76, 55, 14);
		contentPanel.add(lblYear);
		
		NumberFormatter dayFormatter = new NumberFormatter();
		dayFormatter.setFormat(NumberFormat.getInstance());
		dayFormatter.setValueClass(Integer.class);
		dayFormatter.setMinimum(1);
		dayFormatter.setMaximum(2);
		dayFormatter.setAllowsInvalid(false);
		
		NumberFormatter monthFormatter = new NumberFormatter();
		monthFormatter.setFormat(NumberFormat.getInstance());
		monthFormatter.setValueClass(Integer.class);
		monthFormatter.setMinimum(1);
		monthFormatter.setMaximum(2);
		monthFormatter.setAllowsInvalid(false);
		
		NumberFormatter yearFormatter = new NumberFormatter();
		yearFormatter.setFormat(NumberFormat.getInstance());
		yearFormatter.setValueClass(Integer.class);
		yearFormatter.setMinimum(1);
		yearFormatter.setMaximum(2);
		yearFormatter.setAllowsInvalid(false);

		formattedTextFieldDay = new JFormattedTextField(dayFormatter);
		formattedTextFieldDay.setBounds(158, 10, 55, 18);
		contentPanel.add(formattedTextFieldDay);
		
		formattedTextFieldMonth = new JFormattedTextField(monthFormatter);
		formattedTextFieldMonth.setBounds(158, 41, 55, 18);
		contentPanel.add(formattedTextFieldMonth);
		
		formattedTextFieldYear = new JFormattedTextField(yearFormatter);
		formattedTextFieldYear.setBounds(158, 74, 55, 18);
		contentPanel.add(formattedTextFieldYear);
		
		formattedTextFieldDay.setEditable(editable);
		formattedTextFieldMonth.setEditable(editable);
		formattedTextFieldYear.setEditable(editable);
		
		// setting the numeric formatters

		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 138, 308, 34);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("OK");
				okButton.setBounds(31, 5, 51, 24);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setBounds(94, 5, 73, 24);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		JLabel lblSeat = new JLabel("Posto,tavolo");
		lblSeat.setBounds(12, 102, 90, 14);
		contentPanel.add(lblSeat);
		
		JLabel lblSeatValue = new JLabel("");
		lblSeatValue.setBounds(158, 104, 55, 14);
		contentPanel.add(lblSeatValue);
		
		lblSeat.setVisible(showSeat);
		lblSeatValue.setVisible(showSeat);
		
		if (showSeat){
			lblSeatValue.setText(String.format("%d,%d", seatNumber, tableNumber));
		}
	}
}
