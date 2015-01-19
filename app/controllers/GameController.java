package controllers;

import authentication.MyAuthenticator;
import config.ServiceLocator;
import contracts.game.GameHandler;
import contracts.game.GameStatus;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;



public class GameController extends Controller{

    // GET /game/
    @Security.Authenticated(MyAuthenticator.class)
    public static Result index() {
    	
    	//get uid from request
    	String pid = request().username();
    	
    	//get database connection
    	//DataProvider dp = DatabaseController.getInstance();
    	
    	//CHANGED CHANGED, Ändern bitte, da game.main.html eine Card will, ich jedoch keine geben kann
        //CHANGED Im der UI benötige keien Zugriff auf die Datensicht, geht nur über Logik
        //return ok(main.render(dp.getAllCards().get(0)));
        return ok(views.html.game.selectModus.render());
    }

    // POST /game/create
    @Security.Authenticated(MyAuthenticator.class)
    public static Result createGame(){

        GameHandler gh = ServiceLocator.getGameHandler();
        String pid = session().get("pid");
        String gid;
        
        try {
            gid = gh.createNewGame(pid);
            session("gid", gid);
        }
        catch(Exception ex)
        {
            return badRequest();
        }
        
        return ok(views.html.game.main.render(gh.getCard(gid, pid)));
    }
    
    // POST /game/play
    public static Result playCard(){

        try {

            String pid = session().get("pid");
            String gid = session().get("gid");
            int cid = Integer.getInteger(request().body().asFormUrlEncoded().get("cid")[0]);

            ServiceLocator.getGameHandler().makeMove(gid, pid, cid);
        }
        catch (Exception ex){
            return badRequest(ex.getMessage());
        }

        return ok();
    }
    
    // GET /game/status
    public static Result getStatus(){       
        
        GameStatus state = null;

        try {
            String pid = session().get("pid");
            String gid = session().get("gid");
            state = ServiceLocator.getGameHandler().getGameStatus(gid, pid);
        }
        catch(Exception ex){
            
            return badRequest(ex.getMessage());
        }
        
        return ok(Json.toJson(state));
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
