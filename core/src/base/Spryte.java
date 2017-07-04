package base;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Cloud Strife on 7/3/2017.
 */

public class Spryte extends Sprite {

    public GameState stage;

    public Spryte(GameState stage, float x, float y, Texture texture){
        super(texture);
        this.stage = stage;
        this.setPosition(x, y);
    }

    public Spryte(GameState stage, float x, float y, TextureRegion region){
        super(region);
        this.stage = stage;
        this.setPosition(x, y);
    }

    public Spryte(GameState stage, float x, float y){
        super();
        this.stage = stage;
        this.setPosition(x, y);
    }

    public void update(){

    }

}
