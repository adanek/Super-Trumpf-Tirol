package contracts.data;

import contracts.model.User;

public interface DataProvider {
    
    User getUser(String email, String hash);

}
