package unesc.com.unesctcc3.motor;

import android.view.View;

import unesc.com.unesctcc3.modelos.Atividade;
import unesc.com.unesctcc3.utilitarios.ElementIdParserUtilitario;
import unesc.com.unesctcc3.utilitarios.EstadoDispositivoUtilitario;
import unesc.com.unesctcc3.utilitarios.UtilitarioMetodos;

public class GeradorTestes {
    private static AlgoritmoRegistro algoritmoRegistro;

    public static void inicializar(AlgoritmoRegistro algoritmoRegistro) {
        GeradorTestes.algoritmoRegistro = algoritmoRegistro;
        GeradorTestes.algoritmoRegistro.registrarEstadoAparelho();
    }

    public static Atividade gerarTesteElemento(View elemento) {
        if (elemento == null) {
            throw new RuntimeException("O elemento não pode ser nulo");
        }
        String id = ElementIdParserUtilitario.getStringId(elemento);
        return gerarTesteElemento(id);
    }

    public static Atividade gerarTesteElemento(String id) {
        if (id == null) {
            throw new RuntimeException("O identificador do elemento não pode ser nulo");
        }
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
