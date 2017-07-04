package base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import base.GameState;
import base.input.Signaler;

/**
 * Created by Cloud Strife on 7/2/2017.
 */

public class InputSprite extends Spryte implements InputProcessor {

    public Signaler signals;

    public InputSprite(GameState stage, float x, float y, Texture texture){
        super(stage, x, y, texture);
        this.stage.receivesInput(this);

        signals = new Signaler();
        signals.addEventType("touchDown");
        signals.addEventType("touchUp");
        signals.addEventType("touchDragged");
    }

    public InputSprite(GameState stage, float x, float y, TextureRegion region){
        super(stage, x, y, region);
        this.stage.receivesInput(this);

        signals = new Signaler();
        signals.addEventType("touchDown");
        signals.addEventType("touchUp");
        signals.addEventType("touchDragged");
    }

    public void update(){

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector3 transformed = stage.camera.unproject(new Vector3(screenX, screenY, 0));
        if (this.getBoundingRectangle().contains(transformed.x, transformed.y)){
            signals.dispatch("touchDown");
            return true;
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        Vector3 transformed = stage.camera.unproject(new Vector3(screenX, screenY, 0));
        if (this.getBoundingRectangle().contains(transformed.x, transformed.y)){
            signals.dispatch("touchUp");
            return true;
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        Vector3 transformed = stage.camera.unproject(new Vector3(screenX, screenY, 0));
        if (this.getBoundingRectangle().contains(transformed.x, transformed.y)){
            signals.dispatch("touchDragged");
            return true;
        }

        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

