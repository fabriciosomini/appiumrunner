package appiumrunner.unesc.net.appiumrunner.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import appiumrunner.unesc.net.appiumrunner.R;
import appiumrunner.unesc.net.appiumrunner.helpers.EstadoUtil;
import appiumrunner.unesc.net.appiumrunner.states.Estado;


public class SearchActivity extends AppCompatActivity {

    private EditText searchEditTxt;
    private ListView listView;
    private boolean ignoreFocus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEditTxt = findViewById(R.id.searchEditTxt);
        listView = findViewById(android.R.id.list);

        setEventosInterface();
        registrarEstadoInicialTela();


    }


    private void registrarEstadoInicialTela() {
        EstadoUtil.encontrar(searchEditTxt)
                .focar(Estado.Foco.SEM_FOCO)
                .escrever(getString(R.string.hint_search))
                .verificar()
                .finalizar();
    }

    private void setEventosInterface() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        final ArrayList<String> items = getItemsLista();

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, R.layout.simple_checkbox_item,
                R.id.simple_checkbox_item_text, items);

        listView.setAdapter(stringArrayAdapter);

        searchEditTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus && !ignoreFocus) {
                    String text = searchEditTxt.getText().toString();
                    EstadoUtil.encontrar(searchEditTxt)
                            .focar(Estado.Foco.FOCADO)
                            .escrever(text)
                            .verificar()
                            .finalizar();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EstadoUtil.terminarTeste();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ignoreFocus = false;
    }

    public ArrayList<String> getItemsLista() {
        ArrayList<String> itemsLista = new ArrayList<>();
        itemsLista.add("Adubo 15kg");
        itemsLista.add("Fertilizante 7kg");
        itemsLista.add("Sal 12kg");

        return itemsLista;
    }

}
