package objects;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.equations.Quad;
import base.state.GameState;
import base.sprite.Spryte;

/**
 * Created by Cloud Strife on 7/4/2017.
 */

public class HadronTitle extends Spryte {

    public Spryte leftBracket, rightBracket;
    public Tween introSequence;

    public HadronTitle(GameState stage) {
        super(stage, stage.width / 2, stage.height, stage.textures.region("menu", "title"));
        setCenter(stage.width / 2, stage.height);
        //setAlpha(0);

        leftBracket = new Spryte(stage, 0, stage.height / 1.25f, stage.textures.region("menu", "arc"));
        leftBracket.setCenter(0, stage.height / 1.25f);

        rightBracket = new Spryte(stage, stage.width, stage.height / 1.25f, stage.textures.region("menu", "arc"));
        rightBracket.setCenter(stage.width + rightBracket.getWidth(), stage.height / 1.25f);
        rightBracket.flip(true, false);

        leftBracket.setAlpha(0);
        rightBracket.setAlpha(0);

        Tween.registerAccessor(HadronTitle.class, new HadronTitleAccessor());
        introSequence = Tween.to(this, 0, 3)
                        .target(1, stage.height / 1.25f, stage.width / 12, stage.width - (stage.width / 12))
                        .ease(Quad.OUT);

        stage.layers.addToLayer("ui", this);
        stage.layers.addToLayer("ui", leftBracket);
        stage.layers.addToLayer("ui", rightBracket);
    }
}

class HadronTitleAccessor implements TweenAccessor<HadronTitle> {

    @Override
    public int getValues(HadronTitle target, int tweenType, float[] returnValues) {
        returnValues[0] = target.getColor().a;
        returnValues[1] = target.getY();
        returnValues[2] = target.leftBracket.getX();
        returnValues[3] = target.rightBracket.getX();
        return 4;
    }

    @Override
    public void setValues(HadronTitle target, int tweenType, float[] newValues) {

        //Alpha
        target.setAlpha(newValues[0]);
        target.leftBracket.setAlpha(newValues[0]);
        target.rightBracket.setAlpha(newValues[0]);

        //Position
        target.setCenterY(newValues[1]);
        target.leftBracket.setCenterX(newValues[2]);
        target.rightBracket.setCenterX(newValues[3]);
    }
}
