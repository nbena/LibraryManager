package com.github.nbena.librarymanager.man;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.nbena.librarymanager.core.Book;

public class DbManagerTest {
	
	private DbManager db;
	private Book [] booksToAdd = new Book[]{
			new Book("Title1", new String[]{
					"Me",
					"You",
				},
					2018,
					"Info",
					"phouse"
					)
	};
	private Book [] booksAdded = new Book[booksToAdd.length];
	

	@Before
	public void setUp() throws Exception {
		db = new DbManager("localhost:5434/docker", "docker", "docker");
	}

	@After
	public void tearDown() throws Exception {
		for (Book b: booksAdded){
			db.deleteBook(b);
		}
		db.close();
	}

	@Test
	public void test() {
		assertTrue(true);
	}
	
	@Test
	public void testAddBooks() throws SQLException{
		int i = 0;
		String query = "select count(*) from book where ";
		for (Book b: booksToAdd){
			Book added = db.addBook(b);
			booksAdded[i] = added;
			query += "id=? or";
			i++;
		}
		query = query.substring(0, query.lastIndexOf("or"));
		Connection c = db.connection;
		PreparedStatement pstmt = c.prepareStatement(query);
		
		for (i=0;i<booksAdded.length;i++){
			pstmt.setInt(i+1, booksAdded[i].getID());
		}
		
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		int count = rs.getInt(1);
		assertTrue(count == booksAdded.length);
	}

}
