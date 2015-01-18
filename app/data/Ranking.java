package data;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

/**
 * This class contains the information about a village and the position of the
 * attributes compared to the other one. The instances are also written in a
 * database to save them during the game.
 * 
 * @author Witsch Daniel
 *
 */
@Entity
public class Ranking extends Model {

    @Id
    private UUID ID = UUID.randomUUID();
    /** name of commune */
    private String name;
    /** rank of this town referencing to all others -> population of last census */
    private int rankPopulation;
    /** rank of this town referencing to all others -> size of the whole town */
    private int rankArea;
    /**
     * rank of this town referencing to all others -> indebtedness in percentage
     */
    private int rankIndebtedness;
    /**
     * rank of this town referencing to all others -> nights in hotels,
     * apartments and other touristic overnight possibilities
     */
    private int rankNights;
    /**
     * rank of this town referencing to all others -> places where everybody can
     * do sport activities like soccer, swimming,..
     */
    private int rankSportFields;

    /** attribute to make it easier to compute a query in the database */
    protected static Finder<UUID, Ranking> find = new Finder<UUID, Ranking>(UUID.class, Ranking.class);

    protected final void setName(String name) {
	this.name = name;
    }

    protected final void setRankPopulation(int rankPopulation) {
	this.rankPopulation = rankPopulation;
    }

    protected final void setRankArea(int rankArea) {
	this.rankArea = rankArea;
    }

    protected final void setRankIndebtedness(int rankIndebtedness) {
	this.rankIndebtedness = rankIndebtedness;
    }

    protected final void setRankNights(int rankNights) {
	this.rankNights = rankNights;
    }

    protected final void setRankSportFields(int rankSportFields) {
	this.rankSportFields = rankSportFields;
    }

    public final UUID getID() {
	return ID;
    }

    public final String getName() {
	return name;
    }

    public final int getRankPopulation() {
	return rankPopulation;
    }

    public final int getRankArea() {
	return rankArea;
    }

    public final int getRankIndebtedness() {
	return rankIndebtedness;
    }

    public final int getRankNights() {
	return rankNights;
    }

    public final int getRankSportFields() {
	return rankSportFields;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((ID == null) ? 0 : ID.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + rankArea;
	result = prime * result + rankIndebtedness;
	result = prime * result + rankNights;
	result = prime * result + rankPopulation;
	result = prime * result + rankSportFields;
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
	Ranking other = (Ranking) obj;
	if (ID == null) {
	    if (other.ID != null)
		return false;
	} else if (!ID.equals(other.ID))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (rankArea != other.rankArea)
	    return false;
	if (rankIndebtedness != other.rankIndebtedness)
	    return false;
	if (rankNights != other.rankNights)
	    return false;
	if (rankPopulation != other.rankPopulation)
	    return false;
	if (rankSportFields != other.rankSportFields)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Ranking [ID=" + ID + ", name=" + name + ", rankPopulation=" + rankPopulation + ", rankArea=" + rankArea
		+ ", rankIndebtedness=" + rankIndebtedness + ", rankNights=" + rankNights + ", rankSportFields="
		+ rankSportFields + "]";
    }

}
