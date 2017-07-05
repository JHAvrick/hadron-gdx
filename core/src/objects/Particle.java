package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import base.state.GameState;
import base.sprite.Spryte;

/**
 * Created by Cloud Strife on 7/4/2017.
 */

public class Particle extends Spryte {
    private final GameState stage;

    String shape;
    Color color;
    float rotationRadius, cx, cy, radians, speed, animStateTime;
    public Animation<TextureRegion> glow;

    public Particle(GameState stage, float rotationCenterX, float rotationCenterY) {
        super(stage, stage.width / 2, stage.height / 2);
        this.stage = stage;
        this.cx = rotationCenterX;
        this.cy = rotationCenterY;

        setRegion(stage.textures.region("sprites", "orb"));
        setBounds(getX(), getY(), getRegionWidth(), getRegionHeight());

        animations.add("glow", 25, stage.textures.atlas("sprites").findRegions("orb"), Animation.PlayMode.LOOP_PINGPONG);
        animations.play("glow");

        stage.recievesUpdate(this);
        stage.layerManager.addToForeground(this);

        //To be changed later
        radians = 2.6f;
        rotationRadius = 700;
        speed = .0065f;
    }

    public void start(String shape, Color color, float speed, float radius){

    }

    @Override
    public void update(){
        setCenterX((float)(cx + Math.sin(radians) * rotationRadius));
        setCenterY((float)(cy + Math.cos(radians) * rotationRadius));
        radians += speed;
    }
}
