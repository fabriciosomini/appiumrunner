package unesc.com.unesctcc3.motor;

import android.view.View;

import unesc.com.unesctcc3.modelos.Atividade;
import unesc.com.unesctcc3.modelos.Registro;
import unesc.com.unesctcc3.modelos.Teste;
import unesc.com.unesctcc3.utilitarios.ElementIdParserUtilitario;
import unesc.com.unesctcc3.utilitarios.EstadoDispositivoUtilitario;
import unesc.com.unesctcc3.utilitarios.MetodosUtilitario;

public class RegistroAtividades {
    private static Registro registro;
    private static boolean inicializado;

    public static void inicializar(Registro registro) {
        inicializado = true;
        RegistroAtividades.registro = registro;
        RegistroAtividades.registro.registrarEstadoAparelho();
    }

    public static Atividade registrar(View elemento) {
        checarInicializacao();
        if (elemento == null) {
            throw new RuntimeException("O elemento não pode ser nulo");
        }
        String id = ElementIdParserUtilitario.getStringId(elemento);
        return registrar(id);
    }

    private static void checarInicializacao() {
        if(!inicializado){
            throw new RuntimeException("Esta operação não pode ser realizada, " +
                    "pois não houve uma chamada para o método de inicialização.");
        }
    }

    public static Atividade registrar(String id) {
        checarInicializacao();
        if (id == null) {
            throw new RuntimeException("O identificador do elemento não pode ser nulo");
        }
        Atividade atividade = new Atividade(registro);
        MetodosUtilitario.invocarMetodo(atividade, "setIdentificadorElemento", String.class, id);
        return atividade;
    }

    public static void terminarTeste() {
        checarInicializacao();
        registro.parar();
    }

    public static void terminarTeste(String nomeTeste) {
        checarInicializacao();
        registro.parar(nomeTeste);
    }

    public static Teste getTeste() {
        checarInicializacao();
        return registro.getTeste();
    }

    public static EstadoDispositivoUtilitario.EstadoAparelhoMovel getEstadoAparelhoMovel() {
        checarInicializacao();
        return registro.getEstadoAparelhoMovel();
    }

    public static void pressionar(Atividade.Tecla tecla) {
        checarInicializacao();
        Atividade atividade = new Atividade(registro);
        atividade.pressionarTeclas(tecla).reproduzirAcoes();
    }
}
