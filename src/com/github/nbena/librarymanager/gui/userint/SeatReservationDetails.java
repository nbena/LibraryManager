package com.github.nbena.librarymanager.gui.userint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import com.github.nbena.librarymanager.core.AbstractReservation;
import com.github.nbena.librarymanager.core.SeatReservation;
import com.github.nbena.librarymanager.gui.UserController;
import com.github.nbena.librarymanager.gui.view.SeatReservationView;

public class SeatReservationDetails extends AbstractDetailsWithController implements Details {
	
	private SeatReservationView view;
	
	public SeatReservationDetails(UserController controller) {
		super(controller);
		
		this.view = new SeatReservationView();
		this.view.addActionListenerCancelReservation(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {	
					
					boolean ok = controller.unreserve((AbstractReservation) item, view, true);
					if (ok){
						view.setVisible(false);
						view.dispose();
					}
				}		
		});
	}

	@Override
	public void show() {
		this.view.setSeatReservation((SeatReservation) super.item);
		this.view.setVisible(true);
	}

}
