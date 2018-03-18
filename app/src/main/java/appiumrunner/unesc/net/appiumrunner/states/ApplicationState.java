package appiumrunner.unesc.net.appiumrunner.states;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fabri on 18/03/2018.
 */

public class ApplicationState {

    private Map<String, String> informations;

    public ApplicationState() {

        informations = new HashMap<>();
    }

    public void addInfo(String key, String value) {
        informations.put(key, value);
    }

    public Object getInfo(String key) {
        return informations.get(key);
    }
}
