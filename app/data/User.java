package data;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
import contracts.model.UserI;

/**
 * This class contains the information about a village and the position of the
 * attributes compared to the other one. The instances are also written in a
 * database to save them during the game.
 * 
 * @author Witsch Daniel
 *
 */
@Entity
public class User extends Model implements UserI {

    @Id
    private UUID ID = UUID.randomUUID();
    /** name of user */
    private String name;
    /** email adress of the user */
    private String email;
    /** password of the user in hash */
    private String password;
    /** attribute to make it easier to compute a query in the database */
    protected static Finder<UUID, User> find = new Finder<UUID, User>(UUID.class, User.class);

    /** constructor */
    protected User(String name, String email, String password) {
	super();
	this.name = name;
	this.email = email;
	this.password = password;
    }

    public final UUID getID() {
	return ID;
    }

    public final String getName() {
	return name;
    }

    public final String getEmail() {
	return email;
    }

    public final String getPassword() {
	return password;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((ID == null) ? 0 : ID.hashCode());
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((password == null) ? 0 : password.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	User other = (User) obj;
	if (ID == null) {
	    if (other.ID != null)
		return false;
	} else if (!ID.equals(other.ID))
	    return false;
	if (email == null) {
	    if (other.email != null)
		return false;
	} else if (!email.equals(other.email))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (password == null) {
	    if (other.password != null)
		return false;
	} else if (!password.equals(other.password))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "User [ID=" + ID + ", name=" + name + ", email=" + email + ", password=" + password + "]";
    }

}