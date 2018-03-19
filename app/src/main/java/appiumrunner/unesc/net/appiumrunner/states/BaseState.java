package appiumrunner.unesc.net.appiumrunner.states;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by fabri on 18/03/2018.
 */

public class BaseState<T> implements Serializable {
    private static ArrayList<BaseState> steps;
    private boolean focusedState;
    private boolean visibility = true;
    private boolean enabled = true;
    private int elementIdState;

    public BaseState() {
        if (steps == null) {
            steps = new ArrayList<>();
        }
    }

    public int getElementIdState() {
        return elementIdState;
    }

    public T setElementId(int elementIdState) {
        this.elementIdState = elementIdState;
        return (T) this;
    }

    public T setFocusedState(boolean focusedState) {
        this.focusedState = focusedState;
        return (T) this;
    }

    public T setVisibility(boolean visibility) {
        this.visibility = visibility;
        return (T) this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public T setEnabled(boolean enabled) {
        this.enabled = enabled;
        return (T) this;
    }


    public void record() {
        steps.add(this);
        if (steps.size() > 0) {

        }
    }
}
