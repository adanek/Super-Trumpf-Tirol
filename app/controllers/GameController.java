package controllers;

import play.mvc.Controller;

public class GameController extends Controller{
    
    public static play.mvc.Result NewGame(){
        return ok("New Game started");
    }
    
    public static play.mvc.Result MakeMove(){
        
        return notFound();
    }
    
    public static play.mvc.Result GetStatus(){
        
        return  notFound();        
    }
    
    public static play.mvc.Result CommitRound(){
        
        return notFound();
    }
}
