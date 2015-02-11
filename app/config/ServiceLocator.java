package config;

import authentication.MyLoginHandler;
import contracts.data.DataProvider;
import contracts.game.IGameHandler;
import contracts.login.LoginHandler;
import core.GameHandler;
import data.DatabaseController;
import play.Logger;


/**
 * Simulates a simple DependencyInjectionContainer.
 */
public class ServiceLocator {

    private static DataProvider db = null;
    private static IGameHandler gh = null;
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
    public static IGameHandler getGameHandler() {
	if (gh == null) {
        Logger.info("IGameHandler initialized");
        gh= new GameHandler();
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
