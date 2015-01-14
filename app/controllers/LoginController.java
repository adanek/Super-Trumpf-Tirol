package controllers;

import authentication.MyLoginHandler;
import play.mvc.Result;
import play.mvc.Controller;
import play.mvc.Results;
import views.html.*;
import contracts.login.*;
import play.data.*;

import java.util.UUID;

public class LoginController extends Controller{

    //Login
    public static Result login() {
        return ok(login.render(Form.form(Credentials.class)) );
    }
    
    //authenticate
    //POST   /login
    public static Result authenticate() {
        Form<Credentials> loginForm = Form.form(Credentials.class).bindFromRequest();
        Credentials c = loginForm.get();

        LoginHandler lh = new MyLoginHandler();
        UUID id = lh.authenticate(c.email, c.password);
        
        //if(id == null) return unauthorized();

        session().clear();
        session("uid", id.toString());
        
        return redirect("/game/new");
    }
}
