package com.github.nbena.librarymanager.gui.librarianint;

import java.sql.SQLException;

import com.github.nbena.librarymanager.core.LibraryManagerException;

@Deprecated
public interface FiveArgsFunction<A, B, C, D, E> {
	
	public Object apply(A a, B b, C c, D d, E e) throws SQLException, LibraryManagerException;
}
