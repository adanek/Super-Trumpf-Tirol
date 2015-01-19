package contracts.game;

import java.util.UUID;

/**
 * 
 * @author Witsch Daniel
 *
 */
public interface ICard {

    UUID getID();

    String getName();

    int getPopulation();

    float getArea();

    float getIndebtedness();

    int getNights();

    int getSportFields();

    // public URL getImageUrl();

}
