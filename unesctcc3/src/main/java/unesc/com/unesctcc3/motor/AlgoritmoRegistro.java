package unesc.com.unesctcc3.motor;

import android.app.Activity;

import java.util.ArrayList;

import unesc.com.unesctcc3.modelos.Atividade;
import unesc.com.unesctcc3.modelos.Preferencias;
import unesc.com.unesctcc3.modelos.Setup;
import unesc.com.unesctcc3.modelos.Teste;
import unesc.com.unesctcc3.utilitarios.EstadoDispositivoUtilitario;


/**
 * Created by fabri on 18/03/2018.
 */
public class AlgoritmoRegistro {
    private Activity activity;
    private Setup setup;
    private Teste teste;
    private AlgoritmoCriacao algoritmoCriacao;
    private EstadoDispositivoUtilitario.EstadoAparelhoMovel estadoAparelhoMovel;
    private ArrayList<Atividade> atividades;
    private Preferencias preferencias;

    public AlgoritmoRegistro(Activity activity, Setup setup) {
        init(activity, setup);
    }

    public AlgoritmoRegistro(Activity activity) {
        this(activity, null);
    }


    private void init(Activity activity, Setup setup) {
        this.activity = activity;
        atividades = new ArrayList<>();
        if (setup != null) {
            this.setup = setup;
            algoritmoCriacao = new AlgoritmoCriacao(setup);
        } else {
            algoritmoCriacao = new AlgoritmoCriacao();
        }
    }

    public void setPreferencias(Preferencias preferencias) {
        this.preferencias = preferencias;
    }

    public EstadoDispositivoUtilitario.EstadoAparelhoMovel getEstadoAparelhoMovel() {
        return estadoAparelhoMovel;
    }

    public void registrar(Atividade atividade) {
        if (atividades.contains(atividade)) {
            atividades.set(atividades.indexOf(atividade), atividade);
        } else {
            atividades.add(atividade);
        }
    }

    public Teste getTeste() {
        return teste;
    }

    public void parar() {
        parar(null);
    }

    public void parar(String nomeTeste) {
        teste = algoritmoCriacao.criar(atividades, preferencias, nomeTeste);
        init(this.activity, this.setup);
    }

    public void registrarEstadoAparelho() {
        estadoAparelhoMovel = EstadoDispositivoUtilitario.getInfo(activity);
    }
}
