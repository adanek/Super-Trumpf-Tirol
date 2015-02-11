package controllers.helpers;

import contracts.game.ICard;
import contracts.game.ICardCategory;

import java.util.List;

/**
 * Created by adanek on 23.01.15.
 */
public class CardAjax {
    
    public CardAjax(ICard card){
        this.name = card.getName();
        this.image = "assets" + card.getImageUrl();
        this.categories = card.getCategories();
    }
    
    private String name;
    private String image;
    private List<ICardCategory> categories;

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public List<ICardCategory> getCategories() {
        return categories;
    }
}


