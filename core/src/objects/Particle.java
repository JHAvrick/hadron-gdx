package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

import base.input.Signaler;
import base.state.GameState;
import base.sprite.Spryte;

/**
 * Created by Cloud Strife on 7/4/2017.
 */

public class Particle extends Spryte {
    private final GameState stage;
    private final ParticleFactory factory;
    public Signaler signals = new Signaler();
    public String shape, color;

    boolean movingUp, isResting, hasEnteredBounds, hasLeftBounds;
    float rotationRadius, cx, cy, radians, speed;

    ParticleEffect trail;
    Spryte outline;

    public Particle(GameState stage, ParticleFactory factory, float rotationCenterX, float rotationCenterY) {
        super(stage, -100, -100);
        this.stage = stage;
        this.factory = factory;
        this.cx = rotationCenterX;
        this.cy = rotationCenterY;
        setOriginCenter();

        trail = new ParticleEffect();
        trail.load(Gdx.files.internal("trail-particles"), stage.textures.atlas("sprites"));

        //State
        movingUp = true;
        isResting = true;
        hasEnteredBounds = false;
        hasLeftBounds = false;

        //Animations
        animations.add("orbGlow", 25, stage.textures.atlas("sprites").findRegions("orb"), Animation.PlayMode.LOOP_PINGPONG);
        animations.add("cubeGlow", 25, stage.textures.atlas("sprites").findRegions("cube"), Animation.PlayMode.LOOP_PINGPONG);
        animations.add("hexGlow", 25, stage.textures.atlas("sprites").findRegions("hex"), Animation.PlayMode.LOOP_PINGPONG);
        animations.add("triGlow", 25, stage.textures.atlas("sprites").findRegions("tri"), Animation.PlayMode.LOOP_PINGPONG);

        //Signals
        signals.addEventType("resting");

        setCollisionBoxSize(30, 30);

        //State
        stage.layers.addToLayer("particles", this);
        this.setVisible(false);
        this.setActive(false);
    }

    public void start(String shape, String color, float speed, float radius){

        outline = new Spryte(stage, getX(), getY(), stage.textures.atlas("sprites").findRegion(shape + "Shape"));

        //Start given animation
        animations.play(shape + "Glow");
        trail.start();


        //Set state
        isResting = false;
        hasEnteredBounds = false;
        this.speed = speed;
        this.rotationRadius = radius;

        this.color = color;
        this.shape = shape;

        //Set the tint and direction
        setColor(Color.valueOf(color));
        setOrientation();
        setCollisionLocked(false);

        //Add this sprite into the render/update calls
        this.setVisible(true);
        this.setActive(true);
    }

    public void setOrientation(){
        if (this.speed > 0){
            this.radians = 0.5f;
            movingUp = false;
        } else {
            this.radians = 2.6f;
            movingUp = true;
        }
    }

    public void rest(boolean doNotMoveToRestingList){
        setCollisionLocked(true);
        isResting = true;

        this.setPosition(-100, -100);
        this.collisionBox.setPosition(-100,-100);
        this.setVisible(false);
        this.setActive(false);

        if (!doNotMoveToRestingList) factory.setResting(this);
    }

    /*
    * Checks whether the particle should be put to sleep, having passed out of bounds.
    * The check changes depending on which direction the particle is travelling
    */
    public void restTest(){
        if (!hasEnteredBounds){
            if (inBounds(false)){
                hasEnteredBounds = true;
            }
        } else {
            if (!inBounds(true)){
                rest(false);
            }
        }
    }

    @Override
    public void update(){
        setCenterX((float)(cx + Math.sin(radians) * rotationRadius));
        setCenterY((float)(cy + Math.cos(radians) * rotationRadius));
        radians += speed;

        restTest();

        //Particle trail, unused at the moment
        //trail.setPosition(getCenterX(), getCenterY());

        if (outline != null) outline.setPosition(getX(), getY());
    }

    @Override
    public void draw(Batch batch){
        //trail.draw(batch, Gdx.graphics.getDeltaTime());
        super.draw(batch);
        outline.draw(batch);
    }
}
