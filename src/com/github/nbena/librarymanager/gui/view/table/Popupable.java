package com.github.nbena.librarymanager.gui.view.table;

import javax.swing.event.PopupMenuListener;

public interface Popupable {
	
	public void addPopupListener(PopupMenuListener listener);
	
	
	public void setSelectedRowToPopup();

}
