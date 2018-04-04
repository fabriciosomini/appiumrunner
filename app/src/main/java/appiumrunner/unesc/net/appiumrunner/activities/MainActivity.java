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
import appiumrunner.unesc.net.appiumrunner.helpers.EstadoUtil;
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
        //setup.setAppActivity(this.getClass().getName());
        setup.setDeviceName("adroid");
        setup.setPlatformVersion("4.4.4");
        setup.setUseDefaultTearDown(true);
        setup.setPackageName(getPackageName());
        setup.setAppiumServerAddress("http://127.0.0.1:4723/wd/hub");
        setup.setAppPath(".\\finalizar\\outputs\\apk\\debug\\", "app-debug.apk");

        registrador = new Registrador(setup);

        setEventosInterface();
        registrarEstadoInicialTela();

    }

    private void registrarEstadoInicialTela() {

        EstadoUtil.adicionarTeste(nomeMotorista)
                .setEstadoFoco(Estado.Foco.SEM_FOCO)
                .setEstadoTexto(null)
                .reproduzir()
                .finalizar();

        EstadoUtil.adicionarTeste(cpfMotorista)
                .setEstadoFoco(Estado.Foco.SEM_FOCO)
                .setEstadoTexto(null)
                .reproduzir()
                .finalizar();

        EstadoUtil.adicionarTeste(estadoMotorista)
                .setEstadoFoco(Estado.Foco.SEM_FOCO)
                .setEstadoTexto(null)
                .reproduzir()
                .finalizar();
    }


    private void setEventosInterface() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        nomeEmpresa = findViewById(R.id.nome_empresa);
        nomeMotorista = findViewById(R.id.nomeMotorista);
        cpfMotorista = findViewById(R.id.cpfMotorista);
        estadoMotorista = findViewById(R.id.estadoMotorista);
        volumeCarga = findViewById(R.id.volumeCarga);
        tipoCarga = findViewById(R.id.tipoCarga);
        motoristaAtivo = findViewById(R.id.motoristaAtivo);
        abrirListaMercadorias = findViewById(R.id.abrirListaMercadorias);

        nomeEmpresa.setText(getString(R.string.company_name));
        estadoMotorista.setAdapter(getEstadoAdapter());

        nomeMotorista.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                if (!hasFocus) {
                    String text = nomeMotorista.getText().toString();
                    EstadoUtil.adicionarTeste(nomeMotorista)
                            .setEstadoFoco(Estado.Foco.SEM_FOCO)
                            .setEstadoTexto(text)
                            .reproduzir()
                            .verificar()
                            .finalizar();



                }
            }
        });

        cpfMotorista.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                if (hasFocus) {
                    EstadoUtil.adicionarTeste(cpfMotorista).setEstadoFoco(Estado.Foco.FOCADO).verificar().finalizar();
                } else {
                    String text = cpfMotorista.getText().toString();
                    EstadoUtil.adicionarTeste(cpfMotorista)
                            .setEstadoFoco(Estado.Foco.SEM_FOCO)
                            .setEstadoTexto(text);

                }
            }
        });

        estadoMotorista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (++estadoMotoristaSelected > 1) {
                    final String newValue = (String) estadoMotorista.getItemAtPosition(i);
                    EstadoUtil.adicionarTeste(estadoMotorista).setEstadoSelecao(newValue);

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


               /* EstadoUtil.adicionarTeste(IdUtil.getStringId(volumeCarga), pos);
                EstadoUtil.adicionarTeste(IdUtil.getStringId(volumeCarga), Estado.Foco.FOCADO, pos);*/

            }
        });


        tipoCarga.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                /*EstadoUtil.adicionarTeste(IdUtil.getStringId(tipoCarga), i);
                EstadoUtil.adicionarTeste(registrador, IdUtil.getStringId(tipoCarga), Estado.Foco.FOCADO, i);*/

            }
        });

        motoristaAtivo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                /*EstadoUtil.adicionarTeste(IdUtil.getStringId(motoristaAtivo))
                        .setEstadoFoco(Estado.Foco.FOCADO);*/

            }
        });


        abrirListaMercadorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Altera o foco para o botão, solucionando o problema de não disparar o evento onFocusChange
                abrirListaMercadorias.requestFocusFromTouch();

                EstadoUtil.adicionarTeste(abrirListaMercadorias).setEstadoFoco(Estado.Foco.FOCADO)
                        .reproduzir()
                        .finalizar();

                EstadoUtil.terminarTeste();

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
