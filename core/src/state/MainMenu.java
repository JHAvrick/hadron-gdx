package state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.mygdx.game.Hadron;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;
import base.state.GameState;
import base.input.InputEvent;
import objects.Fader;
import objects.HadronMenuButton;
import objects.HadronTitle;

/**
 * Created by Cloud Strife on 7/2/2017.
 */

public class MainMenu extends GameState {

    ParticleEffect effect;
    HadronTitle title;
    HadronMenuButton playBtn, tutorialBtn, moreBtn;
    Fader fader;

    public MainMenu(Hadron game, int width, int height) {
        super(game, width, height);

        textures.addAtlas("menu", "images/menu.txt");

        audio.addMusic("lineLost", "audio/line-lost.mp3");
        audio.loop("lineLost");

        title = new HadronTitle(this);
        title.introSequence.start(tweens);

        playBtn = new HadronMenuButton(this, width / 1.25f, height / 3f, "play", "dottedCircle");
        playBtn.signals.on("touchDown", new StartGame(game, this));
        playBtn.fadeIn();

        tutorialBtn = new HadronMenuButton(this, width / 2, height / 3f, "tutorial", "dottedCube");
        tutorialBtn.signals.on("touchDown", new StartTutorial(game, this));
        tutorialBtn.fadeIn();

        moreBtn = new HadronMenuButton(this, width / 5, height / 3f, "settings", "dottedHex");
        moreBtn.signals.on("touchDown", new StartMore(game, this));
        moreBtn.fadeIn();

        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("hadron-particles"), textures.atlas("menu"));
        effect.setPosition(-width / 3, -height / 3);
        effect.start();

        fader = new Fader(this, 0, 0);
        fader.fadeIn(1);
    }

    @Override
    public void render(float delta){
        super.render(delta);

        //Draw
        game.batch.begin();
        effect.draw(game.batch, delta);
        layerManager.draw(this.game.batch);
        game.batch.end();

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
        stage.audio.fade("lineLost", 0, 2);
        stage.fader.fadeOut(2, new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                stage.dispose();
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

