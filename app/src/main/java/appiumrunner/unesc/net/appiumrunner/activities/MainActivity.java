package appiumrunner.unesc.net.appiumrunner.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import appiumrunner.unesc.net.appiumrunner.R;
import appiumrunner.unesc.net.appiumrunner.engine.Recorder;
import appiumrunner.unesc.net.appiumrunner.fragments.SearchFragment;
import appiumrunner.unesc.net.appiumrunner.states.ApplicationState;
import appiumrunner.unesc.net.appiumrunner.states.ButtonState;
import appiumrunner.unesc.net.appiumrunner.states.ListViewState;
import appiumrunner.unesc.net.appiumrunner.states.TextViewState;
import appiumrunner.unesc.net.appiumrunner.states.ViewState;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Recorder recorder = new Recorder();
        Button openSearchListBtn = findViewById(R.id.openSearchListBtn);
        TextView userTxtView = findViewById(R.id.userTxtView);
        ListView listView = findViewById(android.R.id.list);

        String userName = userTxtView.getText().toString();

        openSearchListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchFragment searchFragment = new SearchFragment();

                fragmentManager.beginTransaction().replace(R.id.container_layout,
                        searchFragment,
                        searchFragment.getTag())
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .commit();

                //Talvez tenha que mudar para um TransitionState
                ViewState viewState = new ViewState();
                viewState.setElementId(R.id.search_fragment)
                        .setVisibility(true)
                        .record();


            }
        });

        //----------------------------------------------------------------

        ApplicationState applicationState = new ApplicationState();
        TextViewState textViewState = new TextViewState();

        recorder.createDBBackup("burger.db");

        applicationState.addInfo("userName", userName);

        //Ao abrir tela principal
        //-----------------------
        textViewState.setText((String) applicationState.getInfo("userName"));
        recorder.createStateAssertion(textViewState);

        ButtonState buttonState = new ButtonState();
        buttonState.setElementId(R.id.openSearchListBtn)
                .setFocusedState(false)
                .setHint("ABRIR LISTA")
                .record();

        //Ao clicar no botão de abrir a lista de pesquisa
        //-----------------------------------------------
        ArrayList<String> items = new ArrayList<>();
        int size = items.size();

        ListViewState listViewState = new ListViewState();
        listViewState.setElementId(android.R.id.list)
                .setVisibility(true)
                .setCount(size)
                .record();


    }
}
