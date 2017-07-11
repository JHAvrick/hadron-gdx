package state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.mygdx.game.Hadron;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;
import base.state.GameState;
import base.input.SignalCallback;
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
    ShaderProgram shader, shaderRed, shaderGreen, shaderBlue;

    public MainMenu(Hadron game, int width, int height) {
        super(game, width, height);
        textures.addAtlas("menu", "images/menu.txt");
        layers.addLayer("ui");
        layers.addLayer("overlay");

        audio.addMusic("lineLost", "audio/line-lost.mp3");
        //audio.loop("lineLost");

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

        //-----------------------------------------------------------------------------------

        ShaderProgram.pedantic = false;
        shader = new ShaderProgram(Gdx.files.internal("shaders/rgb/rgb.vert"), Gdx.files.internal("shaders/rgb/rgb.frag"));
        //layers.setLayerShader("ui", shader);


        //Gdx.gl.glEnable(GL20.GL_ALPHA);



        //game.batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
        //game.batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        //Gdx.gl.glBlendEquation(GL20.GL_FUNC_ADD);
        //Gdx.gl.glEnable(GL20.GL_BLEND);
        game.batch.enableBlending();
        game.batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);

        System.out.println(shader.getLog());

        fader = new Fader(this, 0, 0);
        fader.fadeIn(1);
    }

    @Override
    public void render(float delta){
        super.render(delta);



        //viewport.setScreenPosition(viewport.getScreenX(), viewport.getScreenY());
        //game.batch.setShader(shader);
        game.batch.begin();
        effect.draw(game.batch, delta);
        game.batch.end();

        //game.batch.setShader(null);
        game.batch.begin();
        layers.draw(this.game.batch);
        game.batch.end();

    }

}

class StartGame implements SignalCallback {
    Hadron game;
    MainMenu stage;

    public StartGame(Hadron game, MainMenu stage){
        this.game = game;
        this.stage = stage;
    }

    @Override
    public void onSignal() {
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


class StartTutorial implements SignalCallback {
    Hadron game;
    MainMenu stage;

    public StartTutorial(Hadron game, MainMenu stage){
        this.game = game;
        this.stage = stage;
    }

    @Override
    public void onSignal() {
        //game.getScreen().dispose();
        stage.fader.fadeOut(2, new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                //game.setScreen(new MainGame(game, 720, 1280));
            }
        });
    }
}

class StartMore implements SignalCallback {
    Hadron game;
    MainMenu stage;

    public StartMore(Hadron game, MainMenu stage){
        this.game = game;
        this.stage = stage;
    }

    @Override
    public void onSignal() {
        //game.getScreen().dispose();
        stage.fader.fadeOut(2, new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                //game.setScreen(new MainGame(game, 720, 1280));
            }
        });

    }
}

