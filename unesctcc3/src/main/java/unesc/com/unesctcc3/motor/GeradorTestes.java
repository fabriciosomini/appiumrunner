package unesc.com.unesctcc3.motor;

import android.view.View;

import unesc.com.unesctcc3.modelos.Atividade;
import unesc.com.unesctcc3.modelos.Teste;
import unesc.com.unesctcc3.utilitarios.ElementIdParserUtilitario;
import unesc.com.unesctcc3.utilitarios.EstadoDispositivoUtilitario;
import unesc.com.unesctcc3.utilitarios.UtilitarioMetodos;

public class GeradorTestes {
    private static AlgoritmoRegistro algoritmoRegistro;
    private static boolean inicializado;

    public static void inicializar(AlgoritmoRegistro algoritmoRegistro) {
        inicializado = true;
        GeradorTestes.algoritmoRegistro = algoritmoRegistro;
        GeradorTestes.algoritmoRegistro.registrarEstadoAparelho();
    }

    public static Atividade gerarTesteElemento(View elemento) {
        checarInicializacao();
        if (elemento == null) {
            throw new RuntimeException("O elemento não pode ser nulo");
        }
        String id = ElementIdParserUtilitario.getStringId(elemento);
        return gerarTesteElemento(id);
    }

    private static void checarInicializacao() {
        if(!inicializado){
            throw new RuntimeException("Esta operação não pode ser realizada, " +
                    "pois não houve uma chamada para o método de inicialização.");
        }
    }

    public static Atividade gerarTesteElemento(String id) {
        checarInicializacao();
        if (id == null) {
            throw new RuntimeException("O identificador do elemento não pode ser nulo");
        }
        Atividade atividade = new Atividade(algoritmoRegistro);
        UtilitarioMetodos.invocarMetodo(atividade, "setIdentificadorElemento", String.class, id);
        return atividade;
    }

    public static void terminarTeste() {
        checarInicializacao();
        algoritmoRegistro.parar();
    }

    public static void terminarTeste(String nomeTeste) {
        checarInicializacao();
        algoritmoRegistro.parar(nomeTeste);
    }

    public static Teste getTeste() {
        checarInicializacao();
        return algoritmoRegistro.getTeste();
    }

    public static EstadoDispositivoUtilitario.EstadoAparelhoMovel getEstadoAparelhoMovel() {
        checarInicializacao();
        return algoritmoRegistro.getEstadoAparelhoMovel();
    }

    public static void pressionar(Atividade.Tecla tecla) {
        checarInicializacao();
        Atividade atividade = new Atividade(algoritmoRegistro);
        atividade.pressionarTeclas(tecla).reproduzirAcoes();
    }
}
