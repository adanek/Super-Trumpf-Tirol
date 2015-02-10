package contracts.login;

import java.util.UUID;

import contracts.model.IUser;

/**
 * Created by adanek on 14/01/15.
 */
public interface LoginHandler {
    
    public IUser authenticate(String email, String password);
    
    public IUser register(String firstName, String lastName, String email, String password);
}
