package contracts.login;

import java.util.UUID;

import contracts.model.UserI;

/**
 * Created by adanek on 14/01/15.
 */
public interface LoginHandler {
    
    public UserI authenticate(String email, String password);
}
