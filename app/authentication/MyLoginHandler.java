package authentication;

import contracts.login.LoginHandler;
import contracts.model.User;
import contrats.data.DataProvider;

import javax.activation.DataHandler;
import java.util.UUID;

/**
 * Created by adanek on 14/01/15.
 */
public class MyLoginHandler implements LoginHandler {

    private DataProvider dp;
    
    public MyLoginHandler(){
        
        this.dp=null;
    }
    @Override
    public UUID authenticate(String email, String password) {
        
        //User u = this.dp.getUser(email, password);
        
        return UUID.randomUUID();
    }
}
