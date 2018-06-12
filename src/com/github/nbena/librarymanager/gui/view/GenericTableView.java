package com.github.nbena.librarymanager.gui.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.AbstractTableModel;

import com.github.nbena.librarymanager.gui.view.table.Popupable;
import com.github.nbena.librarymanager.gui.view.table.SelectableItem;

import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.event.MouseListener;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class GenericTableView extends JDialog implements Popupable {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JPopupMenu menu;
	private JMenuItem mntmViewDetails;
	private JMenuItem mntmCancel;
	
	public void setTableModel(AbstractTableModel model){
		this.table.setModel(model);
	}
	
	public void addTableMouseListener(MouseListener mouseListener){
		this.table.addMouseListener(mouseListener);
	}
	
	public void setPopupEnabled(boolean enabled){
		this.menu.setEnabled(enabled);
	}
	
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
	
	public void addMenuItemDetailsListener(ActionListener listener){
		this.mntmViewDetails.addActionListener(listener);
	}
	
	public void addMenuItemCancelListener(ActionListener listener){
		this.mntmCancel.addActionListener(listener);
	}
	
	public void addPopupListener(PopupMenuListener listener){
		this.menu.addPopupMenuListener(listener);
	}
	
	/**
	 * This method set the selection on the table to the row
	 * in which the popup has been called. You need to call
	 * this function inside the popupListener.
	 * <code>
	  		popuable.addPopupListener(new PopupMenuListener(){
     
			@Override
			public void popupMenuCanceled(PopupMenuEvent arg0) {
				
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
				
			}

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				popuable.setSelectedRowToPopup();
			}
			
		});
	 * <code>
	 */
	public void setSelectedRowToPopup(){
		int rowAtPoint = this.table.rowAtPoint(SwingUtilities.convertPoint(
				this.menu, MouseInfo.getPointerInfo().getLocation(), this.table));
		
		if (rowAtPoint > -1){
			this.table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
		}
	}
	
	/**
	 * 
	 * @return the current selected item in the table, in the form
	 * of the real object that it's stored inside the table model.
	 */
	public Object getSelectedItem(){
		SelectableItem model = (SelectableItem) this.table.getModel();
		return model.getSelectedItem(this.table.getSelectedRow());
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
		
		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPanel.add(scrollPane);
		
		scrollPane.setViewportView(this.table);
		
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();				
			}
		});
		
		this.menu = new JPopupMenu();
		
		this.mntmViewDetails = new JMenuItem("Dettagli");
		this.mntmCancel = new JMenuItem("Cancella");
		
		this.menu.add(this.mntmViewDetails);
		this.menu.add(this.mntmCancel);
		
		this.table.setComponentPopupMenu(this.menu);
		
	}

}
