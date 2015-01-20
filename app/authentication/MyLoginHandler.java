package authentication;

import config.ServiceLocator;
import contracts.data.DataProvider;
import contracts.login.LoginHandler;
import contracts.model.IUser;

public class MyLoginHandler implements LoginHandler {

    private DataProvider dp;

    public MyLoginHandler() {
	this.dp = ServiceLocator.getDataProvider();
    }

    @Override
    public IUser authenticate(String email, String password) {

	// return user if found
	return this.dp.checkUser(email, password);
    }
}
