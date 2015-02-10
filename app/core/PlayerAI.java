package core;

import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

/**
 * Created by adanek on 09.02.15.
 */
public class PlayerAI implements Observer{
    
    private UUID Id;

    public PlayerAI(UUID id) {
        Id = id;
    }

    public UUID getId() {
        return Id;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
