package com.github.nbena.librarymanager.gui.userint;

import com.github.nbena.librarymanager.core.ConsultationReservation;
import com.github.nbena.librarymanager.gui.view.ConsultationReservationView;

public class ConsultationReservationDetails extends AbstractDetails implements Details {

	@Override
	public void show() {
		ConsultationReservationView view = new ConsultationReservationView();
		view.setConsultationReservation((ConsultationReservation) super.item);
		view.setVisible(true);
	}

}
