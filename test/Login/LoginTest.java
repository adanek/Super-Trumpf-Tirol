package Login;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import javafx.util.Callback;

import org.junit.Before;
import org.junit.Test;

import play.mvc.Result;
import authentication.MyLoginHandler;
import config.ServiceLocator;
import contracts.data.DataProvider;
import contracts.login.LoginHandler;
import contracts.model.IUser;

import play.test.*;
import play.test.Helpers.*;

public class LoginTest extends WithApplication {

	private DataProvider db;

	@Before
	public void before() {
		
		//get service provider
		db = ServiceLocator.getDataProvider();
	}

	@Test
	public void LoginSuc() {

		LoginHandler lh = new MyLoginHandler();
		IUser user = lh.authenticate("admin@gmx.at", "123456");

		// correct login
		assertNotEquals(user, null);

	}

	@Test
	public void WrongPW() {

		LoginHandler lh = new MyLoginHandler();

		// password is wrong -> 123456
		IUser user = lh.authenticate("admin@gmx.at", "12345");

		// we must not have a user object
		assertEquals(user, null);

	}

	@Test
	public void WrongUserName() {

		LoginHandler lh = new MyLoginHandler();

		// username is wrong -> admin@gmx.at
		IUser user = lh.authenticate("admin2@gmx.at", "12345");

		// we must not have a user object
		assertEquals(user, null);

	}

	@Test
	public void CheckUserName() {

		LoginHandler lh = new MyLoginHandler();

		IUser user = lh.authenticate("admin@gmx.at", "123456");

		// username is "admin
		assertEquals(user.getName(), "admin");

	}

}
