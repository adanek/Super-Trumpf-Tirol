package contracts.model;

import java.util.UUID;

/**
 * Created by adanek on 14/01/15.
 */
public interface User {
    
    public UUID getID();
    public String getName();
    public String getEmail();
}
