package data;

/**
 * class that represents one category of the card
 * @author Witsch Daniel
 */
public class CardCategory implements contracts.game.ICardCategory {

    int id;
    /**
     * name of the category
     */
    String name;
    /**
     * value of this category
     */
    String value;
    private boolean choosen = false;

    /**
     * constructor
     * @param id
     * @param name
     * @param value
     */
    public CardCategory(int id, String name, String value) {
	this.id = id;
	this.name = name;
	this.value = value;
    }

    @Override
    public int getId() {
	return id;
    }

    @Override
    public String getName() {
	return name;
    }

    @Override
    public String toString() {
	return "CardCategory [id=" + id + ", name=" + name + ", value=" + value + "]";
    }

    @Override
    public String getValue() {
	return value;
    }

    @Override
    public boolean isChoosen() {
        return this.choosen;
    }
    
    public void setChoosen(boolean newValue){
        
        this.choosen = newValue;
    }
}
