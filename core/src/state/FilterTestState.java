package state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import base.sprite.Spryte;
import base.state.GameMaster;
import base.state.GameState;

/**
 * Created by Cloud Strife on 7/9/2017.
 */

public class FilterTestState extends GameState {

    Spryte sprite, otherSprite;
    ShaderProgram rgb;

    public FilterTestState(GameMaster game, int width, int height) {
        super(game, width, height);
        textures.addAtlas("menu", "images/menu.txt");

        setBackgroundColor(new Color(200, 200, 200, 255));

        sprite = new Spryte(this, halfWidth, halfHeight, textures.atlas("menu").findRegion("title"));
        sprite.setCenter(halfWidth, halfHeight);

        otherSprite = new Spryte(this, halfWidth, halfHeight+ 200, textures.atlas("menu").findRegion("title"));
        otherSprite.setCenter(halfWidth, halfHeight + 200);

        rgb = new ShaderProgram(Gdx.files.internal("shaders/rgb/rgb.vert"), Gdx.files.internal("shaders/rgb/rgb.frag"));

        layers.addLayer("test");
        layers.addLayer("testTwo");

        layers.setLayerShader("test", rgb);
        layers.addToLayer("test", sprite);
        layers.addToLayer("testTwo", otherSprite);

    }

    @Override
    public void render(float delta){
        super.render(delta);


        game.batch.begin();
        layers.draw(game.batch);
        game.batch.end();

    }
}
