package base.sprite;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;

/**
 * Created by Cloud Strife on 7/12/2017.
 */

public interface Layerable {

    public void draw(Batch batch);
    public boolean isVisible();

}
