package controllers;

import java.util.UUID;

import contracts.data.DataProvider;
import contracts.model.UserI;
import data.DatabaseController;
import authentication.MyAuthenticator;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.game;
import play.mvc.Security;

public class GameController extends Controller{

    // GET /game/
    @Security.Authenticated(MyAuthenticator.class)
    public static Result index() {
    	
    	//get uid from request
    	UUID uid = UUID.fromString(request().username());
    	
    	//get database connection
    	DataProvider dp = DatabaseController.getInstance();
    	
    	//call game view with username
        return ok(game.render(dp.getUserByID(uid).getName()));
    }
    
    // POST /game/create
    //@Security.Authenticated(MyAuthenticator.class)
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
