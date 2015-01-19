package config;

import contracts.data.DataProvider;
import contracts.game.GameHandler;
import data.DatabaseController;

/**
 * Simulates a simple DependencyInjectionContainer.
 */
public class ServiceLocator {
    
    public static DataProvider db = null;
    public static GameHandler gh = null;

    /**
     * Returns the dataprovider* 
     * @return
     */
    public static DataProvider getDataProvider(){
        
        if (db == null){
            db = new DatabaseController();
        }
        
        return db;
    }

    /**
     * Retruns an singelton instance of the Gamehandler*
     * @return
     */
    public static GameHandler getGameHandler() {
        if (gh == null){
            
            gh = new mock.GameHandler();
        }
        
        return gh;
    }
}
