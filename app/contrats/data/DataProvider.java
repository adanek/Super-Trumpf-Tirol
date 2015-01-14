package contrats.data;

import contracts.model.User;

/**
 * Created by adanek on 14/01/15.
 */
public interface DataProvider {
    
    User getUser(String email, String hash);

}
