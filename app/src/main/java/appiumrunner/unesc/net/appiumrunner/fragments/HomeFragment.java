package appiumrunner.unesc.net.appiumrunner.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import appiumrunner.unesc.net.appiumrunner.R;
import appiumrunner.unesc.net.appiumrunner.engine.Recorder;
import appiumrunner.unesc.net.appiumrunner.states.ApplicationState;
import appiumrunner.unesc.net.appiumrunner.states.ButtonState;
import appiumrunner.unesc.net.appiumrunner.states.TextViewState;
import appiumrunner.unesc.net.appiumrunner.states.ViewState;

public class HomeFragment extends Fragment {
    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentManager = getActivity().getSupportFragmentManager();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button openSearchListBtn = view.findViewById(R.id.openSearchListBtn);
        TextView userTxtView = view.findViewById(R.id.userTxtView);

        String userName = userTxtView.getText().toString();
        final Recorder recorder = new Recorder();

        // recorder.createDB("burger.db");

        openSearchListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchFragment searchFragment = new SearchFragment();

                fragmentManager.beginTransaction().replace(R.id.container_layout,
                        searchFragment,
                        searchFragment.getTag())
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .commit();

                ViewState viewState = new ViewState();
                viewState.setElementId(R.id.search_fragment)
                        .setVisibility(true)
                        .record();

            }
        });

        //----------------------------------------------------------------

        ApplicationState applicationState = new ApplicationState();
        TextViewState textViewState = new TextViewState();

        applicationState.addInfo("userName", userName);

        textViewState.setText((String) applicationState.getInfo("userName"));

        ButtonState buttonState = new ButtonState();
        buttonState.setElementId(R.id.openSearchListBtn)
                .setFocusedState(false)
                .setHint("ABRIR LISTA")
                .record();


        return view;
    }

}
