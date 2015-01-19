package data;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
import contracts.game.ICard;

/**
 * Class to save the data about the communes (cards) in a database. H2 is a
 * in-memory database and with Ebean we can manage the communication with H2.
 * 
 * @author Witsch Daniel
 *
 */
@Entity
public class Card extends Model implements ICard {

    @Id
    public UUID ID = UUID.randomUUID();
    /** name of commune */
    private String name;
    /** population of last census */
    private int population;
    /** size of the whole town */
    private float area;
    /** indebtedness in percentage */
    private float indebtedness;
    /**
     * nights in hotels, apartements and other touristic overnight possibilities
     */
    private int nights;
    /** places where everybody can do sport activities like soccer, swimming,.. */
    private int sportFields;
    /** attribute to make it easier to compute a query in the database */
    protected static Finder<UUID, Card> find = new Finder<UUID, Card>(UUID.class, Card.class);

    protected final void setName(String name) {
	this.name = name;
    }

    protected final void setPopulation(int population) {
	this.population = population;
    }

    protected final void setArea(float area) {
	this.area = area;
    }

    protected final void setIndebtedness(float indebtedness) {
	this.indebtedness = indebtedness;
    }

    protected final void setNights(int nights) {
	this.nights = nights;
    }

    protected final void setSportFields(int sportFields) {
	this.sportFields = sportFields;
    }

    public final UUID getID() {
	return ID;
    }

    public final String getName() {
	return name;
    }

    public final int getPopulation() {
	return population;
    }

    public final float getArea() {
	return area;
    }

    public final float getIndebtedness() {
	return indebtedness;
    }

    public final int getNights() {
	return nights;
    }

    public final int getSportFields() {
	return sportFields;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((ID == null) ? 0 : ID.hashCode());
	result = prime * result + Float.floatToIntBits(area);
	result = prime * result + Float.floatToIntBits(indebtedness);
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + nights;
	result = prime * result + population;
	result = prime * result + sportFields;
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
	Card other = (Card) obj;
	if (ID == null) {
	    if (other.ID != null)
		return false;
	} else if (!ID.equals(other.ID))
	    return false;
	if (Float.floatToIntBits(area) != Float.floatToIntBits(other.area))
	    return false;
	if (Float.floatToIntBits(indebtedness) != Float.floatToIntBits(other.indebtedness))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (nights != other.nights)
	    return false;
	if (population != other.population)
	    return false;
	if (sportFields != other.sportFields)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Card [ID=" + ID + ", name=" + name + ", population=" + population + ", area=" + area
		+ ", indebtedness=" + indebtedness + ", nights=" + nights + ", sportFields=" + sportFields + "]";
    }
}
