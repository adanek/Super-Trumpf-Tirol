package mock;

import java.util.LinkedList;
import java.util.List;

public class Card implements contracts.game.Card{
    
    List<contracts.game.CardCategory> categories;
    
    public Card() {
        this.categories = new LinkedList<>();
        
        this.categories.add(new CardCategory(0, "population", "120.000"));
        this.categories.add(new CardCategory(1, "area","40"));
        this.categories.add(new CardCategory(2, "indebtedness","20%"));
        this.categories.add(new CardCategory(3, "overnightstays","102.233"));
        this.categories.add(new CardCategory(4, "sportfacilities","47"));
    }
    
    @Override
    public String getName() {
        return "Innsbruck";
    }

    @Override
    public String getImageUrl() {
        return "images/tirol.jpg";
    }

    @Override
    public List<contracts.game.CardCategory> getCategories() {
        return this.categories;
    }
}
