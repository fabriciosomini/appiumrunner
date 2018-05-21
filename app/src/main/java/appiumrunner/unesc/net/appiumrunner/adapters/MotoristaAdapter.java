package appiumrunner.unesc.net.appiumrunner.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import appiumrunner.unesc.net.appiumrunner.models.Motorista;

public class MotoristaAdapter extends ArrayAdapter<Motorista> {

    public MotoristaAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public MotoristaAdapter(Context context, int resource, List<Motorista> items) {
        super(context, resource, items);

    }

    @Override
    public View getView(int position, View convertView,
                        ViewGroup parent) {
        AppCompatTextView v = (AppCompatTextView) convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = (AppCompatTextView) vi.inflate(android.R.layout.simple_list_item_1, null);
        }
        Motorista p = getItem(position);
        v.setText(p.getNome());
        v.setTag(p);
        return v;
    }
}