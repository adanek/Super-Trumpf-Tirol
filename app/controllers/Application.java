package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.manual;

public class Application extends Controller {

    // GET /
    public static Result index() {

        return ok(index.render(isSignedIn()));
    }

    // GET /manual
    public static Result manual() {

        return ok(manual.render(isSignedIn()));
    }

    /**
     * Returns if the user is signed in*
     *
     * @return true if the user is signed in
     */
    private static Boolean isSignedIn() {

        return session().get("pid") != null;
    }
}
