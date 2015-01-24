package contracts.game;

/**
 * Represents a category on the card e.g. population
 * @author Witsch Daniel
 */
public interface ICardCategory {
    int getId();

    String getName();

    String getValue();
}
