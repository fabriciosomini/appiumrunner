package appiumrunner.unesc.net.appiumrunner.states;

/**
 * Created by fabri on 18/03/2018.
 */

public class BaseState {
    private boolean focusedState;
    private boolean visibility;
    private boolean enabled;
    private int elementIdState;

    public int getElementIdState() {
        return elementIdState;
    }

    public void setElementId(int elementIdState) {
        this.elementIdState = elementIdState;
    }

    public void setFocusedState(boolean focusedState) {
        this.focusedState = focusedState;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
