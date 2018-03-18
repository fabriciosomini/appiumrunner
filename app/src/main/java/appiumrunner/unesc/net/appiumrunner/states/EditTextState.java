package appiumrunner.unesc.net.appiumrunner.states;

/**
 * Created by fabri on 18/03/2018.
 */

public class EditTextState extends BaseState<EditTextState> {
    private String hintState;
    private String textState;

    public EditTextState setHintState(String hintState) {
        this.hintState = hintState;
        return this;
    }

    public EditTextState setTextState(String textState) {
        this.textState = textState;
        return this;
    }
}
