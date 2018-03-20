package appiumrunner.unesc.net.appiumrunner.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import appiumrunner.unesc.net.appiumrunner.R;
import appiumrunner.unesc.net.appiumrunner.engine.Registro;
import appiumrunner.unesc.net.appiumrunner.states.Estado;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        EditText searchEditTxt = findViewById(R.id.searchEditTxt);
        ListView listView = findViewById(android.R.id.list);
        final ArrayList<String> items = new ArrayList<>();
        final Registro registro = new Registro();

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                items);
        listView.setAdapter(stringArrayAdapter);

        final Estado editTextEstado = new Estado(registro);
        editTextEstado.setIdentificadorElemento("searchEditTxt")
                .setEstadoFoco(false)
                .setEstadoTexto(null)
                .setEstadoDica("Pesquisar")
                .build();

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
                editTextEstado.setIdentificadorElemento("searchEditTxt")
                        .setEstadoFoco(true)
                        .setEstadoTexto(text)
                        .setEstadoDica(null)
                        .build();

            }
        });
    }
}
