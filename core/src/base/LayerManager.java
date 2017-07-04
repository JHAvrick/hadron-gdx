package base;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


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

    public void addToBackground(Sprite s){
        background.add(s);
    }

    public void addLayer(String key){
        layers.put(key, new Layer());
    }

    public void addToLayer(String key, Sprite s){
        if (layers.containsKey(key)){
            layers.get(key).add(s);
        }
    }

    public void addToForeground(){

    }

    public void addToUI(Sprite s){
        ui.add(s);
    }

    public void addToOverlay(Sprite s){
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

class Layer extends ArrayList<Sprite> {

    public Layer(){}

    public void draw(SpriteBatch batch){
        for (Sprite s : this){
            s.draw(batch);
        }
    }
}
