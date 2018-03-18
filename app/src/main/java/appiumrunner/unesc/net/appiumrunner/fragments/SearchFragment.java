package appiumrunner.unesc.net.appiumrunner.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentActivity = getActivity();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EditText searchEditTxt = currentActivity.findViewById(R.id.searchEditTxt);
        ListView listView = currentActivity.findViewById(android.R.id.list);
        //Ao abrir a tela de pesquisa
        //---------------------------
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

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Ao digitar algo na caixa de pesquisa
                //------------------------------------
                editTextState.setElementId(R.id.searchEditTxt)
                        .setFocusedState(true)
                        .setTextState("Salada")
                        .setHintState(null)
                        .record();
            }
        });


        ArrayList<String> items = new ArrayList<>();
        int size = items.size();
        ListViewState listViewState = new ListViewState();
        listViewState.setElementId(R.id.resultsListView)
                .setVisibility(true)
                .setCount(size)
                .record();
    }
}
