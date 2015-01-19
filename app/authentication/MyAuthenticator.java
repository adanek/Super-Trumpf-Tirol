package authentication;

import play.mvc.*;
import play.mvc.Http.*;

public class MyAuthenticator extends Security.Authenticator {
    
    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("pid");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        
    	//user is not authorized -> redirect to login form
        return redirect(controllers.routes.LoginController.showLoginForm());
    }
}
