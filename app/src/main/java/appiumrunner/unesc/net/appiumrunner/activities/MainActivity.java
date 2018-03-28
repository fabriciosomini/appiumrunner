package appiumrunner.unesc.net.appiumrunner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import appiumrunner.unesc.net.appiumrunner.helpers.UtilitarioEstados;
import appiumrunner.unesc.net.appiumrunner.states.Estado;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Setup setup = new Setup();
        setup.setAppActivity(this.getClass().getName());
        setup.setDeviceName("adroid");
        setup.setPlatformVersion("7.1.2");
        setup.setUseDefaultTearDown(true);
        setup.setPackageName(getPackageName());
        setup.setAppiumServerAddress("http://127.0.0.1:4723/wd/hub");
        setup.setAppPath(".\\build\\outputs\\apk\\debug\\", "app-debug.apk");

        registrador = new Registrador(setup);

        setEventosInterface();
        registrarEstadoInicialTela();

    }

    private void registrarEstadoInicialTela() {
        UtilitarioEstados.verificarEstadoCampoTexto(registrador, "nome_motorista", Estado.Foco.FOCADO, null);
        UtilitarioEstados.verificarEstadoCampoTexto(registrador, "driver_cpf", Estado.Foco.SEM_FOCO, null);
        UtilitarioEstados.verificarEstadoCampoSelecao(registrador, "driver_state", Estado.Foco.IGNORAR, getEstados().get(0));
    }


    private void setEventosInterface() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        nomeEmpresa = findViewById(R.id.nome_empresa);
        nomeMotorista = findViewById(R.id.nome_motorista);
        cpfMotorista = findViewById(R.id.cpf_motorista);
        estadoMotorista = findViewById(R.id.estado_motorista);
        volumeCarga = findViewById(R.id.volume_carga);
        tipoCarga = findViewById(R.id.tipo_carga);
        motoristaAtivo = findViewById(R.id.motorista_ativo);
        abrirListaMercadorias = findViewById(R.id.abrir_lista_mercadorias);

        nomeEmpresa.setText(getString(R.string.company_name));
        estadoMotorista.setAdapter(getEstadoAdapter());

        nomeMotorista.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                if (hasFocus) {
                    String text = nomeMotorista.getText().toString();
                    UtilitarioEstados.reproduzirEstadoCampoTexto(registrador, "nome_motorista", Estado.Foco.FOCADO, text);
                    UtilitarioEstados.verificarEstadoCampoTexto(registrador, "nome_motorista", Estado.Foco.FOCADO, text);

                } else {
                    String text = nomeMotorista.getText().toString();
                    UtilitarioEstados.reproduzirEstadoCampoTexto(registrador, "nome_motorista", Estado.Foco.SEM_FOCO, text);
                    UtilitarioEstados.verificarEstadoCampoTexto(registrador, "nome_motorista", Estado.Foco.SEM_FOCO, text);

                }
            }
        });

        cpfMotorista.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                if (hasFocus) {
                    String text = nomeMotorista.getText().toString();
                    UtilitarioEstados.reproduzirEstadoCampoTexto(registrador, "cpf_motorista", Estado.Foco.FOCADO, text);
                    UtilitarioEstados.verificarEstadoCampoTexto(registrador, "cpf_motorista", Estado.Foco.FOCADO, text);

                } else {
                    String text = nomeMotorista.getText().toString();
                    UtilitarioEstados.reproduzirEstadoCampoTexto(registrador, "cpf_motorista", Estado.Foco.SEM_FOCO, text);
                    UtilitarioEstados.verificarEstadoCampoTexto(registrador, "cpf_motorista", Estado.Foco.SEM_FOCO, text);

                }
            }
        });

        estadoMotorista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (++estadoMotoristaSelected > 1) {
                    final String newValue = (String) estadoMotorista.getItemAtPosition(i);
                    UtilitarioEstados.reproduzirEstadoCampoSelecao(registrador, "estado_motorista", newValue);
                    UtilitarioEstados.verificarEstadoCampoSelecao(registrador, "estado_motorista", Estado.Foco.FOCADO, newValue);
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
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                UtilitarioEstados.reproduzirEstadoCampoBarraProgresso(registrador, "volume_carga", pos);
                UtilitarioEstados.verificarEstadoCampoBarraProgresso(registrador, "volume_carga", Estado.Foco.FOCADO, pos);

            }
        });


        tipoCarga.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                UtilitarioEstados.reproduzirEstadoCampoSelecaoUnica(registrador, "tipo_carga", i);
                UtilitarioEstados.verificarEstadoCampoSelecaoUnica(registrador, "tipo_carga", Estado.Foco.FOCADO, i);

            }
        });

        motoristaAtivo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                UtilitarioEstados.reproduzirEstadoCampoToggle(registrador, "motorista_ativo", true);
                UtilitarioEstados.verificarEstadoCampoToggle(registrador, "motorista_ativo", Estado.Foco.FOCADO, true);
            }
        });


        abrirListaMercadorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Altera o foco para o botão, solucionando o problema de não disparar o evento onFocusChange
                abrirListaMercadorias.requestFocusFromTouch();

                UtilitarioEstados.reproduzirEstadoCampoBotao(registrador, "abrir_lista_mercadorias");
                registrador.parar();

                Intent i = new Intent(getBaseContext(), SearchActivity.class);
                startActivity(i);

            }
        });

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
