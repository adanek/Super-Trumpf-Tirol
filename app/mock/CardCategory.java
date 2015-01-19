package mock;

public class CardCategory implements contracts.game.CardCategory {
    
    int id;
    String name;
    String value;

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
    public String getValue() {
        return value;
    }
}
