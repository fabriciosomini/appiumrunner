package unesc.com.unesctcc3.utilitarios;

import android.view.View;

import unesc.com.unesctcc3.modelos.Atividade;
import unesc.com.unesctcc3.motor.AlgoritmoRegistro;

public class GeradorTestes {
    private static AlgoritmoRegistro algoritmoRegistro;

    public static void init(AlgoritmoRegistro algoritmoRegistro) {
        GeradorTestes.algoritmoRegistro = algoritmoRegistro;
        GeradorTestes.algoritmoRegistro.registrarEstadoAparelho();
    }

    public static Atividade gerarTesteElemento(View elemento) {
        String id = IdUtilitario.getStringId(elemento);
        Atividade atividade = new Atividade(algoritmoRegistro);
        UtilitarioMetodos.invocarMetodo(atividade, "setIdentificadorElemento", String.class, id);
        return atividade;
    }

    public static void terminarTeste() {
        algoritmoRegistro.parar();
    }

    public static void terminarTeste(String nomeTeste) {
        algoritmoRegistro.parar(nomeTeste);
    }

    public static String getTeste() {
        return algoritmoRegistro.getScript();
    }

    public static EstadoDispositivoUtilitario.EstadoAparelhoMovel getEstadoAparelhoMovel() {
        return algoritmoRegistro.getEstadoAparelhoMovel();
    }

    public static void pressionar(Atividade.Tecla tecla) {
        Atividade atividade = new Atividade(algoritmoRegistro);
        atividade.pressionarTeclas(tecla).reproduzirAcoes();
    }
}
