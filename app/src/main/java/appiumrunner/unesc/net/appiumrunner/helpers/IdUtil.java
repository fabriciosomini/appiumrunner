package appiumrunner.unesc.net.appiumrunner.helpers;

import android.view.View;

public class IdUtil {
    public static String getStringId(View view) {
        String id = view.getResources().getResourceName(view.getId());
        String parts[] = id.split("/");
        return parts[parts.length - 1];
    }
}
