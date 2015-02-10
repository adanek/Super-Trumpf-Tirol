package authentication;

import config.ServiceLocator;
import contracts.data.DataProvider;
import contracts.login.LoginHandler;
import contracts.model.IUser;

public class MyLoginHandler implements LoginHandler {

	private DataProvider dp;

	public MyLoginHandler() {
		
		//get data provider
		this.dp = ServiceLocator.getDataProvider();
	}

	@Override
	public IUser authenticate(String email, String password) {

		// return user if found
		return this.dp.checkUser(email, password);
	}

	@Override
	public IUser register(String firstName, String lastName, String email,
			String password) {

		//create User
		return this.dp.createUser(firstName, email, password);
	}
	

}
