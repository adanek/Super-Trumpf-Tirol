package authentication;

import play.mvc.*;
import play.mvc.Http.*;

public class MyAuthenticator extends Security.Authenticator {
    
    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("id");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        
        return redirect(controllers.routes.LoginController.showLoginForm());
        
    }
}
