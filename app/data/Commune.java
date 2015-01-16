package data;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

/**
 * Class to save the data about the communes in a database. H2 is a in-memory
 * database and with Ebean we can manage the communication with H2.
 * 
 * @author Witsch Daniel
 *
 */
@Entity
public class Commune extends Model {

    @Id
    public Long id;
    /** name of commune */
    @Id
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

    public static Finder<Long, Commune> find = new Finder<Long, Commune>(Long.class, Commune.class);

    protected Commune(String name, int population, float area, float indebtedness, int nights, int sportFields) {
	super();
	this.name = name;
	this.population = population;
	this.area = area;
	this.indebtedness = indebtedness;
	this.nights = nights;
	this.sportFields = sportFields;
    }

    public final Long getId() {
	return id;
    }

    public final void setId(Long id) {
	this.id = id;
    }

    public Commune() {

    }

    public final String getName() {
	return name;
    }

    public final void setName(String name) {
	this.name = name;
    }

    public final int getPopulation() {
	return population;
    }

    public final void setPopulation(int population) {
	this.population = population;
    }

    public final float getArea() {
	return area;
    }

    public final void setArea(float area) {
	this.area = area;
    }

    public final float getIndebtedness() {
	return indebtedness;
    }

    public final void setIndebtedness(float indebtedness) {
	this.indebtedness = indebtedness;
    }

    public final int getNights() {
	return nights;
    }

    public final void setNights(int nights) {
	this.nights = nights;
    }

    public final int getSportFields() {
	return sportFields;
    }

    public final void setSportFields(int sportFields) {
	this.sportFields = sportFields;
    }

    public static final Finder<Long, Commune> getFind() {
	return find;
    }

    public static final void setFind(Finder<Long, Commune> find) {
	Commune.find = find;
    }

    @Override
    public String toString() {
	return "Commune [name=" + name + ", population=" + population + ", area=" + area + ", indebtedness="
		+ indebtedness + ", nights=" + nights + ", sportFields=" + sportFields + "]";
    }

}
