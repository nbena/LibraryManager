package com.github.nbena.librarymanager.gui.userint;

import com.github.nbena.librarymanager.core.SeatReservation;
import com.github.nbena.librarymanager.gui.view.SeatReservationView;

public class SeatReservationDetails extends AbstractDetails implements Details {

	@Override
	public void show() {
		SeatReservationView view = new SeatReservationView();
		view.setSeatReservation((SeatReservation) super.item);
		view.setVisible(true);
	}

}
