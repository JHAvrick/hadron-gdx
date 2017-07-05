package base.tweens;

import com.badlogic.gdx.audio.Music;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by Cloud Strife on 7/4/2017.
 */

public class MusicAccessor implements TweenAccessor<Music> {

    public static final int FADE = 1;

    @Override
    public int getValues(Music target, int tweenType, float[] returnValues) {
        switch (tweenType){
            case FADE: returnValues[0] = target.getVolume(); return 1;
        }
        return 0;
    }

    @Override
    public void setValues(Music target, int tweenType, float[] newValues) {
        switch (tweenType){
            case FADE: target.setVolume(newValues[0]);
        }
    }

}
