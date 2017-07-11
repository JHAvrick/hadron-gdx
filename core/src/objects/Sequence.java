package objects;


import com.badlogic.gdx.math.MathUtils;
import net.dermetfan.gdx.utils.ArrayUtils;
import java.util.ArrayList;
import java.util.Collections;
import objects.Phase;

/**
 * Created by Cloud Strife on 7/5/2017.
 */

public class Sequence {
    public String name;
    public ArrayList<String> palette, shapes;
    public boolean shuffle;
    public int minSpeed, maxSpeed, speedStep, minCount, maxCount, countStep, interval, intervalStep;
    public ArrayList<Integer> typeWeight, sequenceMix;
    public int sequenceStep;
    //public ArrayList<ArrayList<ArrayList<Integer>>> sequenceSimple, sequenceComplex;
    public int[][][] sequenceSimple, sequenceComplex;

    public Sequence(){}


    public Phase getPhase(int phaseIndex){
        Phase phase = new Phase();
        phase.palette = palette;
        phase.shapes = shapes;
        phase.type = typeWeight.get(MathUtils.random(0, typeWeight.size() - 1));
        phase.shape = shapes.get(MathUtils.random(0, shapes.size() - 1));
        phase.color = palette.get(MathUtils.random(0, palette.size() - 1));
        phase.minSpeed = minSpeed + (speedStep * phaseIndex);
        phase.maxSpeed = maxSpeed + (speedStep * phaseIndex);
        phase.count = MathUtils.random(minCount + (countStep * phaseIndex), maxCount + (countStep * phaseIndex));
        phase.interval = interval - (intervalStep * phaseIndex);
        phase.sequence = buildSequence(phaseIndex);

        return phase;
    }

    private ArrayList<int[]> buildSequence(int phaseIndex){

        ArrayList<int[]> sequence = new ArrayList<int[]>();

        //Loop through sequence mix and add the appropriate sequence types to the mix
        //0 = simple sequence
        //1 = complex sequence
        for (Integer sequenceType : sequenceMix){
            if (sequenceType == 0){

                int[][] simple = sequenceSimple[MathUtils.random(0, sequenceSimple.length - 1)];

                if (shuffle) ArrayUtils.shuffle(simple);

                Collections.addAll(sequence, simple);

            } else {
                int[][] complex = sequenceComplex[MathUtils.random(0, sequenceComplex.length - 1)];

                if (shuffle) ArrayUtils.shuffle(complex);

                Collections.addAll(sequence, complex);
            }
        }

        //Add more complex sequences depending on the phase
        int sequenceSteps = sequenceStep * phaseIndex;
        for (int i = 0; i < sequenceSteps; i++){
            int[][] complex = sequenceComplex[MathUtils.random(0, sequenceComplex.length - 1)];

            if (shuffle) ArrayUtils.shuffle(complex);

            Collections.addAll(sequence, complex);
        }

        return sequence;
    }


}

