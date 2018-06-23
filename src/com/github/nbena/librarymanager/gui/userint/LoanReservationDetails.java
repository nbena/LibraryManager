package com.github.nbena.librarymanager.gui.userint;

import com.github.nbena.librarymanager.core.AbstractReservationWithBook;
import com.github.nbena.librarymanager.gui.view.AbstractReservationWithBookView;

public class LoanReservationDetails extends AbstractDetails implements Details {

	@Override
	public void show() {
		AbstractReservationWithBookView view = new AbstractReservationWithBookView();
		view.setAbstractReservation((AbstractReservationWithBook) super.item);
		view.setVisible(true);
	}

}
