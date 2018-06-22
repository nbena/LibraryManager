package com.github.nbena.librarymanager.gui.userint;

public abstract class AbstractDetails implements Details {
	
	protected Object item;

	@Override
	public void setItem(Object item) {
		this.item = item;
	}

}
