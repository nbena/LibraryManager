package com.github.nbena.librarymanager.gui;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;

public class AbstractControllerTest {
	
	private void runTestDate(String date, LocalDate expected){
		LocalDate got = AbstractController.parseDate(date);
		assertTrue(got.equals(expected));
	}

	@Test
	public void testDatePickerOkSlash() {
		String date = "16/03/2199";
		LocalDate expected = LocalDate.of(2199, Month.MARCH, 16);
		this.runTestDate(date, expected);
	}
	
	@Test
	public void testDatePickerOkDash() {
		String date = "16-03-2199";
		LocalDate expected = LocalDate.of(2199, Month.MARCH, 16);
		this.runTestDate(date, expected);
	}
	
	@Test(expected = NumberFormatException.class)
	public void testDateFail(){
		String date = "junit";
		LocalDate expected = null;
		this.runTestDate(date, expected);
	}
	
	@Test(expected = NullPointerException.class)
	public void testDateNull(){
		String date = "";
		LocalDate expected = LocalDate.of(1999, Month.MARCH, 16);
		this.runTestDate(date, expected);
	}
	
	@Test(expected = NumberFormatException.class)
	public void testDateBefore(){
		String date = "16/03/1999";
		LocalDate expected = LocalDate.of(1999, Month.MARCH, 16);
		this.runTestDate(date, expected);
	}

}
