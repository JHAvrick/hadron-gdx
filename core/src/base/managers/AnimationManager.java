package base.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.HashMap;

import base.sprite.Spryte;

/**
 * Created by Cloud Strife on 7/5/2017.
 */

public class AnimationManager {
    private final Spryte target;

    boolean hasActiveAnimation;
    float animStateTime;

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

    public void play(String key){
        if (animations.containsKey(key)){
            playing = animations.get(key);
            hasActiveAnimation = true;
            animStateTime = 0;
        }
    }

    public void updateAnimation(){
        if (hasActiveAnimation){
            animStateTime += Gdx.graphics.getDeltaTime();
            target.setRegion(playing.getKeyFrame(animStateTime, true));
            target.setBounds(target.getX(), target.getY(), target.getRegionWidth(), target.getRegionHeight());
        }
    }


}
