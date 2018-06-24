package com.github.nbena.librarymanager.gui.userint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.github.nbena.librarymanager.core.AbstractReservation;
import com.github.nbena.librarymanager.core.ConsultationReservation;
import com.github.nbena.librarymanager.gui.UserController;
import com.github.nbena.librarymanager.gui.view.ConsultationReservationView;

public class ConsultationReservationDetails extends AbstractDetailsWithController implements Details {

	private ConsultationReservationView view;
	
	public ConsultationReservationDetails(UserController controller) {
		super(controller);
		
		this.view = new ConsultationReservationView();
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
		this.view.setConsultationReservation((ConsultationReservation) super.item);
		this.view.setVisible(true);
	}

}
