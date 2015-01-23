package contracts.game;

/**
 * Represents a category on the card e.g. population
 * @author Witsch Daniel
 */
public interface ICardCategory {
    int getId();

    String getName();

    String getValue();

    /**
     * *
     * @return true if the active player has choosen this category.
     */
    boolean isChoosen();
    
    void setChoosen(boolean newValue);
}
