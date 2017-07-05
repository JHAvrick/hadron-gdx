package base.managers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import base.sprite.Spryte;


/**
 * Created by Cloud Strife on 7/2/2017.
 */

public class LayerManager {

    SpriteBatch batch;
    Layer background; //Always in the back
    LinkedHashMap<String, Layer> layers;
    Layer foreground; //Always on top
    Layer ui;
    Layer overlay;

    public LayerManager(){
        this.batch = batch;

        background = new Layer();
        layers = new LinkedHashMap<String, Layer>();
        foreground = new Layer();
        ui = new Layer();
        overlay = new Layer();
    }

    public void addToBackground(Spryte s){
        background.add(s);
    }

    public void addLayer(String key){
        layers.put(key, new Layer());
    }

    public void addToLayer(String key, Spryte s){
        if (layers.containsKey(key)){
            layers.get(key).add(s);
        }
    }

    public void addToForeground(Spryte s){
        foreground.add(s);
    }

    public void addToUI(Spryte s){
        ui.add(s);
    }

    public void addToOverlay(Spryte s){
        overlay.add(s);
    }

    public void draw(SpriteBatch batch){
        background.draw(batch);

        //Draw all the layers in between back and foreground
        for (Map.Entry<String, Layer> layer : layers.entrySet()) {
            layer.getValue().draw(batch);
        }

        foreground.draw(batch);
        ui.draw(batch);
        overlay.draw(batch);
    }
}

class Layer extends ArrayList<Spryte> {

    public Layer(){}

    public void draw(SpriteBatch batch){
        for (Spryte s : this){
            if (s.visible){
                s.draw(batch);
            }
        }
    }
}
