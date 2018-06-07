package com.github.nbena.librarymanager.run;

import java.sql.SQLException;

import javax.swing.UIManager;

import com.github.nbena.librarymanager.core.User;
import com.github.nbena.librarymanager.gui.LibrarianModel;
import com.github.nbena.librarymanager.gui.StartupLogin;
import com.github.nbena.librarymanager.gui.UserController;
import com.github.nbena.librarymanager.gui.UserModel;
import com.github.nbena.librarymanager.gui.view.UserView;
import com.github.nbena.librarymanager.man.LibraryManager;
import com.github.nbena.librarymanager.utils.Hash;

public class Main {
	
	private LibraryManager manager;
	
	public Main(boolean test) throws ClassNotFoundException, SQLException{
		String uri = "localhost:5435/docker";
		if (!test){
			uri = "localhost:5434/docker";
		}
		this.manager = new LibraryManager(uri, "docker", "docker");
	}
	
	public void user(User user){
		UserModel model = new UserModel(this.manager);
		model.setUser(user);
		// boolean result = model.authenticate(user);
		//if (result){
			UserView view = new UserView();
			/*UserController controller = */new UserController(model, view);
		// }
	}
	
	public void librarian(){
		LibrarianModel model = new LibrarianModel(this.manager);
	}
	
	public void turnstile(){
		
	}

	public static void main(String[] args) throws Exception {
		Main main = null;
		try {
			main = new Main(true);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		StartupLogin startup = new StartupLogin();
		
		String [] credentials;
		User user = null;
		boolean loop = true;
		boolean authenticated = false;
		while (loop){
			credentials = startup.getCredentials();
			if (credentials == null){
				loop = false;
			}else{
				user = new User();
				user.setEmail(credentials[0]);
				user.setHashedPassword(Hash.hash(credentials[1]));
				user = main.manager.authenticateUser(user);
				if (user!=null){
					loop = false;
					authenticated = true;
				}
			}
			startup.clear();
		}
		
		if (!authenticated){
			System.exit(1);
		}
		
		if(args.length == 0){
			main.user(user);
		}else{
			if (args[0] == "user"){
				main.user(user);
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
