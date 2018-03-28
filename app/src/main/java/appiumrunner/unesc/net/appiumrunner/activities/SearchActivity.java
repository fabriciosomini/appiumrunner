package appiumrunner.unesc.net.appiumrunner.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import appiumrunner.unesc.net.appiumrunner.R;
import appiumrunner.unesc.net.appiumrunner.engine.Registrador;
import appiumrunner.unesc.net.appiumrunner.engine.Setup;
import appiumrunner.unesc.net.appiumrunner.helpers.UtilitarioEstados;
import appiumrunner.unesc.net.appiumrunner.states.Estado;


public class SearchActivity extends AppCompatActivity {

    private EditText searchEditTxt;
    private ListView listView;
    private Registrador registrador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Setup setup = new Setup();
        setup.setAppActivity(this.getClass().getName());
        setup.setDeviceName("adroid");
        setup.setPlatformVersion("7.1.2");
        setup.setUseDefaultTearDown(true);
        setup.setPackageName(getPackageName());
        setup.setAppiumServerAddress("http://127.0.0.1:4723/wd/hub");
        setup.setAppPath(".\\build\\outputs\\apk\\debug\\", "app-debug.apk");

        registrador = new Registrador(setup);

        searchEditTxt = findViewById(R.id.searchEditTxt);
        listView = findViewById(android.R.id.list);

        setEventosInterface();
        registrarEstadoInicialTela();


    }


    private void registrarEstadoInicialTela() {
        UtilitarioEstados.verificarEstadoCampoTexto(registrador,
                "searchEditTxt",
                Estado.Foco.SEM_FOCO, getString(R.string.hint_search)
        );
    }

    private void setEventosInterface() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        final ArrayList<String> items = getItemsLista();

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, R.layout.simple_checkbox_item,
                R.id.simple_checkbox_item_text, items);

        listView.setAdapter(stringArrayAdapter);

        searchEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

                String text = editable.toString();
                UtilitarioEstados.verificarEstadoCampoTexto(registrador, "searchEditTxt", Estado.Foco.FOCADO, text);

            }
        });



    }


    public ArrayList<String> getItemsLista() {
        ArrayList<String> itemsLista = new ArrayList<>();
        itemsLista.add("Adubo 15kg");
        itemsLista.add("Fertilizante 7kg");
        itemsLista.add("Sal 12kg");

        return itemsLista;
    }

}
