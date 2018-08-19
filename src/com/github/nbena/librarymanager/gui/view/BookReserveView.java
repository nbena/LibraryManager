package com.github.nbena.librarymanager.gui.view;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

import com.github.nbena.librarymanager.core.Book;

@SuppressWarnings("serial")
public class BookReserveView extends BasicBookView implements DetailsViewable {
	
	protected JButton btnReserve;

	// private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			BookReserveView dialog = new BookReserveView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addActionListenerReserveButton(ActionListener listener){
		this.btnReserve.addActionListener(listener);
	}
	
	public void setButtonReserveEnabled(boolean enabled){
		this.btnReserve.setEnabled(enabled);
	}

	/**
	 * Create the dialog.
	 */
	public BookReserveView() {
		super();

		super.btnOk.setBounds(106, 12, 81, 24);
		
		// super.btnCancel.setBounds(190, 12, 81, 24);
		
		this.btnReserve = new JButton("Prenota");
		this.btnReserve.setBounds(275, 12, 81, 24);
		super.buttonPane.add(this.btnReserve);
		
	}

	@Override
	public void setItem(Object item) {
		super.setBook((Book) item);
	}

}
