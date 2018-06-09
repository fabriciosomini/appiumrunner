package unesc.com.unesctcc3.motor;

import android.view.View;

import java.util.ArrayList;

import unesc.com.unesctcc3.modelos.Atividade;
import unesc.com.unesctcc3.utilitarios.ElementIdParserUtilitario;
import unesc.com.unesctcc3.utilitarios.MetodosUtilitario;

public class RegistroAtividades {

    private static boolean inicializado;
    private static ArrayList<Atividade> atividades;
    private static RegistroAtividades INSTANCE;

    public static void inicializar() {
        inicializado = true;
        INSTANCE = new RegistroAtividades();
        atividades = new ArrayList<>();

    }

    public static Atividade vincularElemento(View elemento) {
        if (elemento == null) {
            throw new RuntimeException("O elemento não pode ser nulo");
        }
        String id = ElementIdParserUtilitario.getStringId(elemento);
        return vincularElemento(id);
    }

    public static Atividade vincularElemento(String id) {
        if (!inicializado) {
            throw new RuntimeException("Esta operação não pode ser realizada, " +
                    "pois não houve uma chamada para o método de inicialização.");
        }
        if (id == null || id.isEmpty()) {
            throw new RuntimeException("O identificador do elemento é requerido");
        }

        if (!id.matches("^[a-zA-Z_$][a-zA-Z_$0-9]*$")) {
            throw new RuntimeException("O identificador especificado não é valido: '" + id + "'");
        }

        Atividade atividade = new Atividade(INSTANCE);
        MetodosUtilitario.invocarMetodo(atividade, "setIdentificadorElemento", String.class, id);
        return atividade;
    }

    public static void registrarAcessoTela(String tela) {
        Atividade atividade = new Atividade(INSTANCE);
        MetodosUtilitario.invocarMetodo(atividade, "setIdentificadorElemento", String.class, "screen:" + tela);
        adicionarAtividade(atividade);
    }


    public static void pressionar(Atividade.Tecla tecla) {
        if (!inicializado) {
            throw new RuntimeException("Esta operação não pode ser realizada, " +
                    "pois não houve uma chamada para o método de inicialização.");
        }
        Atividade atividade = new Atividade(INSTANCE);
        atividade.pressionarTeclas(tecla).reproduzirAcoes();
    }

    private static void adicionarAtividade(Atividade atividade) {
        if (atividades.contains(atividade)) {
            atividades.set(atividades.indexOf(atividade), atividade);
        } else {
            atividades.add(atividade);
        }
    }

    public static ArrayList<Atividade> getListaAtividades() {
        return atividades;
    }


    private RegistroAtividades getINSTANCE() {
        return INSTANCE;
    }


}
