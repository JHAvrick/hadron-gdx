package base.managers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import base.sprite.Spryte;
import base.state.GameState;

/**
 * This is a naive implementation of collision detection, suitable only for very simple
 * cases. Each collision group can be checked against other collisions groups (or itself)
 * and each detected collision will trigger a callback. This implementation does not use
 */

/*

 */

public class CollisionManager {

    HashMap<String, CollisionGroup> groups;

    public CollisionManager(){
        groups = new HashMap<String, CollisionGroup>();
    }

    public CollisionGroup addGroup(String key){
        groups.put(key, new CollisionGroup());
        return groups.get(key);
    }

    public void enableSelfCollision(String key, CollisionCallback callback){
        groups.get(key).enableSelfCollision(callback);
    }

    public void disableSelfCollision(String key){
        groups.get(key).disableSelfCollision();
    }

    public void addToGroup(String key, Spryte sprite){
        groups.get(key).add(sprite);
    }

    public void addToGroup(String key, ArrayList<? extends Spryte> sprites){
        CollisionGroup group = groups.get(key);
        for (Spryte s : sprites){
            group.add(s);
        }
    }

    public void removeFromGroup(String key, Spryte sprite){
        groups.get(key).remove(sprite);
    }

    public void collides(String keyOne, String keyTwo, CollisionCallback callback){
        if (keyOne == keyTwo) return;
        groups.get(keyOne).collidesWith(groups.get(keyTwo), callback);
    }

    public void checkCollisions(){
        for (Map.Entry<String, CollisionGroup> entry : groups.entrySet()){
            entry.getValue().checkCollisions();
        }
    }

}


class CollisionGroup extends ArrayList<Spryte> {

    private HashMap<CollisionGroup, CollisionCallback> collides;
    private CollisionCallback selfCollisionCallback;
    private boolean selfCollides = false;

    public CollisionGroup() {
        collides = new HashMap<CollisionGroup, CollisionCallback>();
    }

    public void collidesWith(CollisionGroup group, CollisionCallback callback){
        collides.put(group, callback);
    }

    public void enableSelfCollision(CollisionCallback callback){
        this.selfCollides = true;
        this.selfCollisionCallback = callback;
    }

    public void disableSelfCollision(){
        this.selfCollides = false;
    }

    public void setSelfCollisionCallback(CollisionCallback callback){
        this.selfCollisionCallback = callback;
    }

    public void checkSelfCollision(){
        for (Spryte selfSpryte : this){
            for (Spryte otherSpryte: this){
                if (selfSpryte != otherSpryte){

                    //Update the position of each sprites collision box
                    selfSpryte.collisionBox.setCenter(selfSpryte.getX(), selfSpryte.getY());
                    otherSpryte.collisionBox.setCenter(otherSpryte.getX(), otherSpryte.getY());

                    if (selfSpryte.getBoundingRectangle().overlaps(otherSpryte.getBoundingRectangle())){
                        selfCollisionCallback.onCollision(selfSpryte, otherSpryte);
                    }
                }
            }
        }
    }

    public void checkCollisions(){
        //For every collision group that this one collides with
        for (Map.Entry<CollisionGroup, CollisionCallback> entry : collides.entrySet()){

            //For every sprite in this collision group
            for (Spryte ownSpryte : this){

                //Check against every sprite in the other collision group
                for (Spryte otherSpryte : entry.getKey()){

                    //Update the position of each sprites collision box
                    ownSpryte.collisionBox.setCenter(ownSpryte.getX(), ownSpryte.getY());
                    otherSpryte.collisionBox.setCenter(otherSpryte.getX(), otherSpryte.getY());

                    if (ownSpryte.collisionBox.overlaps(otherSpryte.collisionBox)){
                        entry.getValue().onCollision(ownSpryte, otherSpryte);
                    }

                    /*
                    if (ownSpryte.getBoundingRectangle().overlaps(otherSpryte.getBoundingRectangle())){
                        entry.getValue().onCollision(ownSpryte, otherSpryte);
                    }
                    */

                }
            }
        }

        if (selfCollides) checkSelfCollision();
    }

}

