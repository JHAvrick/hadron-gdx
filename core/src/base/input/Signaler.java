package base.input;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Cloud Strife on 7/3/2017.
 */

public class Signaler {

    HashMap<String, ArrayList<SignalCallback>> signals;

    public Signaler(){
        signals = new HashMap<String, ArrayList<SignalCallback>>();
    }

    public void addEventType(String key){
        signals.put(key, new ArrayList<SignalCallback>());
    }

    public void on(String key, SignalCallback e){
        if (signals.containsKey(key)){
            signals.get(key).add(e);
        }
    }

    public void dispatch(String key){
        if (signals.containsKey(key)){
            for (SignalCallback e : signals.get(key)){
                e.onSignal();
            }
        }
    }
}
