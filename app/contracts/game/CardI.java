package contracts.game;

import java.util.UUID;

public interface CardI {

    UUID getID();

    String getName();

    int getPopulation();

    float getArea();

    float getIndebtedness();

    int getNights();

    int getSportFields();

    // public URL getImageUrl();

}
