package appiumrunner.unesc.net.appiumrunner.states;

/**
 * Created by fabri on 18/03/2018.
 */

public class ListViewState extends BaseState<ListViewState> {
    private int count;

    public ListViewState setCount(int count) {
        this.count = count;
        return this;
    }
}
