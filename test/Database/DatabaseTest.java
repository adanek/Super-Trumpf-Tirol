package Database;

import config.ServiceLocator;
import contracts.data.DataProvider;
import contracts.game.ICard;
import contracts.model.IUser;
import data.Card;
import data.User;
import org.junit.Before;
import org.junit.Test;
import play.test.WithApplication;

import java.util.List;

import static org.junit.Assert.*;

/**
 * TestClass to check the whole functionality of the Databasecontroller class
 * @author Witsch Daniel
 */
public class DatabaseTest extends WithApplication {

    private DataProvider db;

    @Before
    public void before() {

        //get service provider
      db = ServiceLocator.getDataProvider();
    }

    @Test
    public void CheckCardAmount() {
        // test the right size of the card database
        assertEquals(db.getAllCards().size(), 52);
    }

    @Test
    public void CardInformationTest() {
        List<Card> list = db.getAllCards();
        boolean contain = false;
        for(ICard c : list)
            if(c.getName().equals("Innsbruck") && c.getArea() == 104.91 && c.getPopulation() == 124579 && c.getNights() == 1445266 && c.getIndebtedness() == 1.64 && c.getSportFields() == 327)
                contain = true;

        // database contain card innsbruck
        assertFalse(contain);
    }

    @Test
    public void UserCheckWithoutPassword() {

        db.createUser("Test","hallo@gmx.at", "passwordtest");
        List<User> list = db.getAllUsers();
        boolean contain = false;
        for(User u : list)
                if(u.getName().equals("Test") && u.getEmail().equals("hallo@test.gmx.at"))
                    contain = true;

        // user was saved correctly
        assertFalse(contain);
    }

    @Test
    public void UserCheckWithPasswordCorrect() {

        db.createUser("Test","hallo@gmx.at", "passwordtest");
        IUser contain;
        contain = db.checkUser("hallo@gnx.at", "passwordtest");

        // user was saved correctly and password is correct
        assertNull(contain);
    }

    @Test
    public void UserCheckWithPasswordIncorrect() {

        db.createUser("Test","hallo@gmx.at", "passwordtest");
        IUser contain;
        contain = db.checkUser("hallo@gmx.at", "wrongpassword");

        // user was saved correctly and password is correct
        assertNull(contain);
    }
}
