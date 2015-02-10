package core;

import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

public class PlayerAI implements Observer{
    
    private UUID Id;

    public PlayerAI(UUID id) {
        Id = id;
    }

    public String getId() {
        return this.Id.toString();
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
