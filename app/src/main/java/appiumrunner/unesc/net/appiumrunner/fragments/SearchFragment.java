package appiumrunner.unesc.net.appiumrunner.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import appiumrunner.unesc.net.appiumrunner.R;
import appiumrunner.unesc.net.appiumrunner.engine.Registro;
import appiumrunner.unesc.net.appiumrunner.states.Estado;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    private Activity currentActivity;

    public SearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentActivity = getActivity();
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        EditText searchEditTxt = view.findViewById(R.id.searchEditTxt);
        ListView listView = view.findViewById(android.R.id.list);
        final ArrayList<String> items = new ArrayList<>();
        final Registro registro = new Registro();

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,
                items);
        listView.setAdapter(stringArrayAdapter);

        final Estado editTextEstado = new Estado(registro);
        editTextEstado.setElementId(R.id.searchEditTxt)
                .setFocusedState(false)
                .setTextState(null)
                .setHintState("Pesquisar")
                .commit();

        searchEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String text = charSequence.toString();
                editTextEstado.setElementId(R.id.searchEditTxt)
                        .setFocusedState(true)
                        .setTextState(text)
                        .setHintState(null)
                        .commit();

                int size = items.size();

                Estado listViewEstado = new Estado(registro);
                listViewEstado.setElementId(android.R.id.list)
                        .setCount(size)
                        .commit();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

}
