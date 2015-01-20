package contracts.data;

import java.util.UUID;

public interface IRanking {
    public UUID getID();

    public String getName();

    public int getRankPopulation();

    public int getRankArea();

    public int getRankIndebtedness();

    public int getRankNights();

    public int getRankSportFields();
}
