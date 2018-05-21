package unesc.com.unesctcc3.utilitarios;

import android.view.View;

public class IdUtilitario {
    public static String getStringId(View view) {
        String id = view.getResources().getResourceName(view.getId());
        String parts[] = id.split("/");
        return parts[parts.length - 1];
    }
}
