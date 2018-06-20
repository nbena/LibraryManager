package com.github.nbena.librarymanager.gui.view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.nbena.librarymanager.core.Book;

import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
/**
 * Basic class to show book's info. Info are not editable.
 * Classes that want to provide editability should extend this one.
 * 
 * @author nicola
 *
 */
public class BasicBookView extends JDialog implements ViewableBook, MainableView {

	// protected final JPanel contentPanel = new JPanel();
	protected JPanel contentPanel;
	protected JPanel buttonPane;
	protected JTextField textFieldTopic;
	protected JTextField textFieldYear;
	protected JTextField textFieldAuthors;
	protected JTextField textFieldTitle;
	
	protected JButton btnOk;
	protected JButton btnCancel;
	
	protected JLabel lblMain;
	
//	public void setTitle(String title){
//		this.textFieldTitle.setText(title);
//	}
//	
//	public void setAuthors(String [] authors){
//		this.textFieldAuthors.setText(Arrays.toString(authors));
//	}
//	
//	public void setTopic(String topic){
//		this.textFieldTopic.setText(topic);
//	}
//	
//	public void setYear(int year){
//		this.textFieldYear.setText(Integer.toString(year));
//	}
//	
//	public void setMainLabelTitle(String main){
//		this.lblMain.setText(main);
//	}
	
	public void addListenerOk(ActionListener listener){
		this.btnOk.addActionListener(listener);
	}
	
	public void addListenerCancel(ActionListener listener){
		this.btnCancel.addActionListener(listener);
	}
	
	public void reset(){
		this.textFieldTitle.setText("");
		this.textFieldAuthors.setText("");
		this.textFieldTopic.setText("");
		this.textFieldYear.setText("");
	}
	
	public void setBook(Book book){
		this.textFieldTitle.setText(book.getTitle());
		this.textFieldAuthors.setText(Arrays.toString(book.getAuthors()));
		this.textFieldTopic.setText(book.getMainTopic());
		this.textFieldYear.setText(Integer.toString(book.getYearOfPublishing()));
	}
	
	@Override
	public void setMainTitle(String main) {
		this.lblMain.setText(main);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			BasicBookView dialog = new BasicBookView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public BasicBookView() {
		
		this.contentPanel = new JPanel();
		
		super.setBounds(100, 100, 450, 241);
		super.getContentPane().setLayout(new BorderLayout());
		this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		super.getContentPane().add(contentPanel, BorderLayout.CENTER);
		this.contentPanel.setLayout(null);
		
		this.buttonPane = new JPanel();
		this.buttonPane.setBounds(0, 157, 450, 45);
		this.contentPanel.add(this.buttonPane);
		this.buttonPane.setLayout(null);
			
		this.btnOk = new JButton("OK");
		this.btnOk.setBounds(163, 12, 51, 24);
		this.buttonPane.add(this.btnOk);
		this.btnOk.setActionCommand("OK");
		super.getRootPane().setDefaultButton(btnOk);
			
			
		this.btnCancel = new JButton("Cancel");
		this.btnCancel.setBounds(226, 12, 73, 24);
		this.buttonPane.add(this.btnCancel);
		this.btnCancel.setActionCommand("Cancel");
			
		
		
		JLabel lblTitle = new JLabel("Titolo");
		lblTitle.setBounds(12, 46, 55, 14);
		this.contentPanel.add(lblTitle);
		
		JLabel lblAuthors = new JLabel("Autori");
		lblAuthors.setBounds(12, 72, 55, 14);
		this.contentPanel.add(lblAuthors);
		
		JLabel lblYear = new JLabel("Anno");
		lblYear.setBounds(12, 98, 55, 14);
		this.contentPanel.add(lblYear);
		
		JLabel lblNewLabel = new JLabel("Argomento");
		lblNewLabel.setBounds(12, 123, 97, 14);
		this.contentPanel.add(lblNewLabel);
		
		textFieldTopic = new JTextField();
		textFieldTopic.setBounds(136, 121, 292, 18);
		contentPanel.add(textFieldTopic);
		textFieldTopic.setColumns(10);
		
		textFieldYear = new JTextField();
		textFieldYear.setBounds(136, 96, 82, 18);
		contentPanel.add(textFieldYear);
		textFieldYear.setColumns(10);
		
		textFieldAuthors = new JTextField();
		textFieldAuthors.setBounds(136, 70, 292, 18);
		contentPanel.add(textFieldAuthors);
		textFieldAuthors.setColumns(10);
		
		textFieldTitle = new JTextField();
		textFieldTitle.setBounds(136, 44, 292, 18);
		contentPanel.add(textFieldTitle);
		textFieldTitle.setColumns(10);
		
		this.lblMain = new JLabel("");
		this.lblMain.setBounds(12, 12, 426, 14);
		this.contentPanel.add(this.lblMain);
		
		this.textFieldAuthors.setEditable(false);
		this.textFieldTitle.setEditable(false);
		this.textFieldTopic.setEditable(false);
		this.textFieldYear.setEditable(false);
	}
}
