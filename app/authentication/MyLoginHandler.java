package authentication;

import contracts.login.LoginHandler;
import contracts.model.IUser;
import contracts.data.DataProvider;

import java.io.FileNotFoundException;
import java.util.UUID;

import data.DatabaseController;

public class MyLoginHandler implements LoginHandler {

	private DataProvider dp;

	public MyLoginHandler() {
		this.dp = DatabaseController.getInstance();
	}

	@Override
	public IUser authenticate(String email, String password) {

		// return user if found
		return this.dp.checkUser(email, password);
	}
}
