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
import appiumrunner.unesc.net.appiumrunner.engine.Registro;
import appiumrunner.unesc.net.appiumrunner.states.Estado;

public class SearchActivity extends AppCompatActivity {

    private EditText searchEditTxt;
    private ListView listView;
    private Registro registro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEditTxt = findViewById(R.id.searchEditTxt);
        listView = findViewById(android.R.id.list);
        registro = new Registro();

        setEventosInterface();
        registrarEstadoCampoPesquisaVazio();


    }

    private void setEventosInterface() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

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
                registrarEstadoCampoPesquisaPreenchido(text);

            }
        });


        final ArrayList<String> items = getItemsLista();

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, R.layout.simple_checkbox_item,
                R.id.simple_checkbox_item_text, items);

        listView.setAdapter(stringArrayAdapter);
    }

    private void registrarEstadoCampoPesquisaPreenchido(String text) {
        final Estado editTextEstado = new Estado(registro);
        editTextEstado.setIdentificadorElemento("searchEditTxt")
                .setEstadoFoco(true)
                .setEstadoTexto(text)
                .setEstadoDica(null)
                .build();
    }

    private void registrarEstadoCampoPesquisaVazio() {
        final Estado editTextEstado = new Estado(registro);
        editTextEstado.setIdentificadorElemento("searchEditTxt")
                .setEstadoFoco(false)
                .setEstadoTexto(null)
                .setEstadoDica("Pesquisar")
                .build();
    }


    public ArrayList<String> getItemsLista() {
        ArrayList<String> itemsLista = new ArrayList<>();
        itemsLista.add("Adubo 15kg");
        itemsLista.add("Fertilizante 7kg");
        itemsLista.add("Sal 12kg");

        return itemsLista;
    }
}
