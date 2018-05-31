package appiumrunner.unesc.net.appiumrunner.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

public class LeveragedSpinner extends android.support.v7.widget.AppCompatSpinner {

    private OnSpinnerEventsListener mListener;
    private boolean mOpenInitiated = false;


    public LeveragedSpinner(Context context) {
        super(context);
    }

    public LeveragedSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean performClick() {

        mOpenInitiated = true;
        if (mListener != null) {
            mListener.onSpinnerExpanded(this);
        }
        return super.performClick();
    }

    public void setSpinnerEventsListener(
            OnSpinnerEventsListener onSpinnerEventsListener) {
        mListener = onSpinnerEventsListener;
    }

    public void performClosedEvent() {
        mOpenInitiated = false;
        if (mListener != null) {
            mListener.onSpinnerCollapsed(this);
        }
    }

    public boolean hasBeenExpanded() {
        return mOpenInitiated;
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasBeenExpanded() && hasFocus) {
            performClosedEvent();
        }
    }

    public interface OnSpinnerEventsListener {

        void onSpinnerExpanded(Spinner spinner);

        void onSpinnerCollapsed(Spinner spinner);

    }

}