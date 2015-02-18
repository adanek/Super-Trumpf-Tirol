package authentication;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

import java.util.UUID;

public class MyAuthenticator extends Security.Authenticator {
    
    @Override
    public String getUsername(Context ctx) {
        String pid = ctx.session().get("pid");
        if(pid == null){
            pid = UUID.randomUUID().toString();
            ctx.session().put("pid", pid);
        }
        return pid;
    }

    @Override
    public Result onUnauthorized(Context ctx) {   
    	
    	//user is not authorized -> redirect to login form
        return redirect(controllers.routes.LoginController.showLoginForm());
    }
}
