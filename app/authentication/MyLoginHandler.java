package authentication;

import contracts.login.LoginHandler;

import contracts.data.DataProvider;

import java.util.UUID;

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
