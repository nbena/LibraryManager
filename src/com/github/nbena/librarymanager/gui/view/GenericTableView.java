package com.github.nbena.librarymanager.gui.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

import java.awt.GridLayout;
import java.awt.event.MouseListener;

import javax.swing.JScrollBar;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class GenericTableView extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	
	public void setTableModel(AbstractTableModel model){
		this.table.setModel(model);
	}
	
	public void setTableMouseListener(MouseListener listener){
		this.table.addMouseListener(listener);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GenericTableView dialog = new GenericTableView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public GenericTableView() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
		{
			table = new JTable();
			contentPanel.add(table);
		}
		{
			JScrollBar scrollBar = new JScrollBar();
			contentPanel.add(scrollBar);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
