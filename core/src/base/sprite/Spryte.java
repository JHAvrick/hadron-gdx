package base.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import base.managers.AnimationManager;
import base.state.GameState;

/**
 * Created by Cloud Strife on 7/3/2017.
 */

public class Spryte extends Sprite {

    public GameState stage;
    public AnimationManager animations;
    public boolean visible, active;

    public Spryte(GameState stage, float x, float y, Texture texture){
        super(texture);
        this.stage = stage;
        this.setPosition(x, y);

        visible = true; //When false, rendering this sprite is skipped
        active = true; //When true, updating for this sprite is skipped

        animations = new AnimationManager(this);
    }

    public Spryte(GameState stage, float x, float y, TextureRegion region){
        super(region);
        this.stage = stage;
        this.setPosition(x, y);

        animations = new AnimationManager(this);
    }

    public Spryte(GameState stage, float x, float y){
        super();
        this.stage = stage;
        this.setPosition(x, y);

        animations = new AnimationManager(this);
    }

    public void update(){

    }

    @Override
    public void draw(Batch batch){
        animations.updateAnimation();
        super.draw(batch);
    }


}
