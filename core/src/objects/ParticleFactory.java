package objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.Arrays;

import base.input.SignalCallback;
import base.input.Signaler;
import base.state.GameState;

/**
 * Created by Cloud Strife on 7/5/2017.
 */

public class ParticleFactory {
    private final GameState stage;
    private final HadronBoard board;
    private Phase phase;
    private Timer timer = new Timer();
    private int patternIndex = 0;

    public Signaler signals = new Signaler();

    //Particles move between these two lists depending on their state
    public ArrayList<Particle> resting = new ArrayList<Particle>();
    public ArrayList<Particle> active = new ArrayList<Particle>();

    //Lists used to parse the different particle types and behaviors in each sequence
    private ArrayList<Integer> collectableOrbs = new ArrayList<Integer>(Arrays.asList(6,7,8,9,10,-6,-7,-8,-9,-10));
    private ArrayList<Integer> behaviorOrbs = new ArrayList<Integer>(Arrays.asList(2,3,4,-2,-3,-4,7,8,9,-7,-8,-9,11));

    public ParticleFactory(GameState stage, HadronBoard board){
        this.stage = stage;
        this.board = board;

        this.signals.addEventType("onParticleOutOfBounds");
        this.signals.addEventType("onParticlePassedPONR");
    }

    public ArrayList<Particle> makeParticles(int count){

        ArrayList<Particle> particles = new ArrayList<Particle>();
        for (int i = 0; i < count; i++){
            Particle p = new Particle(stage, this, board.cx, board.cy);
            resting.add(p);
            particles.add(p);
        }

        return particles;
    }

    public void startSequence(Phase phase){
        this.phase = phase;

        timer.clear();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                addParticle();
            }

        }, 0, 1);
        timer.start();

    }

    public void addParticle(){
        if (resting.size() == 0) return;

        int type = phase.type;
        for (int i = 0; i < 6; i++){
            int next = phase.sequence.get(patternIndex)[i];

            if (next != 0){

                String shape = (type == 1 || type == 3) && collectableOrbs.contains(next) ? phase.shape : randShape();
                String color = (type == 2 || type == 3) && collectableOrbs.contains(next) ? phase.color : randColor();

                float speedFactor = next > 0 ? -0.00001f : 0.00001f;
                float speed = speedFactor * MathUtils.random(phase.minSpeed, phase.maxSpeed);
                int trackRadius = board.getTrackRadius(i);

                Particle p = resting.get(0);
                resting.remove(0);
                active.add(p);

                p.start(shape, color, speed, trackRadius);

            }
        }

        //Increment pattern index
        patternIndex = (patternIndex == phase.sequence.size() - 1) ? 0 : patternIndex + 1;

    }

    public String randShape(){
        String shape = phase.shapes.get(MathUtils.random(0, phase.shapes.size() - 1));
        if ((phase.type == 1 || phase.type == 3) && shape == phase.shape) return randShape();
        else return shape;
    }

    public String randColor(){
        String color = phase.palette.get(MathUtils.random(0, phase.palette.size() - 1));
        if ((phase.type == 2 || phase.type == 3) && color == phase.color) return randColor();
        else return color;
    }

    public void setResting(Particle p){
        active.remove(p);
        resting.add(p);
    }

    public void endSequence(){
        timer.stop();
        clearAll();
    }

    public void clearAll(){
        for (Particle p : active){
            p.rest(true);
        }
        resting.addAll(active);
        active.clear();
    }

}

