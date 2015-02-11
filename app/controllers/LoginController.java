package controllers;

import authentication.MyLoginHandler;
import config.ServiceLocator;
import play.Logger;
import play.mvc.Result;
import play.mvc.Controller;
import contracts.login.*;
import contracts.model.IUser;
import play.data.*;

import java.util.Map;

public class LoginController extends Controller{

    //GET /login
    public static Result showLoginForm() {

    	String message = "";
    	
        return ok(views.html.login.render(message));
    }
    
    //GET /register
    public static Result showRegisterForm(){
    	
    	String message = "";
    	
    	return ok(views.html.register.render(message));
    }

    //GET /logout
    public static Result logout(){

		String pid = session().get("pid");
		String gid = session().get("gid");

		Logger.info(String.format("User %s has aborted the game.\n", pid));
		ServiceLocator.getGameHandler().abortGame(gid, pid);

    	//clear session data
		session().clear();
	
		return redirect(routes.Application.index());
    }
    

	// POST /login
	public static Result authenticate() {
		Form<Credentials> loginForm = Form.form(Credentials.class)
				.bindFromRequest();
		
		Map<String,String> data = loginForm.data();
		
		Credentials c = new Credentials();
		
		//get form fields
		c.email    = data.get("email");
		c.password = data.get("password");
		
		//check if user exists
		LoginHandler lh = new MyLoginHandler();
		IUser user = lh.authenticate(c.email, c.password);

		//user does not exist or is unauthorized
		if (user == null) {
			return unauthorized(views.html.login.render("Invalid username or password"));
		}
		
		//store session data
		session().clear();
		session("pid", user.getID().toString());

		//redirect to game
		return redirect(routes.GameController.selectMode());
	}
	
	// POST /register
	public static Result register() {

		Form<RegistrationData> regForm = Form.form(RegistrationData.class)
				.bindFromRequest();
		
		Map<String,String> data = regForm.data();
		
		RegistrationData regData = new RegistrationData();
		
		String message = null;
		
		//get form fields
		regData.password     = data.get("password");
		regData.confpassword = data.get("confpassword");
		
		//die Passwörter stimmen nicht überein
		if(regData.password.equals(regData.confpassword) == false){
			message = "The passwords do not match";
		}
		
		//Passwort oder Passwort confirmation wurde nicht eingegeben
		if(regData.password == null || regData.confpassword == null ||regData.password.equals("") || regData.confpassword.equals("")){
			message = "Enter and confirm password";
		}
		
		regData.email        = data.get("email");
		
		//keine E-Mail Adresse eingegeben
		if (regData.email == null || regData.email.equals("")){
			message = "Enter your E-Mail address";
		}
	
		regData.firstName    = data.get("firstName");
		regData.lastName     = data.get("lastName");
		
		//kein Vor- oder Nachname eingegeben
		if (regData.firstName == null || regData.lastName == null || regData.firstName.equals("") || regData.lastName.equals("")){
			message = "Enter first name and last name";
		}

		//Fehler...
		if(message != null){
			return forbidden(views.html.register.render(message));
		}
		
		//register user
		MyLoginHandler lh = new MyLoginHandler();
		if(lh.register(regData.firstName, regData.lastName, regData.email, regData.password) == null){
			return forbidden(views.html.register.render("User could not be created"));
		}
		
		//redirect to index site
		return redirect(routes.Application.index());
	}
	
}
