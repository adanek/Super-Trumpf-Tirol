package contracts.login;

import java.util.UUID;

/**
 * Created by adanek on 14/01/15.
 */
public interface LoginHandler {
    
    public UUID authenticate(String email, String password);
}
