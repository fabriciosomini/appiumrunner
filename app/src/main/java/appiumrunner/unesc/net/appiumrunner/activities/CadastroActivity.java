package appiumrunner.unesc.net.appiumrunner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Random;

import appiumrunner.unesc.net.appiumrunner.R;
import appiumrunner.unesc.net.appiumrunner.models.Motorista;
import appiumrunner.unesc.net.appiumrunner.models.Repository;
import unesc.com.unesctcc3.modelos.Atividade;
import unesc.com.unesctcc3.utilitarios.GeradorTestes;

public class CadastroActivity extends AppCompatActivity {

    private TextView nomeEmpresa;
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
    private Button salvarBtn;
    private Button cancelarBtn;
    private Motorista motoristaSerializable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        setEventosInterface();
        registrarEstadoInicialTela();
    }

    private void registrarEstadoInicialTela() {
        GeradorTestes.gerarTesteElemento(nomeMotorista)
                .desfocarCampo()
                .limparValor()
                .verificarValores();
        GeradorTestes.gerarTesteElemento(cpfMotorista)
                .desfocarCampo()
                .limparValor()
                .verificarValores();
        GeradorTestes.gerarTesteElemento(estadoMotorista)
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
        nomeEmpresa.setText(getString(R.string.company_name));
        salvarBtn = findViewById(R.id.salvarBtn);
        cancelarBtn = findViewById(R.id.cancelarBtn);
        estadoMotorista.setAdapter(getEstadoAdapter());
        Intent intent = getIntent();
        motoristaSerializable = (Motorista) intent.getSerializableExtra("motorista");
        if (motoristaSerializable != null) {
            nomeMotorista.setText(motoristaSerializable.getNome());
            cpfMotorista.setText(motoristaSerializable.getCpf());
            estadoMotorista.setSelection(getEstadoIndex(motoristaSerializable.getEstado()));
            volumeCarga.setProgress(motoristaSerializable.getVolumeCarga());
            tipoCarga.check(getTipoCargaId(motoristaSerializable.getTipoCarga()));
            bitrem.setChecked(motoristaSerializable.isBitrem());
            motoristaAtivo.setChecked(motoristaSerializable.isAtivo());
        }

        nomeMotorista.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                String text = nomeMotorista.getText().toString();
                if (hasFocus && !ignoreFocus) {
                    if (!text.isEmpty()) {
                        GeradorTestes.gerarTesteElemento(nomeMotorista)
                                .limparValor()
                                .reproduzirAcoes();
                    }
                }
                if (!hasFocus && !ignoreFocus) {
                    GeradorTestes.gerarTesteElemento(nomeMotorista)
                            .focarCampo()
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
                        GeradorTestes.gerarTesteElemento(cpfMotorista)
                                .limparValor()
                                .reproduzirAcoes();
                    }
                }
                if (!hasFocus && !ignoreFocus) {
                    GeradorTestes.gerarTesteElemento(cpfMotorista)
                            .focarCampo()
                            .escreverValor(text)
                            .desfocarCampo()
                            .reproduzirAcoes()
                            .verificarValores();
                    String maskedText = getCpfMaskedText(text);
                    if (maskedText == null) {
                        cpfMotorista.setError("CPF Inválido");
                    } else {
                        cpfMotorista.setText(maskedText);
                        GeradorTestes.gerarTesteElemento(cpfMotorista)
                                .lerValor(maskedText)
                                .verificarValores();
                    }
                }
            }
        });
        estadoMotorista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (++estadoMotoristaSelected > 1) {
                    final String newValue = (String) estadoMotorista.getItemAtPosition(i);
                    GeradorTestes.gerarTesteElemento(estadoMotorista)
                            .escolherValor(newValue)
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
                GeradorTestes.gerarTesteElemento(volumeCarga)
                        .deslizarBarraProgresso(pos)
                        .reproduzirAcoes()
                        .verificarValores();
            }
        });
        tipoCarga.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                GeradorTestes.gerarTesteElemento(radioButton)
                        .marcarOpcao()
                        .reproduzirAcoes()
                        .verificarValores();
            }
        });
        motoristaAtivo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Atividade.Marcacao marcacao = b ? Atividade.Marcacao.MARCADO : Atividade.Marcacao.DESMARCADO;
                GeradorTestes.gerarTesteElemento(motoristaAtivo)
                        .marcarOpcaoDesmarcavel(marcacao)
                        .rolarAteCampo()
                        .reproduzirAcoes()
                        .verificarValores();
            }
        });
        bitrem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Atividade.Marcacao marcacao = b ? Atividade.Marcacao.MARCADO : Atividade.Marcacao.DESMARCADO;
                GeradorTestes.gerarTesteElemento(bitrem)
                        .marcarOpcaoDesmarcavel(marcacao)
                        .rolarAteCampo()
                        .reproduzirAcoes()
                        .verificarValores();
            }
        });

        salvarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Motorista motorista = motoristaSerializable == null ? new Motorista() : motoristaSerializable;
                String nomeMotoristaValor = nomeMotorista.getText().toString();
                String cpfMotoristaValor = cpfMotorista.getText().toString();
                String estadoOrigemValor = estadoMotorista.getSelectedItem().toString();
                int volumeCargaValor = volumeCarga.getProgress();
                Motorista.TipoCarga tipoCargaValor = getTipoCarga();
                boolean bitremValor = bitrem.isChecked();
                boolean ativo = motoristaAtivo.isChecked();
                boolean gravar = true;

                if (nomeMotoristaValor.isEmpty()) {
                    nomeMotorista.setError("Nome do Motorista Requerido");
                    gravar = false;
                } else {
                    nomeMotorista.setError(null);
                }

                if (cpfMotoristaValor.isEmpty()) {
                    cpfMotorista.setError("CPF do Motorista Requerido");
                    gravar = false;
                } else {
                    cpfMotorista.setError(null);
                }

                if (motorista.getCodigo() == 0) {
                    motorista.setCodigo(generateCodigo());
                }
                motorista.setNome(nomeMotoristaValor);
                motorista.setCpf(cpfMotoristaValor);
                motorista.setEstado(estadoOrigemValor);
                motorista.setVolumeCarga(volumeCargaValor);
                motorista.setTipoCarga(tipoCargaValor);
                motorista.setBitrem(bitremValor);
                motorista.setAtivo(ativo);

                GeradorTestes.gerarTesteElemento(salvarBtn)
                        .clicarCampo()
                        .reproduzirAcoes();

                if (gravar) {
                    Repository.addOrUptate(motorista);
                    finish();
                }
            }
        });

        cancelarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GeradorTestes.gerarTesteElemento(cancelarBtn)
                        .clicarCampo()
                        .reproduzirAcoes();

                finish();
            }
        });

    }

    private long generateCodigo() {

        Random randomno = new Random();
        long value = randomno.nextLong();
        return value;
    }

    private int getTipoCargaId(Motorista.TipoCarga tipoCarga) {
        if (tipoCarga != null) {
            switch (tipoCarga) {
                case VIVA:
                    return R.id.radioButton_viva;
                case PERIGOSA:
                    return R.id.radioButton_perigosa;
                case ALIMENTICIA:
                    return R.id.radioButton_alimenticia;
            }
        }
        return 0;
    }

    private int getEstadoIndex(String estado) {
        ArrayList<String> estados = getEstados();
        return estados.indexOf(estado);
    }

    private String getCpfMaskedText(String cpf) {
        if (cpf.length() > 11 || cpf.length() < 11) {
            return null;
        }
        cpf = cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
        return cpf;
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
        GeradorTestes.gerarTesteElemento(nomeMotorista)
                .rolarAteCampo()
                .reproduzirAcoes();
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

    public Motorista.TipoCarga getTipoCarga() {
        Motorista.TipoCarga tipoCargaValor = null;
        switch (tipoCarga.getCheckedRadioButtonId()) {
            case R.id.radioButton_alimenticia:
                tipoCargaValor = Motorista.TipoCarga.ALIMENTICIA;
                break;
            case R.id.radioButton_perigosa:
                tipoCargaValor = Motorista.TipoCarga.PERIGOSA;
                break;
            case R.id.radioButton_viva:
                tipoCargaValor = Motorista.TipoCarga.VIVA;
                break;
        }
        return tipoCargaValor;
    }
}
