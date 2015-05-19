package data;

import contracts.data.DataProvider;
import contracts.game.ICard;
import contracts.model.IUser;
import play.Play;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by adanek on 19.05.2015.
 */
public class StaticDataController implements DataProvider {

    private HashMap<UUID, Card> cards;
    private HashMap<UUID, Ranking> rankings;

    public StaticDataController() {
        this.cards = new HashMap<>();
        this.rankings = new HashMap<>();
    }


    @Override
    public void load() throws FileNotFoundException {

        File myfile = Play.application().getFile("conf/resources/communeData.csv");

        /**
         * Scanner split the file into the different communes and save them
         */

        try (Scanner s = new Scanner(myfile, "utf-8")) {

            while (s.hasNextLine()) {
                Scanner line = new Scanner(s.nextLine());
                line.useDelimiter(";");
                line.useLocale(Locale.GERMAN);    //float values with , not with dot

                Card c = new Card();
                c.setName(line.next());
                c.setPopulation(line.nextInt());
                c.setArea(line.nextFloat());
                c.setIndebtedness(line.nextFloat());
                c.setNights(line.nextInt());
                c.setSportFields(line.nextInt());

                Ranking rank = new Ranking();
                rank.setName(c.getName());
                rank.setRankPopulation(line.nextInt());
                rank.setRankArea(line.nextInt());
                rank.setRankIndebtedness(line.nextInt());
                rank.setRankNights(line.nextInt());
                rank.setRankSportFields(line.nextInt());

                c.setRanking(rank.getID());
                this.cards.put(c.getID(), c);
                this.rankings.put(rank.getID(), rank);
                line.close();
            }
        }
    }

    @Override
    public ICard getCardByID(UUID id) {
        return cards.get(id);
    }

    @Override
    public Ranking getRankingsByID(UUID id) {
        return rankings.get(id);
    }

    @Override
    public IUser createUser(String name, String email, String password) {
        return null;
    }

    @Override
    public IUser getUserByID(UUID id) {
        return null;
    }

    @Override
    public IUser checkUser(String email, String password) {
        return null;
    }

    @Override
    public List<Card> getAllCards() {

        List<Card> cs = new ArrayList<>(this.cards.values());
        return cs;
    }

    @Override
    public List<Ranking> getAllRankings() {

        List<Ranking> rs = new ArrayList<>(this.rankings.values());
        return rs;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }
}
