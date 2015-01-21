package data;

public class CardCategory implements contracts.game.ICardCategory {

    int id;
    String name;
    String value;
    private boolean choosen = false;

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
