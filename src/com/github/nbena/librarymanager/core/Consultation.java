package com.github.nbena.librarymanager.core;

import java.time.LocalDateTime;

public class Consultation {
	
	private LocalDateTime start;
	private LocalDateTime end;
	
	public Consultation(LocalDateTime start, LocalDateTime end) {
		this.start = start;
		this.end = end;
	}

	public Consultation() {
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

}
