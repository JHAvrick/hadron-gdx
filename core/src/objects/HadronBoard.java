package objects;

import java.util.ArrayList;

import base.state.GameState;
import base.sprite.Spryte;

/**
 * Created by Cloud Strife on 7/4/2017.
 */

public class HadronBoard {
    private final GameState stage;

    int trackRadius;
    int cx, cy;
    Spryte trackSprite;
    ArrayList<Track> tracks;

    public HadronBoard(GameState stage){
        this.stage = stage;

        trackRadius = 1250;
        cx = -(trackRadius - stage.width);
        cy = stage.height / 2;
        tracks = new ArrayList<Track>();

        trackSprite = new Spryte(this.stage, cx, 0, stage.textures.region("tracks", "tracks"));
        trackSprite.setBounds(cx, cy, trackSprite.getRegionWidth(), trackSprite.getRegionHeight());
        trackSprite.setCenterY(cy);

        stage.layers.addToLayer("mainTrack", trackSprite);

        makeTracks();
    }

    public int getTrackRadius(int trackNumber){
        return tracks.get(trackNumber).radius;
    }

    public void makeTracks(){
        int trackStartRadius = 700;
        int trackWidth = 100;
        for (int i = 0; i < 6; i++){
            tracks.add(new Track(i, cx, cy, trackStartRadius + (i * trackWidth)));
        }
    }
}

class Track {
    public int cx, cy, radius, trackNumber;

    public Track(int trackNumber, int cx, int cy, int radius) {
        this.trackNumber = trackNumber;
        this.radius = radius;
        this.cx = cx;
        this.cy = cy;
    }
}
