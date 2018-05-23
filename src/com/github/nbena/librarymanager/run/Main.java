package com.github.nbena.librarymanager.run;

import java.sql.SQLException;

import javax.swing.UIManager;

import com.github.nbena.librarymanager.gui.LibrarianModel;
import com.github.nbena.librarymanager.gui.UserController;
import com.github.nbena.librarymanager.gui.UserModel;
import com.github.nbena.librarymanager.gui.view.UserView;
import com.github.nbena.librarymanager.man.LibraryManager;

public class Main {
	
	private LibraryManager manager;
	
	public Main() throws ClassNotFoundException, SQLException{
		this.manager = new LibraryManager("localhost:5434/docker", "docker", "docker");
	}
	
	public void user(){
		UserModel model = new UserModel(this.manager);
		UserView view = new UserView();
		UserController controller = new UserController(model, view);
	}
	
	public void librarian(){
		LibrarianModel model = new LibrarianModel(this.manager);
	}
	
	public void turnstile(){
		
	}

	public static void main(String[] args) throws Exception {
		Main main = null;
		try {
			main = new Main();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		if(args.length == 0){
			main.user();
		}else{
			if (args[0] == "user"){
				main.user();
			}else if(args[0] == "librarian"){
				main.librarian();
			}else if(args[0] == "turnstile"){
				main.turnstile();
			}else{
				throw new Exception("Unknown arg: "+args[0]);
			}
		}

	}

}
