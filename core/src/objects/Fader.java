package objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Quad;
import base.state.GameState;
import base.sprite.Spryte;

/**
 * Created by Cloud Strife on 7/3/2017.
 */

public class Fader extends Spryte {

    private Pixmap sheet;
    private Texture sheetTexture;

    public Fader(GameState stage, float x, float y) {
        super(stage, x, y);
        setAlpha(0);

        sheet = new Pixmap(stage.width, stage.height, Pixmap.Format.RGBA8888);
        sheet.setColor(Color.BLACK);
        sheet.fillRectangle(0, 0, stage.width, stage.height);
        sheetTexture = new Texture(sheet, Pixmap.Format.RGB888, false);

        setTexture(sheetTexture);
        this.setBounds(0, 0, sheetTexture.getWidth(), sheetTexture.getHeight());

        Tween.registerAccessor(Fader.class, new FaderAccessor());

        stage.layerManager.addToOverlay(this);
    }

    public void fadeIn(float duration, TweenCallback callback){
        this.setAlpha(1);
        Tween fadeOut = Tween.to(this, 1, duration).target(0).ease(Quad.INOUT);
        fadeOut.setCallback(callback);
        fadeOut.start(stage.tweens);
    }

    public void fadeIn(float duration){
        this.setAlpha(1);
        Tween fadeOut = Tween.to(this, 1, duration).target(0).ease(Quad.INOUT);
        fadeOut.start(stage.tweens);
    }

    public void fadeOut(float duration, TweenCallback callback){
        Tween fadeOut = Tween.to(this, 2, duration).target(1).ease(Quad.INOUT);
        fadeOut.start(stage.tweens);
        fadeOut.setCallback(callback);
    }

    public void fadeOut(float duration){
        Tween fadeOut = Tween.to(this, 2, duration).target(1).ease(Quad.INOUT);
        fadeOut.start(stage.tweens);
    }
}

class FaderAccessor implements TweenAccessor<Fader> {

    public static final int FADE_IN = 1;
    public static final int FADE_OUT = 2;

    @Override
    public int getValues(Fader target, int tweenType, float[] returnValues) {
        switch (tweenType){
            case FADE_IN: returnValues[0] = target.getColor().a; return 1;
            case FADE_OUT: returnValues[0] = target.getColor().a; return 1;
        }
        return 0;
    }

    @Override
    public void setValues(Fader target, int tweenType, float[] newValues) {
        switch (tweenType){
            case FADE_IN: target.setAlpha(newValues[0]);
            case FADE_OUT: target.setAlpha(newValues[0]);
        }
    }

}
