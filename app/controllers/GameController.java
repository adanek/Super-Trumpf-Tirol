package controllers;

import authentication.MyAuthenticator;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

public class GameController extends Controller{

    // GET /game/
    @Security.Authenticated(MyAuthenticator.class)
    public static Result index() {

        return ok(views.html.game.selectModus.render());
    }
    
    // POST /game/create
    //@Security.Authenticated(MyAuthenticator.class)
    public static Result createGame(){
    
        String mode = request().body().asFormUrlEncoded().get("mode")[0];
        return ok(mode);
    }
    
    // POST /game/play
    public static Result playCard(){
        
        return TODO;
    }
    
    // GET /game/status
    public static Result getStatus(){
        
        return  TODO;
    }
    
    //POST /game/commit
    public static Result commitRound(){
        
        return TODO;
    }
    
    //POST /game/abord
    public static Result abortGame(){
        
        return TODO;
    }
}
