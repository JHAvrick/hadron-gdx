package base.managers;

import base.sprite.Spryte;

/**
 * Created by Cloud Strife on 7/5/2017.
 */

public interface CollisionCallback {
    public void onCollision(Spryte one, Spryte two);
}
