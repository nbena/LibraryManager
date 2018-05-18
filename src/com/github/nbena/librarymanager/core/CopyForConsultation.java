package com.github.nbena.librarymanager.core;

public class CopyForConsultation extends Copy {

	private boolean inConsultation;
	
	public CopyForConsultation(String title, String[] authors, int yearsOfPublishing, String mainTopic, String ID) {
		super(title, authors, yearsOfPublishing, mainTopic, ID);
		this.inConsultation = false;
	}

	public boolean isInConsultation() {
		return inConsultation;
	}

	public void setInConsultation(boolean inConsultation) {
		this.inConsultation = inConsultation;
	}

}
