package config;

import authentication.MyLoginHandler;
import contracts.data.DataProvider;
import contracts.game.GameHandler;
import contracts.login.LoginHandler;
import data.DatabaseController;

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

	    gh = new mock.GameHandler();
	}

	return gh;
    }

    /**
     * *
     * @return an singelton instance of a loginhandler
     */
    public static LoginHandler getLoginHandler() {

        if(lh == null){
            lh = new MyLoginHandler();
        }
        
        return lh;
    }
}
