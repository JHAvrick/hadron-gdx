package objects;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.equations.Quad;
import base.state.GameState;
import base.sprite.InputSprite;
import base.sprite.Spryte;
import base.input.SignalCallback;

/**
 * Created by Cloud Strife on 7/3/2017.
 */

public class HadronMenuButton extends InputSprite {

    public Spryte halo;
    private Tween fadeIn, expand;

    public HadronMenuButton(GameState stage, float x, float y, String buttonTexture, String haloTexture) {
        super(stage, x, y, stage.textures.region("menu", buttonTexture));
        this.setCenter(x, y);

        halo = new Spryte(stage, this.getX(), this.getY(), stage.textures.region("menu", haloTexture));
        halo.setCenter(x,y);

        stage.layers.addToLayer("ui", this);
        stage.layers.addToLayer("ui", halo);
        stage.recievesUpdate(this);

        this.fadeAlpha(0); //Make this invisible to start
        Tween.registerAccessor(HadronMenuButton.class, new MenuButtonAccessor());
        fadeIn = Tween.to(this, 1, 2).target(1).ease(Quad.INOUT);
        expand = Tween.to(this, 2, 2).target(3,3,0).ease(Quad.INOUT);

        signals.addEventType("play");
        signals.on("touchDown", new SignalCallback() {
            @Override
            public void onSignal() {
                expand();
            }
        });
    }

    public void fadeIn(){
        fadeIn.start(stage.tweens);
    }

    public void expand(){
        expand.start(stage.tweens);
    }

    public void fadeAlpha(float alpha){
        setAlpha(alpha);
        halo.setAlpha(alpha);
    }

    public float getAlpha(){
        return this.getColor().a;
    }

    public void update(){
        halo.setRotation(halo.getRotation() - 1);
    }
}

class MenuButtonAccessor implements TweenAccessor<HadronMenuButton> {
    public static final int FADE_IN = 1;
    public static final int EXPAND_FADE = 2;

    @Override
    public int getValues(HadronMenuButton target, int tweenType, float[] returnValues) {
        switch(tweenType){
            case FADE_IN: returnValues[0] = target.getAlpha(); return 1;
            case EXPAND_FADE:
                returnValues[0] = target.halo.getScaleX();
                returnValues[1] = target.halo.getScaleY();
                returnValues[2] = target.halo.getColor().a;
                return 3;
            default: assert false; return 0;
        }
    }

    @Override
    public void setValues(HadronMenuButton target, int tweenType, float[] newValues) {
        switch (tweenType){
            case FADE_IN: target.fadeAlpha(newValues[0]); break;
            case EXPAND_FADE:
                target.halo.setScale(newValues[0], newValues[1]);
                target.halo.setAlpha(newValues[2]);
                break;
            default: assert false; break;
        }
    }
}
