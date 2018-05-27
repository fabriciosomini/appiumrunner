package appiumrunner.unesc.net.appiumrunner.components;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import appiumrunner.unesc.net.appiumrunner.R;

public class MessageToast {

    public static void show(Context context, String message) {
        Activity activity = ((Activity) context);
        if (activity != null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_message, null);
            TextView text = layout.findViewById(R.id.message_content);
            text.setText(message);

            Toast toast = new Toast(activity.getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }
    }
}
