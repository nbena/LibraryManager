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

import javax.swing.JDialog;
import javax.swing.JMenuItem;

import com.github.nbena.librarymanager.gui.view.table.Popupable;

import java.awt.event.ActionListener;

@SuppressWarnings("serial")
/**
 * This class is used by the librarian to:
 * <ol>
 * 	<li> view consultations in progress -> delivery
 * 	<li> view consultations reservation by user -> start
 * </ol>
 * @author nicola
 *
 */
public class LibrarianConsultationsTableView extends AbstractTableView implements VisibleView,
	Popupable {

	// private final JPanel contentPanel = new JPanel();
	
	private JMenuItem mntmDelivery;
	private JMenuItem mntmStart;
	

	public void addMenuItemDeliveryListener(ActionListener listener){
		this.mntmDelivery.addActionListener(listener);
	}
	
	public void addMenuItemStartListener(ActionListener listener){
		this.mntmStart.addActionListener(listener);
	}
	
	public void setMenuItemDeliveryEnabled(boolean enabled){
		this.mntmDelivery.setEnabled(enabled);
	}
	
	public void setMenuItemStartEnabled(boolean enabled){
		this.mntmStart.setEnabled(enabled);
	}
	
	
	private static final String MENU_ITEM_DELIVER_CONSULTATION = "Consegna consultazione";
	private static final String MENU_ITEM_DELIVER_LOAN = "Consegna prestito";
	
	private static final String MENU_ITEM_START_CONSULTATION = "Inizia consultazione";
	private static final String MENU_ITEM_START_LOAN = "Inizia prestito";
	
	public void setItemsToLoan(){
		this.mntmStart.setText(MENU_ITEM_START_LOAN);
		this.mntmDelivery.setText(MENU_ITEM_DELIVER_LOAN);
	}
	
	public void setItemsToConsultation(){
		this.mntmStart.setText(MENU_ITEM_START_CONSULTATION);
		this.mntmDelivery.setText(MENU_ITEM_DELIVER_CONSULTATION);
	}
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LibrarianConsultationsTableView dialog = new LibrarianConsultationsTableView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public LibrarianConsultationsTableView() {
//		setBounds(100, 100, 450, 300);
//		getContentPane().setLayout(new BorderLayout());
//		contentPanel.setLayout(new FlowLayout());
//		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
//		getContentPane().add(contentPanel, BorderLayout.CENTER);
//		
//		table = new JTable();
//		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//		
//		JScrollPane scrollPane = new JScrollPane();
//		contentPanel.add(scrollPane);
//		
//		scrollPane.setViewportView(table);
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
		super();
		
		this.mntmDelivery = new JMenuItem();
		this.menu.add(this.mntmDelivery);
		
		this.mntmStart = new JMenuItem();
		this.menu.add(this.mntmStart);
		
	}


//	@Override
//	public Object getSelectedItem(int row) {
//		return super.getSelectedItem();
//	}


}
