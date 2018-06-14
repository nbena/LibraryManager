package com.github.nbena.librarymanager.gui.view;

import com.github.nbena.librarymanager.core.User;
import java.util.List;

public interface SearchableBookUser extends SearchableBook {
	
	public void setUsers(List<User> user);
	
	public User getSelectedUser();
	
	public void setUserPanelEnabled(boolean enabled);

}
