package contracts.login;

import contracts.model.IUser;

/**
 * Created by adanek on 14/01/15.
 */
public interface LoginHandler {
    
    public IUser authenticate(String email, String password);
}
