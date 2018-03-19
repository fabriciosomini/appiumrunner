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
import appiumrunner.unesc.net.appiumrunner.states.EditTextState;
import appiumrunner.unesc.net.appiumrunner.states.ListViewState;

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


        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,
                items);
        listView.setAdapter(stringArrayAdapter);

        final EditTextState editTextState = new EditTextState();
        editTextState.setElementId(R.id.searchEditTxt)
                .setFocusedState(false)
                .setTextState(null)
                .setHintState("Pesquisar")
                .record();

        searchEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String text = charSequence.toString();
                editTextState.setElementId(R.id.searchEditTxt)
                        .setFocusedState(true)
                        .setTextState(text)
                        .setHintState(null)
                        .record();

                int size = items.size();

                ListViewState listViewState = new ListViewState();
                listViewState.setElementId(android.R.id.list)
                        .setVisibility(true)
                        .setCount(size)
                        .record();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

}
