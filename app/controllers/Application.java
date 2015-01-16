package controllers;


import play.mvc.Controller;
import play.mvc.Result;

import views.html.index;
import views.html.manual;
import com.avaje.ebean.Ebean;

public class Application extends Controller {

    public static Result index() {
        
        return ok(index.render());
    }

    public static Result manual() {
        
        return ok(manual.render());
    }
    
    public static Result langs() {
        return ok(request().acceptLanguages().toString());
    }
    
}
