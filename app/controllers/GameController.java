package controllers;

import authentication.MyAuthenticator;
import com.fasterxml.jackson.databind.JsonNode;
import config.ServiceLocator;
import contracts.game.GameStatus;
import contracts.game.ICard;
import contracts.game.IGameHandler;
import controllers.helpers.CardAjax;
import controllers.helpers.GameStateAjax;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Security;

public class GameController extends Controller {

    // GET /game
    @Security.Authenticated(MyAuthenticator.class)
    public static Result index() {
        String pid = session().get("pid");
        String gid = session().get("gid");

        IGameHandler gh = ServiceLocator.getGameHandler();
        return ok(views.html.game.main.render((pid != null), gh.getCard(gid, pid)));
    }

    // GET /game/selectMode
    @Security.Authenticated(MyAuthenticator.class)
    public static Result selectMode() {

        return ok(views.html.game.mode.render());
    }

    // POST /game/single/create
    @Security.Authenticated(MyAuthenticator.class)
    public static Result createSinglePlayerGame() {

        IGameHandler gh = ServiceLocator.getGameHandler();
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

    // POST /game/multi/create
    @Security.Authenticated(MyAuthenticator.class)
    public static Result registerForMultiPlayerGame() {

        IGameHandler gh = ServiceLocator.getGameHandler();
        String pid = session().get("pid");
        gh.registerForMultiPlayerGame(pid);

        return ok(views.html.game.waiting.render(pid != null));
    }
    
    // POST /game/multi/abort
    @Security.Authenticated(MyAuthenticator.class)
    public static Result unregisterForMultiPlayerGame(){

        IGameHandler gh = ServiceLocator.getGameHandler();
        String pid = session().get("pid");
        
        gh.unregisterForMultiPlayerGame(pid);
        
        return Results.noContent();        
    }

    // POST /game/multi/ready
    @Security.Authenticated(MyAuthenticator.class)
    public static Result tryStartMultiPlayerGame() {

        Result res;
        IGameHandler gh = ServiceLocator.getGameHandler();
        String pid = session().get("pid");
        String gid = gh.getMultiPlayerGameId(pid);

        if (gid == null)
            res = noContent();
        else {

            session("gid", gid);
            res = redirect(controllers.routes.GameController.index());
        }
        
        return res;
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

        JsonNode jsonNode = Json.toJson(new GameStateAjax(state));
        return ok(jsonNode);
    }

    // GET  /game/card
    @Security.Authenticated(MyAuthenticator.class)
    public static Result getCard() {

        ICard card;

        try {
            String pid = session().get("pid");
            String gid = session().get("gid");

            IGameHandler gameHandler = ServiceLocator.getGameHandler();
            card = gameHandler.getCard(gid, pid);
        } catch (Exception ex) {
            return badRequest();
        }
        response().setContentType("text/json; charset=utf-8");

        CardAjax cardAjax = new CardAjax(card);
        JsonNode jsonNode = Json.toJson(cardAjax);
        return ok(jsonNode, "utf-8");
    }


    // POST /game/competitorcard
    @Security.Authenticated(MyAuthenticator.class)
    public static Result getCompetitorCard() {

        ICard card;

        try {
            String pid = session().get("pid");
            String gid = session().get("gid");

            card = ServiceLocator.getGameHandler().getCardFromCompetitor(gid, pid);
        } catch (IllegalStateException e) {
            return badRequest();
        }

        return ok(Json.toJson(new CardAjax(card)));
    }

    // POST /game/commitcard
    @Security.Authenticated(MyAuthenticator.class)
    public static Result commitCard() {

        try {
            String pid = session().get("pid");
            String gid = session().get("gid");

            ServiceLocator.getGameHandler().commitCard(gid, pid);

        } catch (Exception ex) {
            return badRequest();
        }

        return ok();
    }

    // POST /game/commitround
    @Security.Authenticated(MyAuthenticator.class)
    public static Result commitRound() {

        try {
            String pid = session().get("pid");
            String gid = session().get("gid");

            ServiceLocator.getGameHandler().commitRound(gid, pid);

        } catch (Exception ex) {
            return badRequest();
        }

        return ok();
    }

    // POST /game/abort
    @Security.Authenticated(MyAuthenticator.class)
    public static Result abortGame() {

        try {
            String pid = session().get("pid");
            String gid = session().get("gid");

            ServiceLocator.getGameHandler().abortGame(gid, pid);
            session().remove("gid");

        } catch (Exception ex) {
            return badRequest();
        }

        return ok();
    }
}
