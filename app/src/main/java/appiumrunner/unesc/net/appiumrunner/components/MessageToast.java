package appiumrunner.unesc.net.appiumrunner.components;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import appiumrunner.unesc.net.appiumrunner.R;

public class MessageToast {

    public static void show(Context context, String message) {
        show(context, message, Tipo.SUCESSO);
    }

    public static void show(Context context, String message, Tipo tipo) {
        Activity activity = ((Activity) context);
        if (activity != null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.toast_message, null);
            setColor(context, layout, tipo);

            TextView text = layout.findViewById(R.id.message_content);
            text.setText(message);

            Toast toast = new Toast(activity.getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }
    }

    private static void setColor(Context context, ConstraintLayout layout, Tipo tipo) {
        switch (tipo) {
            case ERRO:
                layout.setBackgroundColor(ContextCompat.getColor(context, R.color.redish));
                break;
            case SUCESSO:
                layout.setBackgroundColor(ContextCompat.getColor(context, R.color.greenish));
                break;
        }
    }

    public enum Tipo {SUCESSO, ERRO}
}
