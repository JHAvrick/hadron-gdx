package state;

import base.GameMaster;
import base.GameState;
import objects.Fader;

/**
 * Created by Cloud Strife on 7/3/2017.
 */

public class MainGame extends GameState {

    Fader fader;

    public MainGame(GameMaster game, int width, int height) {
        super(game, width, height);

        fader = new Fader(this, 0, 0);
        fader.fadeIn(2);

    }
}
