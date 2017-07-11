package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;

import base.sprite.Spryte;
import base.state.GameState;

/**
 * Created by Cloud Strife on 7/6/2017.
 */

public class Player extends Spryte implements InputProcessor {
    private final HadronBoard board;

    int currentTrack = 0;

    public Player(GameState stage, HadronBoard board, float x, float y) {
        super(stage, board.getTrackRadius(0), stage.height / 2);
        this.board = board;

        stage.audio.addFX("move", "audio/move.ogg");
        stage.audio.sounds.get("move").setPitch(1, 5);

        animations.add("glow", 15, stage.textures.atlas("sprites").findRegions("player"), Animation.PlayMode.LOOP);
        animations.play("glow");

        setCollisionBoxSize(30, 30);
        setCenter(board.cx + board.getTrackRadius(currentTrack), stage.halfHeight);

        stage.layers.addToLayer("player", this);
        stage.plexer.addProcessor(this);
    }

    @Override
    public void update(){
        setOriginCenter();
        setRotation(getRotation() - 1);
    }


    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT){
            if (currentTrack != 0){
                currentTrack -= 1;
                setCenterX(board.cx + board.getTrackRadius(currentTrack));
                stage.audio.playFX("move");
            }
        } else if (keycode == Input.Keys.RIGHT) {
            if (currentTrack != 5){
                currentTrack += 1;
                setCenterX(board.cx + board.getTrackRadius(currentTrack));
                stage.audio.playFX("move");
            }
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {return false;}

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (Gdx.input.getX() < stage.halfWidth){
            if (currentTrack != 0){
                currentTrack -= 1;
                setCenterX(board.cx + board.getTrackRadius(currentTrack));
                stage.audio.playFX("move");
            }
        } else {
            if (currentTrack != 5){
                currentTrack += 1;
                setCenterX(board.cx + board.getTrackRadius(currentTrack));
                stage.audio.playFX("move");
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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
