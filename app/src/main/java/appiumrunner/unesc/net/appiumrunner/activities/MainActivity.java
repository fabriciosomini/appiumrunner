package appiumrunner.unesc.net.appiumrunner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import appiumrunner.unesc.net.appiumrunner.R;
import appiumrunner.unesc.net.appiumrunner.engine.Registro;
import appiumrunner.unesc.net.appiumrunner.states.Estado;

import static appiumrunner.unesc.net.appiumrunner.application.AppiumRunnerApplication.TESTSETUP;

public class MainActivity extends AppCompatActivity {

    private Registro registro;
    private TextView userTxtView;
    private Button openSearchListBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registro = new Registro();

        setTestSetup();
        setEventosInterface();
        registrarEstadoTextoNomeUsuario(registro);
        registrarEstadoBotaoContasPagar(registro);


    }

    private void setEventosInterface() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        openSearchListBtn = findViewById(R.id.openCustomerServicesBtn);
        userTxtView = findViewById(R.id.userTxtView);

        userTxtView.setText(getString(R.string.company_name));

        openSearchListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), SearchActivity.class);
                startActivity(i);

                registro.stop();

            }
        });

        EditText nomeMotorista = findViewById(R.id.nome_motorista);

    }

    private void setTestSetup() {
        TESTSETUP.setAppActivity(this.getClass().getName());
        TESTSETUP.setDeviceName("adroid");
        TESTSETUP.setPlatformVersion("7.1.1");
        TESTSETUP.setUseDefaultTearDown(true);
    }

    private void registrarEstadoTextoNomeUsuario(Registro registro) {
        String userName = userTxtView.getText().toString();
        Estado textViewEstado = new Estado(registro);
        textViewEstado.setEstadoTexto(userName);

    }

    private void registrarEstadoBotaoContasPagar(Registro registro) {
        Estado buttonEstado = new Estado(registro);
        buttonEstado.setIdentificadorElemento("openCustomerServicesBtn")
                .setEstadoFoco(false)
                .setEstadoTexto(getResources().getString(R.string.open_cargo_list))
                .verificar(Estado.Verificao.FINAL_ESTADO)
                .build();
    }


}
