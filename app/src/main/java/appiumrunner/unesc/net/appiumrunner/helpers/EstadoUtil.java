package appiumrunner.unesc.net.appiumrunner.helpers;

import android.view.View;

import appiumrunner.unesc.net.appiumrunner.engine.Registrador;
import appiumrunner.unesc.net.appiumrunner.states.Estado;

public class EstadoUtil {
    private static Registrador registrador;


    public static void init(Registrador registrador) {
        EstadoUtil.registrador = registrador;
    }

    public static Estado encontrar(View elemento) {
        String id = IdUtil.getStringId(elemento);
        Estado estado = new Estado(registrador);
        estado.setIdentificadorElemento(id);
        return estado;
    }

    public static void terminarTeste() {
        registrador.parar();
    }

}
