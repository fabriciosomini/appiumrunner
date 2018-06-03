package unesc.com.unesctcc3.motor;

import android.app.Activity;
import android.view.View;

import java.util.ArrayList;

import unesc.com.unesctcc3.modelos.Atividade;
import unesc.com.unesctcc3.modelos.Preferencias;
import unesc.com.unesctcc3.modelos.Setup;
import unesc.com.unesctcc3.modelos.Teste;
import unesc.com.unesctcc3.utilitarios.ElementIdParserUtilitario;
import unesc.com.unesctcc3.utilitarios.EstadoDispositivoUtilitario;
import unesc.com.unesctcc3.utilitarios.MetodosUtilitario;

public class RegistroAtividades {

    private static boolean inicializado;
    private static Activity activity;
    private static Setup setup;
    private static Teste teste;
    private static GeradorCasosTeste geradorCasosTeste;
    private static EstadoDispositivoUtilitario.EstadoAparelhoMovel estadoAparelhoMovel;
    private static ArrayList<Atividade> atividades;
    private static Preferencias preferencias;
    private static RegistroAtividades INSTANCE;


    public static void inicializar(Activity activity) {
        inicializar(activity, null, null);
    }

    public static void inicializar(Activity activity, Setup setup) {
        inicializar(activity, setup, null);
    }

    public static void inicializar(Activity activity, Preferencias preferencias) {
        inicializar(activity, null, preferencias);
    }

    public static void inicializar(Activity activity, Setup setup, Preferencias preferencias) {
        inicializado = true;
        INSTANCE = new RegistroAtividades();
        registrarEstadoAparelho();
        INSTANCE.activity = activity;
        INSTANCE.preferencias = preferencias;
        atividades = new ArrayList<>();
        if (setup != null) {
            INSTANCE.setup = setup;
            geradorCasosTeste = new GeradorCasosTeste(setup);
        } else {
            geradorCasosTeste = new GeradorCasosTeste();
        }
    }

    public static Atividade registrar(View elemento) {
        if (!inicializado) {
            throw new RuntimeException("Esta operação não pode ser realizada, " +
                    "pois não houve uma chamada para o método de inicialização.");
        }
        if (elemento == null) {
            throw new RuntimeException("O elemento não pode ser nulo");
        }
        String id = ElementIdParserUtilitario.getStringId(elemento);
        return registrar(id);
    }

    public static Atividade registrar(String id) {
        if (!inicializado) {
            throw new RuntimeException("Esta operação não pode ser realizada, " +
                    "pois não houve uma chamada para o método de inicialização.");
        }
        if (id == null) {
            throw new RuntimeException("O identificador do elemento não pode ser nulo");
        }
        Atividade atividade = new Atividade(INSTANCE);
        MetodosUtilitario.invocarMetodo(atividade, "setIdentificadorElemento", String.class, id);
        return atividade;
    }

    public static void terminarTeste() {
        if (!inicializado) {
            throw new RuntimeException("Esta operação não pode ser realizada, " +
                    "pois não houve uma chamada para o método de inicialização.");
        }
        terminarTeste(null);
    }

    public static void terminarTeste(String nomeTeste) {
        if (!inicializado) {
            throw new RuntimeException("Esta operação não pode ser realizada, " +
                    "pois não houve uma chamada para o método de inicialização.");
        }
        teste = geradorCasosTeste.criar(atividades, preferencias, nomeTeste);
        inicializar(INSTANCE.activity, INSTANCE.setup, INSTANCE.preferencias);
    }

    public static Teste getTeste() {
        if (!inicializado) {
            throw new RuntimeException("Esta operação não pode ser realizada, " +
                    "pois não houve uma chamada para o método de inicialização.");
        }
        return teste;
    }

    public static EstadoDispositivoUtilitario.EstadoAparelhoMovel getEstadoAparelhoMovel() {
        if (!inicializado) {
            throw new RuntimeException("Esta operação não pode ser realizada, " +
                    "pois não houve uma chamada para o método de inicialização.");
        }
        return estadoAparelhoMovel;
    }

    public static void pressionar(Atividade.Tecla tecla) {
        if (!inicializado) {
            throw new RuntimeException("Esta operação não pode ser realizada, " +
                    "pois não houve uma chamada para o método de inicialização.");
        }
        Atividade atividade = new Atividade(INSTANCE);
        atividade.pressionarTeclas(tecla).reproduzirAcoes();
    }

    private static void registrarEstadoAparelho() {
        estadoAparelhoMovel = EstadoDispositivoUtilitario.getInfo(activity);
    }

    private void adicionarAtividade(Atividade atividade) {
        if (atividades.contains(atividade)) {
            atividades.set(atividades.indexOf(atividade), atividade);
        } else {
            atividades.add(atividade);
        }
    }

}