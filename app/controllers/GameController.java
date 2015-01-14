package controllers;

import authentication.MyAuthenticator;
import play.mvc.Controller;
import play.mvc.Security;

import javax.servlet.annotation.ServletSecurity;

public class GameController extends Controller{
    
    @Security.Authenticated(MyAuthenticator.class)
    public static play.mvc.Result NewGame(){
        return ok("New Game started");
    }
    
    public static play.mvc.Result MakeMove(){
        return TODO;
    }
    
    //GET /game/status
    public static play.mvc.Result GetStatus(){
        
        return  notFound();        
    }
    
    public static play.mvc.Result CommitRound(){
        
        return notFound();
    }
}
