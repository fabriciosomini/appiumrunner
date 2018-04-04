package appiumrunner.unesc.net.appiumrunner.helpers;

import android.view.View;

import appiumrunner.unesc.net.appiumrunner.engine.Registrador;
import appiumrunner.unesc.net.appiumrunner.states.Estado;

public class EstadoUtil {
    private static Registrador registrador;

    private static String id;

    public static void init(Registrador registrador) {
        EstadoUtil.registrador = registrador;
    }

    public static Estado adicionarTeste(View elemento) {
        id = IdUtil.getStringId(elemento);
        EstadoAcesso estado = new EstadoAcesso(registrador);
        return estado;
    }

    public static void terminarTeste() {
        registrador.parar();
    }

    private static class EstadoAcesso extends Estado {

        public EstadoAcesso(Registrador registrador) {
            super(registrador);
            this.setIdentificadorElemento(id);
        }
    }
}
