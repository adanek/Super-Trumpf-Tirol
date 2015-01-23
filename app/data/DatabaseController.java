package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;

import play.Play;
import contracts.data.DataProvider;
import contracts.game.ICard;
import contracts.model.IUser;

/**
 * This class provides all methods to get access to the database.
 * It is important that this class is a singleton class and the load method should only be once invoked.
 * The class load the content of the Daten.csv into the database and create 3 tables. (User, Card, Ranking)
 * @author Witsch Daniel
 */
public class DatabaseController implements DataProvider {

    private boolean flagLoaded = false;

	/**
	 * private constructor
	 */
	private DatabaseController() {
    }

	/**
	 * singleton pattern
	 */
    private static class DatabaseControllerHolder {
	private final static DatabaseController INSTANCE = new DatabaseController();
    }

    public static DatabaseController getInstance() {
	return DatabaseControllerHolder.INSTANCE;
    }

	/**
	 * load the Daten.csv and save the content into the database
	 * @throws FileNotFoundException when the file is not found
	 */
    public void load() throws FileNotFoundException {
	if (flagLoaded)
	    return;

	File myfile = Play.application().getFile("/app/data/Daten.csv");

		/**
		 * next codeblock open the file and read out the information
		 * this code works in Linux and in Windows
		 */
	StringBuilder content = new StringBuilder();
	try (FileReader rd = new FileReader(myfile);) {
	    int r;
	    while ((r = rd.read()) != -1) {
		content.append((char) r);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}

		/**
		 * Scanner split the file into the different communes and save them
		 */
	try (Scanner s = new Scanner(content.toString());) {

	    long i = 0;
	    while (s.hasNextLine()) {
		Scanner line = new Scanner(s.nextLine());
		line.useDelimiter(";");
		line.useLocale(Locale.GERMAN);	//float values with , not with dot

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

			//save the objects into the database
		c.setRanking(rank.getID());
		c.save();
		rank.save();
		line.close();
	    }
	}

	/**
	 * temporary users
	 */
	this.createUser("admin", "admin@gmx.at", "123456");
	this.createUser("test", "test@gmx.at", "654321");
    }

	/**
	 * create a user and save this new user
	 * the password is hashed and only the hashed value is saved into the database
	 * @param name	name of user
	 * @param email	email of new user
	 * @param password	password of new user
	 * @return	return object of the new user
	 */
    @Override
    public IUser createUser(String name, String email, String password) {
	User newUser = null;
	List<User> userList = User.find.where().eq("name", name).eq("email", email).findList();
	if (userList.size() == 0) {
	    try {
		newUser = new User(name, email, PasswordHash.getSaltedHash(password));	//hash the password
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

	/**
	 * compare the input (login) parameters of the GUI with the registered users in the database
	 * @param email	email of login form
	 * @param password	password of login form
	 * @return return the Object of the user when he is in the database otherwise the method return null
	 */
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
