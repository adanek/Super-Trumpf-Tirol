package controllers;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import authentication.MyAuthenticator;
import config.ServiceLocator;
import contracts.game.GameHandler;
import contracts.game.GameStatus;

public class GameController extends Controller {

    // GET /game
    @Security.Authenticated(MyAuthenticator.class)
    public static Result index() {
        String pid = session().get("pid");
        String gid= session().get("gid");
        
        GameHandler gh = ServiceLocator.getGameHandler();

        return ok(views.html.game.main.render(gh.getCard(gid, pid)));
    }

    // GET /game/selectMode
    @Security.Authenticated(MyAuthenticator.class)
    public static Result selectMode() {

        // get pid from request
        String pid = request().username();

        return ok(views.html.game.mode.render());
    }

    // POST /game/create
    @Security.Authenticated(MyAuthenticator.class)
    public static Result createGame() {

        GameHandler gh = ServiceLocator.getGameHandler();
        String pid = session().get("pid");
        String gid;

        try {
            gid = gh.createNewGame(pid);
            session("gid", gid);
        } catch (Exception ex) {
            return badRequest();
        }

        return redirect(controllers.routes.GameController.index());
    }

    // POST /game/play
    @Security.Authenticated(MyAuthenticator.class)
    public static Result playCard() {

        try {

            String pid = session().get("pid");
            String gid = session().get("gid");
            int cid = Integer.parseInt(request().body().asFormUrlEncoded().get("cid")[0]);

            ServiceLocator.getGameHandler().makeMove(gid, pid, cid);
        } catch (Exception ex) {
            return badRequest(ex.getMessage());
        }

        return ok();
    }

    // GET /game/status
    @Security.Authenticated(MyAuthenticator.class)
    public static Result getStatus() {

        GameStatus state;

        try {
            String pid = session().get("pid");
            String gid = session().get("gid");
            state = ServiceLocator.getGameHandler().getGameStatus(gid, pid);
        } catch (Exception ex) {

            return badRequest(ex.getMessage());
        }

        return ok(Json.toJson(state));
    }

    // POST /game/commit
    public static Result commitRound() {

        return TODO;
    }

    // POST /game/abord
    public static Result abortGame() {

        return TODO;
    }
}
