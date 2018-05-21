package appiumrunner.unesc.net.appiumrunner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import appiumrunner.unesc.net.appiumrunner.R;
import appiumrunner.unesc.net.appiumrunner.adapters.MotoristaAdapter;
import appiumrunner.unesc.net.appiumrunner.engine.Registrador;
import appiumrunner.unesc.net.appiumrunner.engine.Setup;
import appiumrunner.unesc.net.appiumrunner.helpers.EstadoDispositivoUtil;
import appiumrunner.unesc.net.appiumrunner.helpers.GeradorTestes;
import appiumrunner.unesc.net.appiumrunner.models.Motorista;
import appiumrunner.unesc.net.appiumrunner.models.Repository;
import appiumrunner.unesc.net.appiumrunner.states.Estado;

public class MainActivity extends AppCompatActivity {
    private EditText searchEditTxt;
    private Button adicionarMotorista;
    private ListView listView;
    private boolean ignoreFocus;
    private Registrador registrador;
    private MotoristaAdapter motoristaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Setup setup = new Setup();
        setup.setDeviceName("adroid");
        setup.setPlatformVersion("5.0");
        setup.setAppiumServerAddress("http://127.0.0.1:4723/wd/hub");
        setup.setAppPath(".\\build\\outputs\\apk\\debug\\", "app-debug.apk");

        registrador = new Registrador(this, setup);

        GeradorTestes.init(registrador);

        setEventosInterface();
        registrarEstadoInicialTela();
    }

    private void registrarEstadoInicialTela() {
        GeradorTestes.gerarTesteElemento(searchEditTxt)
                .escreverValor(getString(R.string.hint_search))
                .desfocarCampo()
                .verificarValores();
    }

    private void setEventosInterface() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        searchEditTxt = findViewById(R.id.searchEditTxt);
        listView = findViewById(R.id.list);
        adicionarMotorista = findViewById(R.id.abrirListaMercadorias);
        motoristaAdapter = new MotoristaAdapter(this, android.R.layout.simple_list_item_1, Repository.getMotoristaList());
        listView.setAdapter(motoristaAdapter);
        searchEditTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus && !ignoreFocus) {
                    String text = searchEditTxt.getText().toString();
                    GeradorTestes.gerarTesteElemento(searchEditTxt)
                            .focarCampo()
                            .escreverValor(text)
                            .reproduzirAcoes()
                            .verificarValores();
                }
            }
        });

        adicionarMotorista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Altera o foco para o botão, solucionando o problema de não disparar o evento onFocusChange
                ignoreFocus = true;
                adicionarMotorista.requestFocusFromTouch();
                GeradorTestes.gerarTesteElemento(adicionarMotorista)
                        .rolarAteCampo()
                        .clicarCampo()
                        .reproduzirAcoes();
                Intent intent = new Intent(getBaseContext(), CadastroActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Motorista motorista = (Motorista) view.getTag();
                Intent intent = new Intent(getBaseContext(), CadastroActivity.class);
                intent.putExtra("motorista", motorista);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        GeradorTestes.pressionar(Estado.Tecla.VOLTAR);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ignoreFocus = false;
        if (motoristaAdapter != null) {
            motoristaAdapter.notifyDataSetChanged();
        }

    }

    private void showTest() {
        GeradorTestes.terminarTeste("TestePrincipal");
        String script = GeradorTestes.getTeste();
        EstadoDispositivoUtil.EstadoAparelhoMovel estadoAparelhoMovel = GeradorTestes.getEstadoAparelhoMovel();
        Log.d("Teste Automatizado: \n", script);
    }

}
