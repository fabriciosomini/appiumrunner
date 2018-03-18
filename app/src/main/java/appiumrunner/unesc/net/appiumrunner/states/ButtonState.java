package appiumrunner.unesc.net.appiumrunner.states;

/**
 * Created by fabri on 18/03/2018.
 */

public class ButtonState extends BaseState<ButtonState> {
    private String hint;

    public ButtonState setHint(String hint) {
        this.hint = hint;
        return this;
    }
}
