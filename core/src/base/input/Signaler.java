package base.input;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Cloud Strife on 7/3/2017.
 */

public class Signaler {

    HashMap<String, ArrayList<InputEvent>> signals;

    public Signaler(){
        signals = new HashMap<String, ArrayList<InputEvent>>();
    }

    public void addEventType(String key){
        signals.put(key, new ArrayList<InputEvent>());
    }

    public void on(String key, InputEvent e){
        if (signals.containsKey(key)){
            signals.get(key).add(e);
        }
    }

    public void dispatch(String key){
        if (signals.containsKey(key)){
            for (InputEvent e : signals.get(key)){
                e.onInput();
            }
        }
    }
}
