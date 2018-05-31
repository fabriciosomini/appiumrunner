package unesc.com.unesctcc3.motor;

import android.app.Activity;

import java.util.ArrayList;

import unesc.com.unesctcc3.modelos.Atividade;
import unesc.com.unesctcc3.modelos.Preferencias;
import unesc.com.unesctcc3.modelos.Setup;
import unesc.com.unesctcc3.utilitarios.EstadoDispositivoUtilitario;


/**
 * Created by fabri on 18/03/2018.
 */
//TODO: Verificar se todos os metodos do utilitario de atividades estão aqui (ex: Verificar Barra Progresso)
//TODO: toggle - reproduzirAcoes e verificarValores
//TODO: seekbar - reproduzirAcoes e verificarValores
//TODO: radiogroup - reproduzirAcoes e verificarValores
public class AlgoritmoRegistro {
    private final Activity activity;
    private Setup setup;
    private String script;
    private AlgoritmoCriacao algoritmoCriacao;
    private EstadoDispositivoUtilitario.EstadoAparelhoMovel estadoAparelhoMovel;
    private ArrayList<Atividade> atividades;
    private Preferencias preferencias;

    public AlgoritmoRegistro(Activity activity, Setup setup) {
        this.activity = activity;
        atividades = new ArrayList<>();
        if (setup != null) {
            this.setup = setup;
            algoritmoCriacao = new AlgoritmoCriacao(setup);
        } else {
            algoritmoCriacao = new AlgoritmoCriacao();
        }
    }

    public AlgoritmoRegistro(Activity activity) {
        this(activity, null);
    }

    public void setPreferencias(Preferencias preferencias) {
        this.preferencias = preferencias;
    }

    public EstadoDispositivoUtilitario.EstadoAparelhoMovel getEstadoAparelhoMovel() {
        return estadoAparelhoMovel;
    }

    //TODO: Adicionar suporte ao método findElement
    public void registrar(Atividade atividade) {
        if (atividades.contains(atividade)) {
            atividades.set(atividades.indexOf(atividade), atividade);
        } else {
            atividades.add(atividade);
        }
    }

    public String getScript() {
        return script;
    }

    public void parar() {
        script = algoritmoCriacao.criar(atividades, preferencias, null);
    }

    public void parar(String nomeTeste) {
        script = algoritmoCriacao.criar(atividades, preferencias, nomeTeste);
    }

    public void registrarEstadoAparelho() {
        estadoAparelhoMovel = EstadoDispositivoUtilitario.getInfo(activity);
    }
}
