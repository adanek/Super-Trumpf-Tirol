package contracts.model;

import java.util.UUID;

/**
 * interface for the model class user
 * @author Witsch Daniel
 */
public interface IUser {

    UUID getID();

    String getName();

    String getEmail();

    String getPassword();
}
