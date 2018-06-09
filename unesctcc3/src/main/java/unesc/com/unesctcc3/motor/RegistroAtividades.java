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
        String identificador = ElementIdParserUtilitario.getStringId(elemento);
        Atividade atividade = new Atividade(INSTANCE);
        MetodosUtilitario.invocarMetodo(atividade, "setIdentificadorElemento",
                String.class, identificador);
        return atividade;
    }

    public static void registrarAcessoTela(String tela) {
        Atividade atividade = new Atividade(INSTANCE);
        MetodosUtilitario.invocarMetodo(atividade, "setIdentificadorElemento", String.class, "screen:" + tela);
        adicionarAtividade(atividade);
    }

    public static void pressionar(Atividade.Tecla tecla) {
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
