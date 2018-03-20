package appiumrunner.unesc.net.appiumrunner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import appiumrunner.unesc.net.appiumrunner.R;
import appiumrunner.unesc.net.appiumrunner.engine.Registro;
import appiumrunner.unesc.net.appiumrunner.states.ApplicationState;
import appiumrunner.unesc.net.appiumrunner.states.Estado;

import static appiumrunner.unesc.net.appiumrunner.application.AppiumRunnerApplication.TESTSETUP;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTestSetup();

        Button openSearchListBtn = findViewById(R.id.openCustomerServicesBtn);
        TextView userTxtView = findViewById(R.id.userTxtView);
        userTxtView.setText("Fabricio Somini");

        String userName = userTxtView.getText().toString();
        final Registro registro = new Registro();

        // registro.createDB("burger.db");

        openSearchListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), SearchActivity.class);
                startActivity(i);

                registro.stop();

            }
        });

        //----------------------------------------------------------------

        ApplicationState applicationState = new ApplicationState();
        Estado textViewEstado = new Estado(registro);

        applicationState.addInfo("userName", userName);

        textViewEstado.setEstadoTexto((String) applicationState.getInfo("userName"));

        Estado buttonEstado = new Estado(registro);
        buttonEstado.setIdentificadorElemento("openCustomerServicesBtn")
                .setEstadoFoco(false)
                .setEstadoTexto(getResources().getString(R.string.open_search_list))
                .verificar(Estado.Verificao.FINAL_ESTADO)
                .build();


    }

    private void setTestSetup() {
        TESTSETUP.setAppActivity(this.getClass().getName());
        TESTSETUP.setDeviceName("adroid");
        TESTSETUP.setPlatformVersion("7.1.1");
        TESTSETUP.setUseDefaultTearDown(true);
    }
}
