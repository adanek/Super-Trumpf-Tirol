package contracts.game;

import java.net.URL;
import java.util.List;

public interface Card {
    
    public String getName();
    public String getImageUrl();
    public List<CardCategory> getCategories();

}
