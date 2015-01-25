package config;

import authentication.MyLoginHandler;
import contracts.data.DataProvider;
import contracts.game.GameHandler;
import contracts.login.LoginHandler;
import data.Card;
import data.DatabaseController;
import model.Logic;
import play.Logger;

import java.util.List;

/**
 * Simulates a simple DependencyInjectionContainer.
 */
public class ServiceLocator {

    private static DataProvider db = null;
    private static GameHandler gh = null;
    private static LoginHandler lh;
    /**
     * Returns the dataprovider*
     * 
     * @return an singelton instance of an dataprovider
     */
    public static DataProvider getDataProvider() {
        
	if (db == null) {

        Logger.info("Dataprovider initialized");
        db = DatabaseController.getInstance();
	}

	return db;
    }

    /**
     * Returns an singelton instance of the Gamehandler*
     * 
     * @return an singelton instance of the Gamehandler
     */
    public static GameHandler getGameHandler() {
	if (gh == null) {
        
        Logger.info("GameHandler initialized");
        List<Card> allCards = ServiceLocator.getDataProvider().getAllCards();
        Card[] cards = allCards.toArray(new Card[allCards.size()]);
        gh= new Logic(cards);
	}

	return gh;
    }

    /**
     * *
     * @return an singelton instance of a loginhandler
     */
    public static LoginHandler getLoginHandler() {

        if(lh == null){
            Logger.info("LoginHandler initialized");
            lh = new MyLoginHandler();
        }
        
        return lh;
    }
}
