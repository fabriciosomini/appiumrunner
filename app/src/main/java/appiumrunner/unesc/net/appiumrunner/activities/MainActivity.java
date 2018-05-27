package appiumrunner.unesc.net.appiumrunner.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import appiumrunner.unesc.net.appiumrunner.R;
import appiumrunner.unesc.net.appiumrunner.adapters.MotoristaAdapter;
import appiumrunner.unesc.net.appiumrunner.models.Motorista;
import appiumrunner.unesc.net.appiumrunner.models.Repository;
import unesc.com.unesctcc3.modelos.Atividade;
import unesc.com.unesctcc3.motor.AlgoritmoRegistro;
import unesc.com.unesctcc3.motor.Setup;
import unesc.com.unesctcc3.utilitarios.EstadoDispositivoUtilitario;
import unesc.com.unesctcc3.utilitarios.GeradorTestes;

public class MainActivity extends AppCompatActivity {
    private EditText searchEditTxt;
    private Button adicionarMotorista;
    private ListView listView;
    private boolean ignoreFocus;
    private AlgoritmoRegistro algoritmoRegistro;
    private MotoristaAdapter motoristaAdapter;
    private TextView emptyView;
    private TextView nomeEmpresa;
    private ConstraintLayout listContainer;
    private ImageButton copyTestBtn;
    private TextView emptyText;
    private boolean noItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Setup setup = new Setup();
        setup.setDeviceName("adroid");
        setup.setPlatformVersion(Build.VERSION.RELEASE);
        setup.setAppiumServerAddress("http://127.0.0.1:4723/wd/hub");
        setup.setAppPath(".\\build\\outputs\\apk\\debug\\", "app-debug.apk");

        algoritmoRegistro = new AlgoritmoRegistro(this, setup);

        GeradorTestes.init(algoritmoRegistro);

        setEventosInterface();
    }

    private void setEventosInterface() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        searchEditTxt = findViewById(R.id.searchEditTxt);
        emptyText = findViewById(R.id.empty_text);
        listView = findViewById(R.id.list);
        adicionarMotorista = findViewById(R.id.add_driver_btn);
        nomeEmpresa = findViewById(R.id.nome_empresa);
        listContainer = findViewById(R.id.list_container);
        copyTestBtn = findViewById(R.id.copy_test_btn);
        adicionarMotorista.setFocusable(true);
        adicionarMotorista.setFocusableInTouchMode(true);
        copyTestBtn.setFocusable(true);
        copyTestBtn.setFocusableInTouchMode(true);
        copyTestBtn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    showTest();
                }
            }
        });

        motoristaAdapter = new MotoristaAdapter(this, android.R.layout.simple_list_item_1);
        Repository.setAdapter(motoristaAdapter);
        listView.setAdapter(motoristaAdapter);
        nomeEmpresa.setText(getString(R.string.company_name));

        searchEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(false, charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        searchEditTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                String text = searchEditTxt.getText().toString();

                if (hasFocus && !ignoreFocus) {
                    if (!text.isEmpty()) {
                        GeradorTestes.gerarTesteElemento(searchEditTxt)
                                .limparValor()
                                .reproduzirAcoes();
                    }
                }
                if (!hasFocus && !ignoreFocus) {

                    if (text.isEmpty()) {
                        GeradorTestes.gerarTesteElemento(searchEditTxt)
                                .focarCampo()
                                .limparValor()
                                .verificarValores();
                    } else {
                        GeradorTestes.gerarTesteElemento(searchEditTxt)
                                .focarCampo()
                                .escreverValor(text)
                                .reproduzirAcoes()
                                .verificarValores();
                    }


                    if (noItems) {
                        GeradorTestes.gerarTesteElemento(emptyText)
                                .visibilidade(Atividade.Visibilidade.VISIVEL)
                                .verificarValores();
                    } else {
                        GeradorTestes.gerarTesteElemento(emptyText)
                                .visibilidade(Atividade.Visibilidade.OCULTO)
                                .verificarValores();
                    }
                }
            }
        });

        adicionarMotorista.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
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
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Motorista motorista = (Motorista) view.getTag();
                Intent intent = new Intent(getBaseContext(), CadastroActivity.class);
                intent.putExtra("motorista", motorista);
                startActivity(intent);

                GeradorTestes.gerarTesteElemento(listView)
                        .escolherValor(motorista.getNome())
                        .reproduzirAcoes();
            }
        });
    }

    private void filter(final boolean isResume, CharSequence charSequence) {
        Filter filter = motoristaAdapter.getFilter();

        filter.filter(charSequence.toString(), new Filter.FilterListener() {
            @Override
            public void onFilterComplete(int count) {
                noItems = count == 0;
                showNoItemsText(noItems);

                if (isResume && !searchEditTxt.getText().toString().isEmpty()) {
                    if (noItems) {
                        GeradorTestes.gerarTesteElemento(emptyText)
                                .visibilidade(Atividade.Visibilidade.VISIVEL)
                                .verificarValores();
                    } else {
                        GeradorTestes.gerarTesteElemento(emptyText)
                                .visibilidade(Atividade.Visibilidade.OCULTO)
                                .verificarValores();
                    }
                }
            }
        });
    }

    private void showNoItemsText(boolean show) {
        if (show) {
            listContainer.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.VISIBLE);
        } else {
            listContainer.setVisibility(View.GONE);
            emptyText.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        GeradorTestes.pressionar(Atividade.Tecla.VOLTAR);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ignoreFocus = false;

        filter(true, searchEditTxt.getText());

    }

    private void showTest() {
        GeradorTestes.terminarTeste("TestePrincipal");
        String script = GeradorTestes.getTeste();
        EstadoDispositivoUtilitario.EstadoAparelhoMovel estadoAparelhoMovel = GeradorTestes.getEstadoAparelhoMovel();
        Log.d("Teste Automatizado: \n", script);
        //TODO: Adicionar dialog para mostrar o test output
    }

}
