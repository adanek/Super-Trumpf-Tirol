package contracts.data;

import java.io.FileNotFoundException;
import java.util.UUID;

import contracts.game.ICard;
import contracts.model.IUser;
import data.Ranking;

/**
 * 
 * @author Witsch Daniel
 *
 */
public interface DataProvider {

    void load() throws FileNotFoundException;

    ICard getCardByID(UUID id);

    Ranking getRankingsByID(UUID id);

    IUser createUser(String name, String email, String password);

    IUser getUserByID(UUID id);

    IUser checkUser(String email, String password);

}
