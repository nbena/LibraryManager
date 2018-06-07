package com.github.nbena.librarymanager.man;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class LibraryManagerTest {
	
	private LibraryManager lm;
	
	public void setUp() throws ClassNotFoundException, SQLException{
			this.lm = new LibraryManager("localhost:5435/docker", "docker", "docker");
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
