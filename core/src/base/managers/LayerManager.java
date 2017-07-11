package base.managers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import base.sprite.Spryte;


/**
 * Created by Cloud Strife on 7/2/2017.
 */

public class LayerManager {

    LinkedHashMap<String, Layer> layers;

    public LayerManager(){
        layers = new LinkedHashMap<String, Layer>();
    }

    public void addLayer(String key){
        layers.put(key, new Layer());
    }

    public void addToLayer(String key, Spryte s){
        if (layers.containsKey(key)){
            layers.get(key).add(s);
        }
    }

    public void setLayerShader(String key, ShaderProgram shader){
        if (layers.containsKey(key)){
            layers.get(key).setShader(shader);
        }
    }

    private void applyShader(Layer layer){
    }

    public void draw(SpriteBatch batch){
        //Draw all the layers in between back and foreground
        for (Map.Entry<String, Layer> entry : layers.entrySet()) {
            Layer layer = entry.getValue();

            if (layer.hasShader()){
                batch.end();

                batch.setShader(layer.getShader());
                batch.begin();
                layer.draw(batch);
                batch.end();

                batch.setShader(null);
                batch.begin();
            } else {

                layer.draw(batch);

            }
        }
    }
}

class Layer extends ArrayList<Spryte> {

    public ShaderProgram shader;
    private boolean hasShader;

    public Layer(){}

    public void draw(SpriteBatch batch){
        for (Spryte s : this){
            if (s.isVisible()){
                s.draw(batch);
            }
        }
    }

    public void setShader(ShaderProgram shader){
        this.shader = shader;
        this.hasShader = true;
    }

    public ShaderProgram getShader(){
        return shader;
    }

    public void removeShaders(){
        this.shader = null;
        this.hasShader = false;
    }

    public boolean hasShader(){
        return hasShader;
    }
}
