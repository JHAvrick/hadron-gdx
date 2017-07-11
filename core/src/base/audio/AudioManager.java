package base.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import java.util.HashMap;
import java.util.Map;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.equations.Quad;
import base.state.GameState;

/**
 * Created by Cloud Strife on 7/4/2017.
 */

public class AudioManager {

    public final GameState stage;
    public HashMap<String, Music> music;
    public HashMap<String, Sound> sounds;

    public AudioManager(GameState stage){
        this.stage = stage;

        music = new HashMap<String, Music>();
        sounds = new HashMap<String, Sound>();

        Tween.registerAccessor(Music.class, new MusicAccessor());
    }

    public void addMusic(String key, String file){
        music.put(key, Gdx.audio.newMusic(Gdx.files.internal(file)));
        System.out.println(music.get(key));
    }

    public void addFX(String key, String file){
        sounds.put(key, Gdx.audio.newSound(Gdx.files.internal(file)));
    }

    public void loop(String key){
        if (music.containsKey(key)){
            music.get(key).setLooping(true);
            music.get(key).play();
        }
    }

    public void fade(String key, float newVolume, float duration){
        if (music.containsKey(key)){
            Tween.to(music.get(key), 1, duration).cast(Music.class).target(newVolume).ease(Quad.OUT).start(stage.tweens);
        }
    }

    public void playFX(String key){
        if (sounds.containsKey(key)){
            sounds.get(key).play();
        }
    }

    public void playFX(String key, float volume, float pitch, float pan){
        if (sounds.containsKey(key)){
            sounds.get(key).play(volume, pitch, pan);
        }
    }

    public void dispose(String key){
        if (music.containsKey(key)){

            music.get(key).dispose();
            music.remove(key);

        } else if (sounds.containsKey(key)){

            sounds.get(key).dispose();
            sounds.remove(key);

        }
    }

    public void disposeAll(){
        for (Map.Entry<String, Music> entry : music.entrySet()){
            entry.getValue().dispose();
        }

        for (Map.Entry<String, Sound> entry : sounds.entrySet()){
            entry.getValue().dispose();
        }
    }
}

class MusicAccessor implements TweenAccessor<Music> {

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


