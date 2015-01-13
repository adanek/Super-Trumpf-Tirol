package controllers;

import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import views.html.index;

public class Application extends Controller {

    public static Result index() {
        
        String msg = Messages.get("files.test");
        return ok(index.render(msg));
    }

    public static Result langs() {
        return ok(request().acceptLanguages().toString());
        
    }

}
