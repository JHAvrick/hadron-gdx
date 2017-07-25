package state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Json;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;
import base.input.SignalCallback;
import base.managers.CollisionCallback;
import base.sprite.Spryte;
import base.state.GameMaster;
import base.state.GameState;
import fx.Flasher;
import objects.ColorIndicator;
import objects.CountMeter;
import objects.Fader;
import objects.HadronBoard;
import objects.Particle;
import objects.ParticleFactory;
import objects.Player;
import objects.Sequence;
import objects.Phase;
import objects.ShapeIndicator;

/**
 * Created by Cloud Strife on 7/3/2017.
 */

//Game Flow
    //New Phase
        //Get sequence
        //Display sequence rules
        //Begin phase
    //Check Collisions
        //Collision with game bounds - sleep
            //Remove update and render, add self back into resting orbs list
        //Collision with PONR - check if illegal orb
            //If so, go to failure
        //Collision with player - check if legal orb
            //If so increment count, or add to special orbs
    //Phase Complete
        //Failure - Go to Results page
        //Success
            //Check if tier complete
                //Show tier splash
                    //New Phase
            //New phase


public class MainGame extends GameState implements CollisionCallback {

    private Json jsonParser = new Json();
    private Fader fader;
    private HadronBoard board;
    private ParticleFactory factory;
    private Player player;
    private Flasher shortFlash, longFlash;
    private CountMeter meter;
    private ShapeIndicator shapeIndicator;
    private ColorIndicator colorIndicator;

    ShaderProgram shader;
    Sequence currentSequence;
    Phase currentPhase;

    int tierIndex = 0;
    int phaseIndex = 4;
    int orbIndex = 0;
    float pitchIndex = 1;
    float pitchStep = 0;

    public MainGame(GameMaster game, int width, int height) {
        super(game, width, height);

        textures.addAtlas("sprites", "images/sprites.txt");
        textures.addAtlas("tracks", "images/tracks.txt");
        layers.addLayer("mainTrack");
        layers.addLayer("particles");
        layers.addLayer("player");
        layers.addLayer("ui");
        layers.addLayer("overlay");

        audio.addFX("hit", "audio/hit.ogg");
        audio.addFX("special", "audio/special.ogg");
        audio.addFX("victory", "audio/victory.ogg");
        audio.addFX("failure", "audio/failure.ogg");
        audio.addFX("tierVictory", "audio/tierVictory.ogg");
        audio.addMusic("coldwire", "audio/coldwire.mp3");
        audio.loop("coldwire");

        //ShaderProgram.pedantic = false;
        shader = new ShaderProgram(Gdx.files.internal("shaders/rgb/rgb.vert"), Gdx.files.internal("shaders/rgb/rgb.frag"));
        //layers.setLayerShader("mainTrack", shader);
        //layers.setLayerShader("player", shader);
        //layers.setLayerShader("particles", shader);
        //layers.setLayerShader("ui", shader);


        //------------------------------------------------------------------------------------------

        board = new HadronBoard(this); //Board lays out where stuff should go
        factory = new ParticleFactory(this, board); //Factory parses and implements each sequence

        //Flash effects triggered when an orb is collected
        shortFlash = new Flasher(this);
        longFlash = new Flasher(this);

        //Game sprites
        player = new Player(this, board, 0, 0);
        meter = new CountMeter(this);
        shapeIndicator = new ShapeIndicator(this);
        colorIndicator = new ColorIndicator(this);

        //------------------------------------------------------------------------------------------

        collisions.addGroup("particles");
        collisions.addGroup("player");
        collisions.addToGroup("player", player);
        collisions.addToGroup("particles", factory.makeParticles(100));
        collisions.collides("particles", "player", this);

        //------------------------------------------------------------------------------------------

        //Fade in and start the game
        fader = new Fader(this, 0,0);
        fader.fadeIn(2, new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                meter.shrink();
                startGame();
            }
        });
    }

    /* Loads sequence in the given JSON */
    private void setSequence(String path){
        currentSequence = jsonParser.fromJson(Sequence.class, Gdx.files.internal(path));
    }

    /* Initializes game state, only called once to kick off game loop */
    private void startGame(){
        //Hide hints
        //Get the tier/phase from memory and update the increment variables (player may have continued after failure)

        setSequence("sequences/tier_" + this.tierIndex + ".json");
        incrementPhase();

        factory.startSequence(currentPhase);
    }

    /* Calls tier or phase increment depending on indexes */
    private void incrementState(){
        if (phaseIndex == 6){

            incrementTier();

        } else {

            factory.endSequence(); //Pauses the timer and clears remaining orbs

            audio.playFX("victory");
            shapeIndicator.fadeOut();
            colorIndicator.fadeOut();
            longFlash.flash(2.5f, Color.WHITE, new SignalCallback() {
                @Override
                public void onSignal() {
                    meter.shrink();
                    incrementPhase();
                }
            });

        }
    }

    /* Increments tier */
    private void incrementTier(){
        tierIndex += 1;
        phaseIndex = 0;

        setSequence("sequences/tier_" + this.tierIndex + ".json");

        audio.playFX("tierVictory");
        fader.fadeOut(0.5f, new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {

            }
        });

        //TODO - Then go to incrementPhase()
    }

    /* Increments phase and resets all relevant indexes */
    private void incrementPhase(){
        phaseIndex += 1;
        currentPhase = currentSequence.getPhase(this.phaseIndex);
        pitchStep = 4f / currentPhase.count;
        pitchIndex = 1;
        orbIndex = 0;

        displayPhase();
        factory.startSequence(currentPhase);
    }

    /* Alters state indicators to match the phase just loaded */
    public void displayPhase(){
        shapeIndicator.fadeIn(currentPhase.type, currentPhase.shape);
        colorIndicator.fadeIn(currentPhase.type, currentPhase.color);
    }

    @Override
    public void onCollision(Spryte one, Spryte two) {
        Particle particle = one instanceof Particle ? (Particle)one : (Particle)two;
        Player player = one instanceof Particle ? (Player) two : (Player) one;

        if (particle.isCollisionLocked()) return;
        else particle.rest(false);

        switch (currentPhase.type){
            case 1:
                if (particle.shape == currentPhase.shape)
                    collectOrb(); return;
            case 2:
                if (particle.color == currentPhase.color)
                    collectOrb(); return;
            case 3:
                if (particle.color == currentPhase.color && particle.shape == currentPhase.shape)
                    collectOrb();  return;
        }

        gameOver(); //If no condition catches above then an incorrect orb has been gathered
    }

    public void collectOrb(){
        orbIndex++;

        meter.increment((float)orbIndex / (float)currentPhase.count);

        if (orbIndex == currentPhase.count){

            //Do end of phase stuff
            incrementState();

        } else {

            //FX
            audio.playFX("hit", 1, pitchIndex, 0);
            shortFlash.flash(0.3f);

            //State update
            pitchIndex += pitchStep;
        }
    }

    public void endPhase(){
        factory.endSequence(); //Pauses the timer and clears remaining orbs

        audio.playFX("victory");
        shapeIndicator.fadeOut();
        colorIndicator.fadeOut();
        longFlash.flash(2.5f, Color.WHITE, new SignalCallback() {
            @Override
            public void onSignal() {
                meter.shrink();
                incrementState();
            }
        });
    }

    public void gameOver(){
        //TODO - Screen shake FX
        //TODO - Go to results screen
        //TODO - Dispose assets
        audio.playFX("failure");
    }

    public void render(float delta){
        super.render(delta);

        game.batch.setShader(null);
        game.batch.begin();
        layers.draw(this.game.batch);
        game.batch.end();

        //game.batch.setShader(shader);
        //game.batch.begin();
        //layerManager.draw(this.game.batch);
        //game.batch.end();
    }

}



