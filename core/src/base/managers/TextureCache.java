package base.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cloud Strife on 7/3/2017.
 */

public class TextureCache {

    HashMap<String,Texture> textures;
    HashMap<String,TextureAtlas> atlases;

    public TextureCache(){
        textures = new HashMap<String, Texture>();
        atlases = new HashMap<String, TextureAtlas>();
    }

    public void addTexture(String key, String path){
       textures.put(key, new Texture(Gdx.files.internal(path)));
    }

    public void addAtlas(String key, String path){
        atlases.put(key,new TextureAtlas(Gdx.files.internal(path)));
    }

    public Texture texture(String key){
        return textures.get(key);
    }

    public TextureAtlas atlas(String key){
        return atlases.get(key);
    }

    public TextureRegion region(String key, String region){
        return atlases.get(key).findRegion(region);
    }

    public void dispose(){
        for (Map.Entry<String, Texture> entry : textures.entrySet()){
            entry.getValue().dispose();
        }

        for (Map.Entry<String, TextureAtlas> entry : atlases.entrySet()){
            entry.getValue().dispose();
        }
    }
}
