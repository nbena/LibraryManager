package com.github.nbena.librarymanager.gui.view;

import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.github.nbena.librarymanager.gui.view.table.Popupable;

import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class GenericUserTableView extends AbstractTableView implements Popupable, VisibleView {

	// private final JPanel contentPanel = new JPanel();
	// private JTable table;
	// private JPopupMenu menu;
	private JMenuItem mntmViewDetails;
	private JMenuItem mntmCancel;
	private JMenuItem mntmReserve;
	
	
	public void setMenuItemDetailsEnabled(boolean enabled){
		this.mntmViewDetails.setEnabled(enabled);
		if (enabled){
			this.menu.setEnabled(true);
		}
	}
	
	public void setMenuItemCancelEnabled(boolean enabled){
		this.mntmCancel.setEnabled(enabled);
		if (enabled){
			this.menu.setEnabled(true);
		}
	}
	
	public void setMenuItemReserveEnabled(boolean enabled){
		this.mntmReserve.setEnabled(enabled);
		if (enabled){
			this.menu.setEnabled(true);
		}
	}
	
	public void addMenuItemDetailsListener(ActionListener listener){
		this.mntmViewDetails.addActionListener(listener);
	}
	
	public void addMenuItemCancelListener(ActionListener listener){
		this.mntmCancel.addActionListener(listener);
	}
	
	public void addMenuItemReserveListener(ActionListener listener){
		this.mntmReserve.addActionListener(listener);
	}


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GenericUserTableView dialog = new GenericUserTableView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public GenericUserTableView() {
//		setBounds(100, 100, 450, 334);
//		getContentPane().setLayout(new BorderLayout());
//		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
//		getContentPane().add(contentPanel, BorderLayout.CENTER);
//		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
//		
//		table = new JTable();
//		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//		
//		JScrollPane scrollPane = new JScrollPane();
//		contentPanel.add(scrollPane);
//		
//		scrollPane.setViewportView(this.table);
//		
//		
//		JPanel buttonPane = new JPanel();
//		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
//		buttonPane.setBounds(0, 177, 370, 43);
//		getContentPane().add(buttonPane, BorderLayout.SOUTH);
//			
//		JButton okButton = new JButton("Ok");
//		buttonPane.add(okButton);
//		
//		okButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				setVisible(false);
//				dispose();
//			}
//		});
		
		this.menu = new JPopupMenu();
		
		this.mntmViewDetails = new JMenuItem("Dettagli");
		this.mntmCancel = new JMenuItem("Cancella");
		this.mntmReserve = new JMenuItem("Prenota");
		
		this.menu.add(this.mntmViewDetails);
		this.menu.add(this.mntmCancel);
		this.menu.add(this.mntmReserve);
		
		this.table.setComponentPopupMenu(this.menu);
		
	}

}
