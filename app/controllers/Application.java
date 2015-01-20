package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.manual;
import config.ServiceLocator;
import contracts.game.ICardCategory;

public class Application extends Controller {

    // GET /
    public static Result index() {

	System.out.println("URL: " + ServiceLocator.getDataProvider().getAllCards().get(0).getImageUrl());
	for (ICardCategory c : ServiceLocator.getDataProvider().getAllCards().get(0).getCategories())
	    System.out.println(c.toString());
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
