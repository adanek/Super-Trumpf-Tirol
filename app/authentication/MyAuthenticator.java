package authentication;

import controllers.routes;
import play.*;
import play.mvc.*;
import play.mvc.Http.*;

import models.*;

import static play.mvc.Results.redirect;

/**
 * Created by adanek on 14/01/15.
 */
public class MyAuthenticator extends Security.Authenticator {
    
    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("id");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return redirect("/showLoginForm");
    }
}
