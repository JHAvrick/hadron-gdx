package base.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;

import aurelienribon.tweenengine.TweenManager;
import base.managers.CollisionCallback;
import base.managers.CollisionManager;
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
    public LayerManager layers;
    public TextureCache textures;
    public ArrayList<base.sprite.Spryte> updateSprites;
    public AudioManager audio;
    public CollisionManager collisions;

    public int width, height, halfWidth, halfHeight;
    public float r, g, b, a;
    private float widthScaleFactor, heightScaleFactor;

    public GameState(GameMaster game, int width, int height){
        this.game = game;
        this.width = width;
        this.height = height;
        this.halfWidth = width / 2;
        this.halfHeight = height / 2;
        this.widthScaleFactor = (float)width / (float)Gdx.graphics.getWidth();
        this.heightScaleFactor = (float)height/ (float)Gdx.graphics.getHeight();
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
        layers = new LayerManager();
        collisions = new CollisionManager();
        textures = new TextureCache();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

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
            if (s.isActive()){
                s.update();
            }
        }
        collisions.checkCollisions();
    }

    public void setBackgroundColor(Color color){
        this.r = color.r / 255f;
        this.g = color.g / 255f;
        this.b = color.b / 255f;
        this.a = color.a / 255f;
    }

    @Override
    public void resize(int width, int height) {
        this.width = (int)Math.floor(width * widthScaleFactor);
        this.height = (int)Math.floor(height * heightScaleFactor);;
        this.halfWidth = width / 2;
        this.halfHeight = height / 2;

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

    //Sprites which require input add themselves to the state's multiplexer
    public void receivesInput(InputProcessor processor){
        plexer.addProcessor(processor);
    }

    public void recievesUpdate(base.sprite.Spryte sprite){
        if (!updateSprites.contains(sprite)){
            updateSprites.add(sprite);
        }
    }

    /*
    public void removeUpdate(Sprite s){
        updateSprites.remove(s);
    }
    */
}
