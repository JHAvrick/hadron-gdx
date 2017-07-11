package objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.equations.Quad;
import base.sprite.Spryte;
import base.state.GameState;

/**
 * Created by Cloud Strife on 7/11/2017.
 */

public class ColorIndicator extends Spryte {

    private boolean spinning = false;

    public ColorIndicator(GameState stage) {
        super(stage);
        setOriginCenter();
        setAlpha(0);

        animations.add("pulse", 15, stage.textures.atlas("sprites").findRegions("colorBlob"), Animation.PlayMode.LOOP);
        animations.add("glitch", 20, stage.textures.atlas("sprites").findRegions("void"), Animation.PlayMode.LOOP_RANDOM);
        stage.layers.addToLayer("ui", this);

        Tween.registerAccessor(ColorIndicator.class, new ColorIndicatorAccessor());
    }

    public void update(){
        if (spinning) setRotation(getRotation() - 1);
    }

    public void fadeIn(int type, String color){
        if (type == 2 || type == 3){
            spinning = true;
            setScale(1f, 1f);
            setColor(Color.valueOf(color));
            animations.play("pulse");
        } else {
            spinning = false;
            setRotation(0);
            setScale(1.5f, 1.5f);
            setColor(Color.WHITE);
            animations.play("glitch");
        }

        Tween.to(this, 0, .5f).target(1).ease(Quad.INOUT).start(stage.tweens);
        setCenter(stage.width / 9, stage.height / 2.5f);
        setOriginCenter();
    }

    public void fadeOut(){
        Tween.to(this, 0, .5f).target(0).ease(Quad.INOUT).start(stage.tweens);
    }
}

class ColorIndicatorAccessor implements TweenAccessor<ColorIndicator> {

    @Override
    public int getValues(ColorIndicator target, int tweenType, float[] returnValues) {
        returnValues[0] = target.getColor().a;
        return 1;
    }

    @Override
    public void setValues(ColorIndicator target, int tweenType, float[] newValues) {
        target.setAlpha(newValues[0]);
    }

}
