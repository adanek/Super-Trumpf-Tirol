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

    protected final UUID getID() {
	return ID;
    }

    protected final String getName() {
	return name;
    }

    protected final int getRankPopulation() {
	return rankPopulation;
    }

    protected final int getRankArea() {
	return rankArea;
    }

    protected final int getRankIndebtedness() {
	return rankIndebtedness;
    }

    protected final int getRankNights() {
	return rankNights;
    }

    protected final int getRankSportFields() {
	return rankSportFields;
    }

    protected static final Finder<UUID, Ranking> getFind() {
	return find;
    }

}
