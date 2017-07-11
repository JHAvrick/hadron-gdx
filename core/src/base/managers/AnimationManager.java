package base.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import base.sprite.Spryte;

/**
 * Created by Cloud Strife on 7/5/2017.
 */

public class AnimationManager {
    private final Spryte target;

    boolean hasActiveAnimation;
    float animStateTime;

    TextureRegion currentFrame;
    Animation<TextureRegion> playing;
    HashMap<String, Animation<TextureRegion>> animations;

    public AnimationManager(Spryte target){
        this.target = target;

        hasActiveAnimation = false;
        animStateTime = 0;

        animations = new HashMap<String, Animation<TextureRegion>>();
    }

    public Animation add(String key, float fps, Array<? extends TextureRegion> regions, Animation.PlayMode mode){
        animations.put(key, new Animation<TextureRegion>( 1f / fps, regions, mode));
        return animations.get(key);
    }

    /*
     *
     */
    public void play(String key){
        if (animations.containsKey(key)){
            playing = animations.get(key);
            hasActiveAnimation = true;
            animStateTime = 0;

            /*
             * First frame is set immediately, otherwise any access to width/height before the next render
             * call will return zero.
             */
            target.setRegion(playing.getKeyFrame(0));
            target.setBounds(target.getX(), target.getY(), target.getRegionWidth(), target.getRegionHeight());
        }
    }

    public void stop(){
        hasActiveAnimation = false;
    }

    public int frameWidth(){
        if (currentFrame != null) return currentFrame.getRegionWidth();
        else return 0;
    }

    public int frameHeight(){
        if (currentFrame != null) return currentFrame.getRegionHeight();
        else return 0;
    }

    public void updateAnimation(){
        if (hasActiveAnimation){
            animStateTime += Gdx.graphics.getDeltaTime();
            currentFrame = playing.getKeyFrame(animStateTime, true);
            target.setRegion(currentFrame);
            //target.setBounds(target.getX(), target.getY(), target.getRegionWidth(), target.getRegionHeight());
        }
    }

}
