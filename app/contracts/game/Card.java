package contracts.game;

import java.net.URL;
import java.util.List;

public interface Card {
    
    public String getName();
    public URL getImageUrl();
    public List<CardCategory> getCategories();

}
