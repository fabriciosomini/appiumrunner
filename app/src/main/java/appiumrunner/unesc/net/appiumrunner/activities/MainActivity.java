package appiumrunner.unesc.net.appiumrunner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import appiumrunner.unesc.net.appiumrunner.R;
import appiumrunner.unesc.net.appiumrunner.engine.Registrador;
import appiumrunner.unesc.net.appiumrunner.engine.Setup;
import appiumrunner.unesc.net.appiumrunner.helpers.GeradorTestes;


public class MainActivity extends AppCompatActivity {

    private Registrador registrador;
    private TextView nomeEmpresa;
    private Button abrirListaMercadorias;
    private EditText nomeMotorista;
    private Spinner estadoMotorista;
    private EditText cpfMotorista;
    private SeekBar volumeCarga;
    private RadioGroup tipoCarga;
    private ToggleButton motoristaAtivo;
    private int estadoMotoristaSelected = 0;
    private boolean ignoreFocus;
    private TextView volumeCargaText;
    private CheckBox bitrem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Setup setup = new Setup();
        setup.setDeviceName("adroid");
        setup.setPlatformVersion("5.0");
        setup.setUseDefaultTearDown(true);
        setup.setPackageName(getPackageName());
        setup.setAppiumServerAddress("http://127.0.0.1:4723/wd/hub");
        setup.setAppPath(".\\build\\outputs\\apk\\debug\\", "app-debug.apk");

        registrador = new Registrador(setup);
        GeradorTestes.init(registrador);

        setEventosInterface();
        registrarEstadoInicialTela();

    }

    private void registrarEstadoInicialTela() {

        GeradorTestes.iniciarTesteElemento(nomeMotorista)
                .desfocarCampo()
                .limparValor()
                .verificarValores();

        GeradorTestes.iniciarTesteElemento(cpfMotorista)
                .desfocarCampo()
                .limparValor()
                .verificarValores();

        GeradorTestes.iniciarTesteElemento(estadoMotorista)
                .desfocarCampo()
                .escreverValor("")
                .verificarValores();

    }


    private void setEventosInterface() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        nomeEmpresa = findViewById(R.id.nome_empresa);
        nomeMotorista = findViewById(R.id.nomeMotorista);
        cpfMotorista = findViewById(R.id.cpfMotorista);
        estadoMotorista = findViewById(R.id.estadoMotorista);
        volumeCarga = findViewById(R.id.volumeCarga);
        volumeCargaText = findViewById(R.id.volumeCargaText);
        tipoCarga = findViewById(R.id.tipoCarga);
        motoristaAtivo = findViewById(R.id.motoristaAtivo);
        bitrem = findViewById(R.id.bitrem);
        abrirListaMercadorias = findViewById(R.id.abrirListaMercadorias);

        nomeEmpresa.setText(getString(R.string.company_name));
        estadoMotorista.setAdapter(getEstadoAdapter());

        nomeMotorista.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                String text = nomeMotorista.getText().toString();
                if (hasFocus && !ignoreFocus) {
                    if (!text.isEmpty()) {
                        GeradorTestes.iniciarTesteElemento(nomeMotorista)
                                .limparValor()
                                .reproduzirAcoes();
                    }
                }

                if (!hasFocus && !ignoreFocus) {

                    GeradorTestes.iniciarTesteElemento(nomeMotorista)
                            .focarElemento()
                            .escreverValor(text)
                            .reproduzirAcoes()
                            .verificarValores();

                }
            }
        });

        cpfMotorista.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                String text = cpfMotorista.getText().toString();
                if (hasFocus && !ignoreFocus) {
                    if (!text.isEmpty()) {
                        GeradorTestes.iniciarTesteElemento(cpfMotorista)
                                .limparValor()
                                .reproduzirAcoes();
                    }
                }

                if (!hasFocus && !ignoreFocus) {

                    GeradorTestes.iniciarTesteElemento(cpfMotorista)
                            .focarElemento()
                            .escreverValor(text)
                            .reproduzirAcoes()
                            .verificarValores();

                }
            }
        });

        estadoMotorista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (++estadoMotoristaSelected > 1) {
                    final String newValue = (String) estadoMotorista.getItemAtPosition(i);
                    GeradorTestes.iniciarTesteElemento(estadoMotorista)
                            .selecionarValor(newValue)
                            .reproduzirAcoes()
                            .verificarValores();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        volumeCarga.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int pos = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                pos = i;
                volumeCargaText.setText(pos + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                GeradorTestes.iniciarTesteElemento(volumeCarga)
                        .deslizarBarraProgresso(pos)
                        .verificarValores()
                        .reproduzirAcoes();
            }
        });


        tipoCarga.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                GeradorTestes.iniciarTesteElemento(radioButton)
                        .marcarOpcao()
                        .reproduzirAcoes()
                        .verificarValores();

            }
        });

        motoristaAtivo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                GeradorTestes.iniciarTesteElemento(motoristaAtivo)
                        .marcarOpcaoDesmarcavel(b)
                        .reproduzirAcoes()
                        .verificarValores();

            }
        });

        bitrem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                GeradorTestes.iniciarTesteElemento(bitrem)
                        .marcarOpcaoDesmarcavel(b)
                        .reproduzirAcoes()
                        .verificarValores();
            }
        });

        abrirListaMercadorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Altera o foco para o botão, solucionando o problema de não disparar o evento onFocusChange
                ignoreFocus = true;
                abrirListaMercadorias.requestFocusFromTouch();

                GeradorTestes.iniciarTesteElemento(abrirListaMercadorias)
                        .rolarAteElemento()
                        .clicarElemento()
                        .reproduzirAcoes();

                Intent i = new Intent(getBaseContext(), SearchActivity.class);
                startActivity(i);

            }
        });

        volumeCarga.setProgress(75);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ignoreFocus = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GeradorTestes.terminarTeste();
        String script = GeradorTestes.getTeste();
        Log.d("Teste Automatizado", script);
    }

    public ArrayList<String> getEstados() {
        ArrayList<String> estados = new ArrayList<>();
        estados.add("Acre - AC");
        estados.add("Alagoas - AL");
        estados.add("Amapá - AP");
        estados.add("Amazonas - AM");
        estados.add("Bahia - BA");
        estados.add("Ceará - CE");
        estados.add("Distrito Federal - DF");
        estados.add("Espírito Santo - ES");
        estados.add("Goiás - GO");
        estados.add("Maranhão - MA");
        estados.add("Mato Grosso - MT");
        estados.add("Mato Grosso do Sul - MS");
        estados.add("Minas Gerais - MG");
        estados.add("Pará - PA");
        estados.add("Paraíba - PB");
        estados.add("Paraná - PR");
        estados.add("Pernambuco - PE");
        estados.add("Piauí - PI");
        estados.add("Rio de Janeiro - RJ");
        estados.add("Rio Grande do Norte - RN");
        estados.add("Rio Grande do Sul - RS");
        estados.add("Rondônia - RO");
        estados.add("Roraima - RR");
        estados.add("Santa Catarina - SC");
        estados.add("São Paulo - SP");
        estados.add("Sergipe - SE");
        estados.add("Tocantins - TO");
        return estados;
    }

    public SpinnerAdapter getEstadoAdapter() {

        ArrayList<String> estados = getEstados();
        SpinnerAdapter estadoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, estados);
        return estadoAdapter;
    }
}
