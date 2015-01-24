package controllers.helpers;

import contracts.game.ICard;
import contracts.game.ICardCategory;
import controllers.Assets;
import data.Card;
import play.api.mvc.Action;
import play.api.mvc.AnyContent;

import java.util.List;

/**
 * Created by adanek on 23.01.15.
 */
public class CardAjax {
    
    public CardAjax(ICard card){
        this.name = card.getName();
        this.image = "assets" + card.getImageUrl();
        this.categories = card.getCategories();
        this.categories.get(2).setChoosen(true);
    }
    
    public String name;
    public String image;
    public List<ICardCategory> categories;
}


