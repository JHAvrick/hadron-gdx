package state;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Hadron;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;
import base.GameMaster;
import base.GameState;
import base.InputSprite;
import base.input.InputEvent;
import objects.Fader;
import objects.HadronMenuButton;

/**
 * Created by Cloud Strife on 7/2/2017.
 */

public class MainMenu extends GameState {

    HadronMenuButton playBtn, tutorialBtn, moreBtn;
    Fader fader;

    public MainMenu(Hadron game, int width, int height) {
        super(game, width, height);

        textures.addAtlas("menu", "menu.txt");

        playBtn = new HadronMenuButton(this, width / 1.25f, height / 3f, "play", "dottedCircle");
        playBtn.signals.on("touchDown", new StartGame(game, this));
        playBtn.fadeIn();

        tutorialBtn = new HadronMenuButton(this, width / 2, height / 3f, "tutorial", "dottedCube");
        tutorialBtn.signals.on("touchDown", new StartTutorial(game, this));
        tutorialBtn.fadeIn();

        moreBtn = new HadronMenuButton(this, width / 5, height / 3f, "settings", "dottedHex");
        moreBtn.signals.on("touchDown", new StartMore(game, this));
        moreBtn.fadeIn();



        fader = new Fader(this, 0, 0);
        fader.fadeIn(1);
    }

}

class StartGame implements InputEvent {
    Hadron game;
    MainMenu stage;

    public StartGame(Hadron game, MainMenu stage){
        this.game = game;
        this.stage = stage;
    }

    @Override
    public void onInput() {
        //game.getScreen().dispose();
        stage.fader.fadeOut(2, new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.setScreen(new MainGame(game, 720, 1280));
            }
        });

    }
}


class StartTutorial implements InputEvent {
    Hadron game;
    MainMenu stage;

    public StartTutorial(Hadron game, MainMenu stage){
        this.game = game;
        this.stage = stage;
    }

    @Override
    public void onInput() {
        //game.getScreen().dispose();
        stage.fader.fadeOut(2, new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                //game.setScreen(new MainGame(game, 720, 1280));
            }
        });

    }
}

class StartMore implements InputEvent {
    Hadron game;
    MainMenu stage;

    public StartMore(Hadron game, MainMenu stage){
        this.game = game;
        this.stage = stage;
    }

    @Override
    public void onInput() {
        //game.getScreen().dispose();
        stage.fader.fadeOut(2, new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                //game.setScreen(new MainGame(game, 720, 1280));
            }
        });

    }
}

