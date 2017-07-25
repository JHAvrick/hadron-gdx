package base.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

import base.managers.AnimationManager;
import base.state.GameState;

/**
 * Created by Cloud Strife on 7/3/2017.
 *
 * This is a higher-level version of a libGDX Sprite, with extra state and management.
 */

public class Spryte extends Sprite implements Layerable {

    public GameState stage;
    public AnimationManager animations;
    public Rectangle collisionBox;
    private boolean visible, active, collisionLocked;

    public Spryte (GameState stage){
        super();
        this.stage = stage;

        visible = true;
        active = true;

        animations = new AnimationManager(this);
        collisionBox = new Rectangle(getX(), getY(), getWidth(), getHeight());

        stage.recievesUpdate(this);
    }

    public Spryte(GameState stage, float x, float y, Texture texture){
        super(texture);
        this.stage = stage;
        this.setPosition(x, y);

        visible = true; //When false, rendering this sprite is skipped, state can still be updated
        active = true; //When true, updating for this sprite is skipped, rendering is still updated

        animations = new AnimationManager(this);
        collisionBox = new Rectangle(getX(), getY(), getWidth(), getHeight());

        stage.recievesUpdate(this);
    }

    public Spryte(GameState stage, float x, float y, TextureRegion region){
        super(region);
        this.stage = stage;
        this.setPosition(x, y);

        visible = true;
        active = true;

        animations = new AnimationManager(this);
        collisionBox = new Rectangle(getX(), getY(), getWidth(), getHeight());

        stage.recievesUpdate(this);
    }

    public Spryte(GameState stage, float x, float y){
        super();
        this.stage = stage;
        this.setPosition(x, y);

        visible = true;
        active = true;

        animations = new AnimationManager(this);
        collisionBox = new Rectangle(getX(), getY(), getWidth(), getHeight());

        stage.recievesUpdate(this);
    }

    @Override
    public void draw(Batch batch){
        if (this.getTexture() == null) return;

        animations.updateAnimation();
        super.draw(batch);
    }

    @Override
    public void setTexture(Texture tex){
        super.setTexture(tex);
        setBounds(getX(), getY(), tex.getWidth(), tex.getHeight());
    }

    @Override
    public void setRegion(TextureRegion region){
        super.setRegion(region);
        setBounds(getX(), getY(), region.getRegionWidth(), getRegionHeight());
    }

    public void update(){

    }

    public void setCollisionBoxSize(int width, int height){
        collisionBox.setSize(width, height);
        collisionBox.setCenter(getX() + width / 2, getY() + width / 2);
    }

    //Checked by the stage's LayerManager during render call
    //Sprite is skipped if false is returned
    public boolean isVisible(){
        return visible;
    }

    public void setVisible(boolean visible){
        this.visible = visible;
    }

    public boolean isActive(){
        return active;
    }

    public boolean inBounds(boolean showPrint){
        Vector3 xy = new Vector3(getX(), getY(), 0);
        Vector3 size = new Vector3(getX() + getWidth(), getY() + getHeight(), 0);

        return stage.camera.frustum.boundsInFrustum(new BoundingBox(xy, size));
    }

    //When set to false, the sprite will remove itself from the stage's update loop
    public void setActive(boolean active){
        this.active = active;
        /*
        if (this.active){
            stage.recievesUpdate(this);
        } else {
            stage.removeUpdate(this);
        }
        */
    }

    //At the moment collision locks must be set manually
    public boolean isCollisionLocked(){
        return collisionLocked;
    }

    public void setCollisionLocked(boolean locked){
        collisionLocked = locked;
    }

    public float getCenterX(){
        return getX() + (getWidth() / 2);
    }

    public float getCenterY(){
        return getY() + (getHeight() / 2);
    }

}
