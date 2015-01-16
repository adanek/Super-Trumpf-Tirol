package data;

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
    private long id;

    public final long getId() {
	return id;
    }

    public final void setId(long id) {
	this.id = id;
    }

    /** name of commune */
    @Id
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

    public static Finder<Long, Ranking> find = new Finder<Long, Ranking>(Long.class, Ranking.class);

    protected Ranking(String name, int rankPopulation, int rankArea, int rankIndebtedness, int rankNights,
	    int rankSportFields) {
	super();
	this.name = name;
	this.rankPopulation = rankPopulation;
	this.rankArea = rankArea;
	this.rankIndebtedness = rankIndebtedness;
	this.rankNights = rankNights;
	this.rankSportFields = rankSportFields;
    }

    public Ranking() {

    }

    public final String getName() {
	return name;
    }

    public final void setName(String name) {
	this.name = name;
    }

    public final int getRankPopulation() {
	return rankPopulation;
    }

    public final void setRankPopulation(int rankPopulation) {
	this.rankPopulation = rankPopulation;
    }

    public final int getRankArea() {
	return rankArea;
    }

    public final void setRankArea(int rankArea) {
	this.rankArea = rankArea;
    }

    public final int getRankIndebtedness() {
	return rankIndebtedness;
    }

    public final void setRankIndebtedness(int rankIndebtedness) {
	this.rankIndebtedness = rankIndebtedness;
    }

    public final int getRankNights() {
	return rankNights;
    }

    public final void setRankNights(int rankNights) {
	this.rankNights = rankNights;
    }

    public final int getRankSportFields() {
	return rankSportFields;
    }

    public final void setRankSportFields(int rankSportFields) {
	this.rankSportFields = rankSportFields;
    }

    public static final Finder<Long, Ranking> getFind() {
	return find;
    }

    public static final void setFind(Finder<Long, Ranking> find) {
	Ranking.find = find;
    }

    @Override
    public String toString() {
	return "Ranking [name=" + name + ", rankPopulation=" + rankPopulation + ", rankArea=" + rankArea
		+ ", rankIndebtedness=" + rankIndebtedness + ", rankNights=" + rankNights + ", rankSportFields="
		+ rankSportFields + "]";
    }

}
