package controllers;

import play.mvc.Result;
import play.mvc.Controller;
import play.mvc.Results;
import views.html.*;
import contracts.login.*;
import play.data.*;

public class LoginController extends Controller{

    //Login
    public static Result login() {
        return ok(login.render(Form.form(Credentials.class)) );
    }
    
    //authenticate
    //POST   /login
    public static Result authenticate() {
        Form<Credentials> loginForm = Form.form(Credentials.class).bindFromRequest();
        return ok("OK");
    }
}
