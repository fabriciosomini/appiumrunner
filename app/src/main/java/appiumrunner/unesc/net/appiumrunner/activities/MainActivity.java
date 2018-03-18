package appiumrunner.unesc.net.appiumrunner.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import appiumrunner.unesc.net.appiumrunner.R;
import appiumrunner.unesc.net.appiumrunner.engine.ActivityRecorder;
import appiumrunner.unesc.net.appiumrunner.states.ApplicationState;
import appiumrunner.unesc.net.appiumrunner.states.ButtonState;
import appiumrunner.unesc.net.appiumrunner.states.EditTextState;
import appiumrunner.unesc.net.appiumrunner.states.ListViewState;
import appiumrunner.unesc.net.appiumrunner.states.TextViewState;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button openSearchListBtn = findViewById(R.id.openSearchListBtn);
        TextView userTxtView = findViewById(R.id.userTxtView);
        ListView listView = findViewById(R.id.resultsListView);
        String userName = userTxtView.getText().toString();


        //----------------------------------------------------------------
        ActivityRecorder activityRecorder = new ActivityRecorder();
        ApplicationState applicationState = new ApplicationState();
        TextViewState textViewState = new TextViewState();

        activityRecorder.createDBBackup("burger.db");

        applicationState.addInfo("userName", userName);

        //Ao abrir tela principal
        //-----------------------
        textViewState.setText((String) applicationState.getInfo("userName"));
        activityRecorder.createStateAssertion(textViewState);

        ButtonState buttonState = new ButtonState();
        buttonState.setElementId(R.id.openSearchListBtn);
        buttonState.setFocusedState(false);
        buttonState.setHint("ABRIR LISTA");
        activityRecorder.createStateAssertion(buttonState);

        //Ao clicar no botão de abrir a lista de pesquisa
        //-----------------------------------------------
        ArrayList<String> items = new ArrayList<>();
        int size = items.size();

        ListViewState listViewState = new ListViewState();
        listViewState.setElementId(R.id.resultsListView);
        listViewState.setVisibility(true);
        listViewState.setCount(size); //O engenheiro de testes deve avaliar se a lista realmente deveria ter esta quantidade de registros

        activityRecorder.createWaitForState(listViewState);


        //Ao abrir a tela de pesquisa
        //---------------------------
        EditTextState editTextState = new EditTextState();

        editTextState.setElementId(R.id.searchBox);
        editTextState.setFocusedState(false);
        editTextState.setTextState(null);
        editTextState.setHintState("Pesquisar");
        activityRecorder.createStateAssertion(editTextState);

        //Ao digitar algo na caixa de pesquisa
        //------------------------------------
        editTextState.setElementId(R.id.searchBox);
        editTextState.setFocusedState(true);
        editTextState.setTextState("Salada");
        editTextState.setHintState(null);
        activityRecorder.createStateAssertion(editTextState);


        size = items.size();
        listViewState.setElementId(R.id.resultsListView);
        listViewState.setVisibility(true);
        listViewState.setCount(size); //O engenheiro de testes deve avaliar se a lista realmente deveria ter esta quantidade de registros

        activityRecorder.createStateAssertion(listViewState);


    }
}
