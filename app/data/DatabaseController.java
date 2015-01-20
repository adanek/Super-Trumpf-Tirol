package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import play.Play;
import contracts.data.DataProvider;
import contracts.game.ICard;
import contracts.model.IUser;

public class DatabaseController implements DataProvider {

    private boolean flagLoaded = false;

    private DatabaseController() {
    }

    private static class DatabaseControllerHolder {
	private final static DatabaseController INSTANCE = new DatabaseController();
    }

    public static DatabaseController getInstance() {
	return DatabaseControllerHolder.INSTANCE;
    }

    public void load() throws FileNotFoundException {
	if (flagLoaded)
	    return;

	File myfile = Play.application().getFile("/app/data/Daten.csv");

	try (Scanner s = new Scanner(myfile);) {
	    long i = 0;
	    while (s.hasNextLine()) {
		Scanner line = new Scanner(s.nextLine());
		line.useDelimiter(";");

		Card c = new Card();
		c.setName(line.next());
		c.setPopulation(line.nextInt());
		c.setArea(line.nextFloat());
		c.setIndebtedness(line.nextFloat());
		c.setNights(line.nextInt());
		c.setSportFields(line.nextInt());
		c.save();

		Ranking rank = new Ranking();
		rank.setName(c.getName());
		rank.setRankPopulation(line.nextInt());
		rank.setRankArea(line.nextInt());
		rank.setRankIndebtedness(line.nextInt());
		rank.setRankNights(line.nextInt());
		rank.setRankSportFields(line.nextInt());
		rank.save();
		line.close();
	    }
	} catch (FileNotFoundException e) {
	    throw new FileNotFoundException("file can't be found");
	}

	/**
	 * temporary users
	 */
	this.createUser("admin", "admin@gmx.at", "123456");
	this.createUser("admin", "sdfad", "admin");
	this.createUser("test", "test@gmx.at", "654321");
    }

    @Override
    public IUser createUser(String name, String email, String password) {
	User newUser = null;
	List<User> userList = User.find.where().eq("name", name).eq("email", email).findList();
	if (userList.size() == 0) {
	    try {
		newUser = new User(name, email, PasswordHash.getSaltedHash(password));
	    } catch (Exception e) {
		System.out.println(e);
	    }
	    newUser.save();
	}
	return newUser;
    }

    public List<Card> getAllCards() {
	return Card.find.all();
    }

    @Override
    public ICard getCardByID(UUID id) {
	return Card.find.byId(id);
    }

    public List<Ranking> getAllRankings() {
	return Ranking.find.all();
    }

    @Override
    public Ranking getRankingsByID(UUID id) {
	return Ranking.find.byId(id);
    }

    public List<User> getAllUsers() {
	return User.find.all();
    }

    @Override
    public IUser getUserByID(UUID id) {
	return User.find.byId(id);
    }

    @Override
    public IUser checkUser(String email, String password) {
	User toCheck = null;
	List<User> users = User.find.where().eq("email", email).findList();
	for (User u : users)
	    try {
		if (PasswordHash.check(password, u.getPassword()))
		    toCheck = u;
	    } catch (Exception e) {
		System.out.println(e);
	    }
	return toCheck;
    }
}
