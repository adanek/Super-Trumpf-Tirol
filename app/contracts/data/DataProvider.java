package contracts.data;

import java.io.FileNotFoundException;
import java.util.UUID;

import contracts.game.CardI;
import contracts.model.UserI;
import data.Ranking;

public interface DataProvider {

    void load() throws FileNotFoundException;

    CardI getCardByID(UUID id);

    Ranking getRankingsByID(UUID id);

    UserI createUser(String name, String email, String password);

    UserI getUserByID(UUID id);

    UserI checkUser(String name, String password);

}
