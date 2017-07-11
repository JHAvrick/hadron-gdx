package objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.equations.Quad;
import base.sprite.Spryte;
import base.state.GameState;

/**
 * Created by Cloud Strife on 7/10/2017.
 */

public class ShapeIndicator extends Spryte {
    private final GameState stage;

    private boolean spinning = false;

    public ShapeIndicator(GameState stage) {
        super(stage);
        this.stage = stage;
        setOriginCenter();
        setAlpha(0);

        animations.add("glitch", 20, stage.textures.atlas("sprites").findRegions("void"), Animation.PlayMode.LOOP_RANDOM);
        stage.layers.addToLayer("ui", this);

        Tween.registerAccessor(ShapeIndicator.class, new ShapeIndicatorAccessor());
    }

    public void update(){
        if (spinning) setRotation(getRotation() - 1);
    }

    public void fadeIn(int type, String shape){
        if (type == 1 || type == 3){
            spinning = true;
            setScale(1f, 1f);
            animations.stop();
            setRegion(stage.textures.atlas("sprites").findRegion(shape + "Shape"));
        } else {
            spinning = false;
            setRotation(0);
            setScale(1.5f, 1.5f);
            animations.play("glitch");
        }

        setCenter(stage.width / 9, stage.height / 1.65f);
        setOriginCenter();

        Tween.to(this, 0, .5f).target(1).ease(Quad.INOUT).start(stage.tweens);
    }

    public void fadeOut(){
        Tween.to(this, 0, .5f).target(0).ease(Quad.INOUT).start(stage.tweens);
    }
}

class ShapeIndicatorAccessor implements TweenAccessor<ShapeIndicator> {

    @Override
    public int getValues(ShapeIndicator target, int tweenType, float[] returnValues) {
        returnValues[0] = target.getColor().a;
        return 1;
    }

    @Override
    public void setValues(ShapeIndicator target, int tweenType, float[] newValues) {
        target.setAlpha(newValues[0]);
    }

}

