package appiumrunner.unesc.net.appiumrunner.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import appiumrunner.unesc.net.appiumrunner.R;
import appiumrunner.unesc.net.appiumrunner.adapters.MotoristaAdapter;
import appiumrunner.unesc.net.appiumrunner.components.MessageToast;
import appiumrunner.unesc.net.appiumrunner.models.Motorista;
import appiumrunner.unesc.net.appiumrunner.models.Repository;
import unesc.com.unesctcc3.modelos.Atividade;
import unesc.com.unesctcc3.modelos.Preferencias;
import unesc.com.unesctcc3.modelos.Setup;
import unesc.com.unesctcc3.modelos.Teste;
import unesc.com.unesctcc3.motor.GeradorCasosTeste;
import unesc.com.unesctcc3.motor.RegistroAtividades;
import unesc.com.unesctcc3.utilitarios.ArquivoUtilitario;
import unesc.com.unesctcc3.utilitarios.EstadoDispositivoUtilitario;

public class MainActivity extends AppCompatActivity {
    private static final int WRITE_FILE_REQUEST_CODE = 1;
    private EditText searchEditTxt;
    private Button adicionarMotorista;
    private ListView listView;
    private boolean ignoreFocus;
    private MotoristaAdapter motoristaAdapter;
    private TextView emptyView;
    private TextView nomeEmpresa;
    private ConstraintLayout listContainer;
    private ImageButton copyTestBtn;
    private TextView emptyText;
    private boolean noItems;
    private String casoTeste;
    private String documentacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Setup setup = new Setup();
        setup.setDeviceName("adroid");
        setup.setPlatformVersion(Build.VERSION.RELEASE);
        setup.setAppiumServerAddress("http://127.0.0.1:4723/wd/hub");
        setup.setAppPath(".\\build\\outputs\\apk\\debug\\", "app-debug.apk");

        Preferencias preferencias = new Preferencias();

        RegistroAtividades.inicializar();
        GeradorCasosTeste.inicializar(setup, preferencias);

        setEventosInterface();

        carregarDadosEmMemoria();
    }

    private void carregarDadosEmMemoria() {

        gerarRegistro("Fabricio Somini", "717.056.040-21",
                "Santa Catarina - SC", 25,
                Motorista.TipoCarga.ALIMENTICIA, true, true);

        gerarRegistro("Franciso Todari", "465.620.900-75",
                "Paraná - PR", 7,
                Motorista.TipoCarga.ALIMENTICIA, true, false);


        gerarRegistro("Igor Silva", "059.967.430-00",
                "Rio de Janeiro - RJ", 88,
                Motorista.TipoCarga.PERIGOSA, true, true);


        gerarRegistro("Jessica Gonçalves", "902.354.440-49",
                "Rio Grande do Sul - RS", 0,
                Motorista.TipoCarga.PERIGOSA, false, true);

        gerarRegistro("Sara Almeida", "954.092.470-78",
                "Mato Grosso - MT", 66,
                Motorista.TipoCarga.VIVA, true, true);


    }

    void gerarRegistro(String nomeMotoristaValor, String cpfMotoristaValor, String estadoOrigemValor,
                       int volumeCargaValor, Motorista.TipoCarga tipoCargaValor, boolean bitremValor, boolean ativo) {

        Motorista motorista = new Motorista();
        motorista.setNome(nomeMotoristaValor);
        motorista.setCpf(cpfMotoristaValor);
        motorista.setEstado(estadoOrigemValor);
        motorista.setVolumeCarga(volumeCargaValor);
        motorista.setTipoCarga(tipoCargaValor);
        motorista.setBitrem(bitremValor);
        motorista.setAtivo(ativo);

        Random randomno = new Random();
        long value = randomno.nextLong();

        if (motorista.getCodigo() == 0) {
            motorista.setCodigo(value);
        }

        Repository.addOrUptate(motorista);
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
        copyTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyTestBtn.requestFocusFromTouch();
                showTest();
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

        searchEditTxt.setFocusable(true);
        searchEditTxt.setFocusableInTouchMode(true);
        searchEditTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                String text = searchEditTxt.getText().toString();

                if (hasFocus) {
                    if (!text.isEmpty()) {
                        RegistroAtividades.vincularElemento(searchEditTxt)
                                .limparValor()
                                .reproduzirAcoes();
                    }
                }
                if (!hasFocus) {

                    if (text.isEmpty()) {
                        RegistroAtividades.vincularElemento(searchEditTxt)
                                .focarCampo()
                                .lerValor("Pesquisar")
                                .verificarValores();
                    } else {
                        RegistroAtividades.vincularElemento(searchEditTxt)
                                .focarCampo()
                                .escreverValor(text)
                                .reproduzirAcoes()
                                .verificarValores();
                    }


                    if (noItems) {
                        RegistroAtividades.vincularElemento(emptyText)
                                .visibilidade(Atividade.Visibilidade.VISIVEL)
                                .lerValor(getString(R.string.nenhum_item))
                                .verificarValores();
                    } else {
                        RegistroAtividades.vincularElemento(emptyText)
                                .visibilidade(Atividade.Visibilidade.OCULTO)
                                .verificarValores();
                    }
                }
            }
        });

        adicionarMotorista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Altera o foco para o botão, solucionando o problema de não disparar o evento onFocusChange
                ignoreFocus = true;
                adicionarMotorista.requestFocusFromTouch();
                RegistroAtividades.vincularElemento(adicionarMotorista)
                        .rolarAteCampo()
                        .clicarBotao()
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

                RegistroAtividades.vincularElemento(listView)
                        .selecionarItemListagem(motorista.getNome())
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
                        RegistroAtividades.vincularElemento(emptyText)
                                .visibilidade(Atividade.Visibilidade.VISIVEL)
                                .lerValor(getString(R.string.nenhum_item))
                                .verificarValores();
                    } else {
                        RegistroAtividades.vincularElemento(emptyText)
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
        RegistroAtividades.pressionar(Atividade.Tecla.VOLTAR);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ignoreFocus = false;

        RegistroAtividades.registrarAcessoTela("Listagem de Motoristas");

        filter(true, searchEditTxt.getText());

    }

    private void showTest() {

        ArrayList<Atividade> listaAtividades = RegistroAtividades.getListaAtividades();
        Teste teste = GeradorCasosTeste.gerar(listaAtividades, "CasoTesteAutomatizado");
        casoTeste = teste.getCasoTeste();
        documentacao = teste.getDocumentacao();
        EstadoDispositivoUtilitario.EstadoAparelhoMovel estadoAparelhoMovel =
                EstadoDispositivoUtilitario.getEstadoAparelhoMovel(this);

        if (isStoragePermissionGranted()) {
            escreverTestes(casoTeste, documentacao);
        }

        Log.d("Teste Automatizado: \n", casoTeste);

        RegistroAtividades.inicializar();

    }

    private void escreverTestes(String casoTeste, String documentacao) {
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File docFile = new File(folder, "DocumentacaoTeste.txt");
        ArquivoUtilitario.writeToFile(docFile, documentacao, this);

        File testeFile = new File(folder, "CasoTesteAutomatizado.java");
        ArquivoUtilitario.writeToFile(testeFile, casoTeste, this);

        MessageToast.show(MainActivity.this, "Teste Finalizado!");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (requestCode == WRITE_FILE_REQUEST_CODE) {
                    escreverTestes(casoTeste, documentacao);
                }
            }
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {

                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_FILE_REQUEST_CODE);
                return false;
            }
        } else {
            return true;
        }
    }

}
