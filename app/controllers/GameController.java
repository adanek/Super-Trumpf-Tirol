package controllers;

import authentication.MyAuthenticator;
import contracts.game.GameState;
import contracts.game.GameStatus;
import mock.GameHandler;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.UUID;

public class GameController extends Controller{

    // GET /game/
    @Security.Authenticated(MyAuthenticator.class)
    public static Result index() {
        
        return ok(views.html.game.selectModus.render());
    }
    
    // POST /game/create
    //@Security.Authenticated(MyAuthenticator.class)
    public static Result createGame(){
        
        UUID pid = UUID.fromString(session().get("uid"));
        contracts.game.GameHandler gh = new GameHandler();
        
        try {
            UUID gid = gh.createNewGame(pid);
            session("gid", gid.toString());
        }
        catch(Exception ex) {
            return badRequest();
        }
        
        return ok(views.html.game.main.render(gh.getCard(null, null)));
    }
    
    // POST /game/play
    public static Result playCard(){

        try {
            UUID pid = UUID.fromString(session().get("uid"));
            UUID gid = UUID.fromString(session().get("gid"));
            int cid = Integer.parseInt(request().body().asFormUrlEncoded().get("cid")[0]);
        
            contracts.game.GameHandler gh = new GameHandler();
            gh.makeMove(gid, pid, cid);
        }
        catch (Exception ex)
        {
            return badRequest();
        }
        
        return ok();
    }
    
    // GET /game/status
    public static Result getStatus(){

        GameStatus state;
        
        try {
            UUID gid = UUID.fromString(session().get("gid"));
            UUID pid = UUID.fromString(session().get("uid"));
            contracts.game.GameHandler gh = new GameHandler();            
   
            state = gh.getGameStatus(gid, pid);
        }
        catch (Exception ex){
            return badRequest();
        }
        
        return  ok(Json.toJson(state));
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
