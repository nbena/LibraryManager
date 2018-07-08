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

import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class LoansInLateView extends AbstractTableView implements VisibleView {

	private JMenuItem mntmSendMail;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LoansInLateView dialog = new LoansInLateView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void addMenuItemSendMailListener(ActionListener listener){
		this.mntmSendMail.addActionListener(listener);
	}

	/**
	 * Create the dialog.
	 */
	public LoansInLateView() {
		super();
		
		this.mntmSendMail = new JMenuItem("Invia sollecito");
		this.menu.add(this.mntmSendMail);
	}

}
