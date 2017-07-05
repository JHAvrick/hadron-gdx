package state;

import base.state.GameMaster;
import base.state.GameState;
import objects.Fader;
import objects.HadronBoard;
import objects.Particle;

/**
 * Created by Cloud Strife on 7/3/2017.
 */

public class MainGame extends GameState {

    Fader fader;
    Particle particle;

    public MainGame(GameMaster game, int width, int height) {
        super(game, width, height);

        System.out.println("Main Started");

        textures.addAtlas("sprites", "images/sprites.txt");
        textures.addAtlas("tracks", "images/tracks.txt");

        HadronBoard board = new HadronBoard(this);

        particle = new Particle(this, -1 * ((2500 / 2) - width), height /2);

        fader = new Fader(this, 0, 0);
        fader.fadeIn(2);

    }

    public void render(float delta){
        super.render(delta);

        game.batch.begin();
        layerManager.draw(this.game.batch);
        game.batch.end();
    }
}
