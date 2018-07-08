package com.github.nbena.librarymanager.gui.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.AbstractTableModel;

import com.github.nbena.librarymanager.gui.view.table.Popupable;
import com.github.nbena.librarymanager.gui.view.table.SelectableItem;

@SuppressWarnings("serial")
public abstract class AbstractTableView extends JDialog implements Popupable {
	
	protected JTable table;
	protected JPopupMenu menu;
	protected final JPanel contentPanel = new JPanel();
	
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
	
	public void setTableModel(AbstractTableModel model){
		this.table.setModel(model);;
	}
	
	public void addTableMouseListener(MouseListener listener){
		this.table.addMouseListener(listener);
	}
	
	public void addPopupListener(PopupMenuListener listener){
		this.menu.addPopupMenuListener(listener);
	}
	
	public void setPopupEnabled(boolean enabled){
		this.menu.setEnabled(enabled);
	}
	
	protected AbstractTableView(){
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPanel.add(scrollPane);
		
		scrollPane.setViewportView(table);
		
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPane.setBounds(0, 177, 370, 43);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
		JButton okButton = new JButton("Ok");
		buttonPane.add(okButton);
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});	
		
	}


}
