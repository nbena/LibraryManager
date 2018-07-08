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
import javax.swing.JPopupMenu;

import com.github.nbena.librarymanager.gui.view.table.Popupable;

import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class ConsultationInProgressView extends AbstractTableView implements VisibleView,
	Popupable {

	// private final JPanel contentPanel = new JPanel();
	
	private JMenuItem mntmDelivery;
	

	public void addMenuItemDeliveryListener(ActionListener listener){
		this.mntmDelivery.addActionListener(listener);
	}
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ConsultationInProgressView dialog = new ConsultationInProgressView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ConsultationInProgressView() {
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
		
		this.menu = new JPopupMenu();
		
		this.mntmDelivery = new JMenuItem("Consegna consultazione");
		this.menu.add(this.mntmDelivery);
		
		this.table.setComponentPopupMenu(this.menu);
	}


}
