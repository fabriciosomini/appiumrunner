package appiumrunner.unesc.net.appiumrunner.components;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import appiumrunner.unesc.net.appiumrunner.R;

public class Popover extends DialogFragment {

    static String text;

    public static Popover show(Activity activity, String text) {
        Popover.text = text;
        Popover popover = new Popover();
        popover.setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_DeviceDefault_Dialog);
        popover.show(activity.getFragmentManager(), null);
        return popover;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Remove the default background
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Inflate the new view with margins and background
        View v = inflater.inflate(R.layout.popup_layout, container, false);

        // Set up a click listener to dismiss the popup if they click outside
        // of the background view
        v.findViewById(R.id.popup_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        EditText textView = v.findViewById(R.id.popup_content);
        textView.setText(text);

        return v;
    }
}