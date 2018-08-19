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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.event.PopupMenuListener;
import javax.swing.table.AbstractTableModel;

import com.github.nbena.librarymanager.gui.view.table.Popupable;
import com.github.nbena.librarymanager.gui.view.table.SelectableItem;

@SuppressWarnings("serial")
public abstract class AbstractTableView extends JDialog implements
	Popupable, MainableView {
	
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
	
	public int getItemsCount(){
		return this.table.getModel().getRowCount();
	}
	
	@Override
	public void setMainTitle(String main) {
		this.setTitle(main);
	}

	protected AbstractTableView(){
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		
		contentPanel.setLayout(gbl_contentPane);
		
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		JScrollPane scrollPane = new JScrollPane();
		
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPanel.add(scrollPane, gbc_scrollPane);
		
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.insets = new Insets(0, 0, 5, 0);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 0;
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
		
		this.menu = new JPopupMenu();
		this.table.setComponentPopupMenu(this.menu);
		
	}


}
