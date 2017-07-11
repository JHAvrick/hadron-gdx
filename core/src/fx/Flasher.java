package fx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Quad;
import base.input.SignalCallback;
import base.sprite.Spryte;
import base.state.GameState;

/**
 * Created by Cloud Strife on 7/8/2017.
 */

public class Flasher extends Spryte implements TweenCallback {
    private final GameState stage;
    private boolean isFlashing, force;
    private Tween flash;
    private SignalCallback callback;

    public Flasher(GameState stage) {
        super(stage);
        this.stage = stage;
        this.setVisible(false);
        this.setActive(false);

        Pixmap sheet = new Pixmap(stage.width, stage.height, Pixmap.Format.RGBA8888);
        sheet.setColor(Color.WHITE);
        sheet.fillRectangle(0, 0, stage.width, stage.height);
        this.setTexture(new Texture(sheet, Pixmap.Format.RGB888, false));

        Tween.registerAccessor(Flasher.class, new FlasherAccessor());

        stage.layers.addToLayer("overlay", this);
    }

    public void forceFlash(boolean force){
        this.force = force;
    }

    public void setCallback(SignalCallback callback){
        this.callback = callback;
    }

    public void flash(float duration, Color color, SignalCallback callback){
        if (isFlashing && !force) return;
        if (flash != null) flash.kill();

        this.callback = callback;
        this.setVisible(true);
        this.setAlpha(1);

        flash = Tween.to(this, 0, duration).target(0).ease(Quad.INOUT);
        flash.setCallback(this);
        flash.start(stage.tweens);
    }

    public void flash(float duration, Color color){
        if (isFlashing && !force) return;
        if (flash != null) flash.kill();

        this.setVisible(true);
        this.setAlpha(1);

        flash = Tween.to(this, 0, duration).target(0).ease(Quad.INOUT);
        flash.setCallback(this);
        flash.start(stage.tweens);
    }

    public void flash(float duration){
        if (isFlashing && !force) return;
        if (flash != null) flash.kill();

        this.setVisible(true);
        this.setAlpha(1);

        flash = Tween.to(this, 0, duration).target(0).ease(Quad.INOUT);
        flash.setCallback(this);
        flash.start(stage.tweens);
    }


    @Override
    public void onEvent(int type, BaseTween<?> source) {
        this.setVisible(false);
        isFlashing = false;

        if (this.callback != null) callback.onSignal();
    }
}

class FlasherAccessor implements TweenAccessor<Flasher>{

    @Override
    public int getValues(Flasher target, int tweenType, float[] returnValues) {
        returnValues[0] = target.getColor().a;
        return 1;
    }

    @Override
    public void setValues(Flasher target, int tweenType, float[] newValues) {
        target.setAlpha(newValues[0]);
    }
}
