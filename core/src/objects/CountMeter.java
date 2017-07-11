package objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.sun.xml.internal.bind.v2.TODO;

import java.text.DecimalFormat;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Quad;
import base.sprite.Spryte;
import base.state.GameState;

/**
 * Created by Cloud Strife on 7/8/2017.
 */

public class CountMeter extends Spryte implements TweenCallback {
    private final GameState stage;

    public Spryte fill;
    Tween grow, shrink;
    DecimalFormat df = new DecimalFormat("###.##");

    public CountMeter(GameState stage){
        super(stage);
        this.stage = stage;

        animations.add("glow", 20, stage.textures.atlas("sprites").findRegions("container"), Animation.PlayMode.LOOP_PINGPONG);
        animations.play("glow");
        setCenter(stage.width / 9, stage.halfHeight);

        fill = new Spryte(stage);
        fill.setRegion(stage.textures.atlas("sprites").findRegion("fill"));
        fill.setCenter(stage.width / 9, stage.halfHeight);
        fill.setOriginCenter();
        fill.setActive(false);

        Tween.registerAccessor(CountMeter.class, new CountMeterAccessor());

        setActive(false);
        stage.layers.addToLayer("ui", this);
        stage.layers.addToLayer("ui", fill);
    }

    public void shrink(){
        shrink = Tween.to(this, CountMeterAccessor.SHRINK, .5f).target(0.0f).ease(Quad.INOUT).start(stage.tweens);
        shrink.setCallback(this);
    }

    public void increment(float step){
        //TODO - Fix tween issues, try tweening fill directly rather than as a property of the meter

        fill.setScale(fill.getScaleX(), step);
        //grow = Tween.to(this, CountMeterAccessor.GROW, .3f).target(step).ease(Quad.INOUT).start(stage.tweens);
    }

    @Override
    public void onEvent(int type, BaseTween<?> source) {
        fill.setColor(Color.WHITE);
    }
}

class CountMeterAccessor implements TweenAccessor<CountMeter>{

    final static int SHRINK = 1;
    final static int GROW = 2;

    @Override
    public int getValues(CountMeter target, int tweenType, float[] returnValues) {
        switch (tweenType){
            case SHRINK:
                returnValues[0] = target.fill.getScaleY();
                return 1;
            case GROW:
                System.out.println("Entry Value: " +  target.fill.getScaleY());
                returnValues[0] = target.fill.getScaleY();
                return 1;
        }

        return 0;
    }

    @Override
    public void setValues(CountMeter target, int tweenType, float[] newValues) {
        switch (tweenType){
            case SHRINK:
                target.fill.setScale(target.fill.getScaleX(), newValues[0]);
                target.fill.setColor(MathUtils.random(0f, 1f), MathUtils.random(0f, 1f), MathUtils.random(0f, 1f), 1);
                break;
            case GROW:

                System.out.println("Steps --: " + newValues[0]);

                target.fill.setScale(target.fill.getScaleX(), newValues[0]);
                break;
        }
    }
}
