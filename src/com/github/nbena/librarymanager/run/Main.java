package com.github.nbena.librarymanager.run;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import com.github.nbena.librarymanager.core.Librarian;

// import javax.swing.UIManager;

import com.github.nbena.librarymanager.core.User;
import com.github.nbena.librarymanager.gui.LibrarianController;
import com.github.nbena.librarymanager.gui.LibrarianModel;
import com.github.nbena.librarymanager.gui.StartupLogin;
import com.github.nbena.librarymanager.gui.UserController;
import com.github.nbena.librarymanager.gui.UserModel;
import com.github.nbena.librarymanager.gui.view.LibrarianView;
import com.github.nbena.librarymanager.gui.view.UserView;
import com.github.nbena.librarymanager.gui.view.turnstile.TurnstileView;
import com.github.nbena.librarymanager.gui.view.turnstile.TurnstileViewController;
import com.github.nbena.librarymanager.man.LibraryManager;
import com.github.nbena.librarymanager.utils.Hash;

public class Main {
	
	private LibraryManager manager;
	private StartupLogin login;
	
	public Main(boolean test) throws ClassNotFoundException, SQLException{
		String uri = "localhost:5435/docker";
		if (!test){
			uri = "localhost:5434/docker";
		}
		this.manager = new LibraryManager(uri, "docker", "docker");
		this.login = new StartupLogin();
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
	
	public void librarian(Librarian librarian){
		LibrarianModel model = new LibrarianModel(this.manager);
		LibrarianView view = new LibrarianView();
		new LibrarianController(model, view);
	}
	
	public void turnstile(){
		TurnstileView view = new TurnstileView();
		new TurnstileViewController(this.manager, view);
		
	}
	
	private Object[] authenticate(boolean librarian) throws NoSuchAlgorithmException{
		Librarian user = null;
		boolean loop = true;
		boolean authenticated = false;
		String [] credentials;
		
		while (loop){
			credentials = this.login.getCredentials(null);
			if (credentials == null){
				loop = false;
			}else{
						
				if (librarian){
					user = new Librarian();
					user.setEmail(credentials[0]);
					user.setHashedPassword(Hash.hash(credentials[1]));
					user = this.manager.authenticateLibrarian(user);
				}else{
					user = new User();
					user.setEmail(credentials[0]);
					user.setHashedPassword(Hash.hash(credentials[1]));
					user = this.manager.authenticateUser(user);
				}
		
				if (user!=null){
					loop = false;
					authenticated = true;
				}
			}
			this.login.clear();
		}
		return new Object[]{user, authenticated};
	}

	public static void main(String[] args) throws Exception {
		Main main = null;
		try {
			main = new Main(true);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		if (args.length == 0 || args[0].equals("user")){
			Object [] res = main.authenticate(false);
			User user = (User) res[0];
			boolean authenticated = (boolean) res[1];
			if (!authenticated){
				System.exit(1);
			}
			main.user(user);
		}else if (args.length > 0 && args[0].equals("librarian")){
			Object [] res = main.authenticate(true);
			Librarian librarian = (Librarian) res[0];
			boolean authenticated = (boolean) res[1];
			if (!authenticated){
				System.exit(1);
			}
			main.librarian(librarian);
		}else if (args.length > 0 && args[0].equals("turnstile")){
			main.turnstile();
		}else{
			throw new Exception("Unknown arg: "+args[0]);
		}
		

	}

}
