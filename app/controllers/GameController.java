package controllers;

import authentication.MyAuthenticator;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.game;
import play.mvc.Security;

public class GameController extends Controller{

    // GET /game/
    public static Result index() {

        return ok(game.render());
    }
    
    // POST /game/create
    @Security.Authenticated(MyAuthenticator.class)
    public static Result createGame(){
    
        return play.mvc.Results.TODO;
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
