package appiumrunner.unesc.net.appiumrunner.helpers;

import android.view.View;

import appiumrunner.unesc.net.appiumrunner.engine.Registrador;
import appiumrunner.unesc.net.appiumrunner.states.Estado;

public class GeradorTestes {
    private static Registrador registrador;


    public static void init(Registrador registrador) {
        GeradorTestes.registrador = registrador;
        GeradorTestes.registrador.registrarEstadoAparelho();
    }

    public static Estado iniciarTesteElemento(View elemento) {
        String id = IdUtil.getStringId(elemento);
        Estado estado = new Estado(registrador);
        estado.setIdentificadorElemento(id);
        return estado;
    }

    public static String terminarTeste() {
        return registrador.parar();
    }

}
