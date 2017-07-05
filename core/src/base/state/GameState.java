package base.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;

import aurelienribon.tweenengine.TweenManager;
import base.managers.LayerManager;
import base.managers.TextureCache;
import base.audio.AudioManager;

/**
 * Created by Cloud Strife on 7/2/2017.
 * State
 *   MultiPlexer
 *   Camera
 *   ViewPort
 *   LayerManager
 *   TextureCache
 */

public class GameState implements Screen {
    public GameMaster game;

    public OrthographicCamera camera;
    public ExtendViewport viewport;
    public InputMultiplexer plexer;
    public TweenManager tweens;
    public LayerManager layerManager;
    public TextureCache textures;
    public ArrayList<base.sprite.Spryte> updateSprites;
    public AudioManager audio;

    public int width, height;
    public int r, g, b, a;

    public GameState(GameMaster game, int width, int height){
        this.game = game;
        this.width = width;
        this.height = height;
        r = b = g = a = 0;

        tweens = new TweenManager();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
        camera.position.set(width / 2, height / 2, 0);
        viewport = new ExtendViewport(width, height, camera);

        plexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(plexer);

        audio = new AudioManager(this);
        updateSprites = new ArrayList<base.sprite.Spryte>();
        layerManager = new LayerManager();
        textures = new TextureCache();
    }

    //Sprites which require input add themselves to the state's multiplexer
    public void receivesInput(InputProcessor processor){
        plexer.addProcessor(processor);
    }

    public void recievesUpdate(base.sprite.Spryte sprite){
        updateSprites.add(sprite);
    }

    public void removeUpdate(Sprite s){
        updateSprites.remove(s);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(r, g, b, a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Camera update
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        //Logic step
        step();

        //Update tweens
        tweens.update(delta);
    }

    public void step(){
        //CHECK STEP HERE
        update();
    }

    public void update(){
        for (base.sprite.Spryte s : updateSprites){
            s.update();
        }
    }

    public void setBackgroundColor(int r, int g, int b, int a){
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        System.out.println("State Dispose...");
        textures.dispose();
        audio.disposeAll();
    }
}
