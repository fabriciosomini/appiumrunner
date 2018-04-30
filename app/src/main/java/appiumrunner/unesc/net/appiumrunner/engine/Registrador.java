package appiumrunner.unesc.net.appiumrunner.engine;

import android.app.Activity;

import java.util.ArrayList;

import appiumrunner.unesc.net.appiumrunner.helpers.EstadoDispositivoUtil;
import appiumrunner.unesc.net.appiumrunner.states.Estado;

/**
 * Created by fabri on 18/03/2018.
 */
//TODO: Verificar se todos os metodos do utilitario de estados estão aqui (ex: Verificar Barra Progresso)
//TODO: toggle - reproduzirAcoes e verificarValores
//TODO: seekbar - reproduzirAcoes e verificarValores
//TODO: radiogroup - reproduzirAcoes e verificarValores
public class Registrador {
    private final Activity activity;
    private Setup setup;
    private String script;
    private Criacao criacao;
    private EstadoDispositivoUtil.EstadoAparelhoMovel estadoAparelhoMovel;
    private ArrayList<Estado> estados;
    private Preferences preferences;

    public Registrador(Activity activity, Setup setup) {
        this.activity = activity;
        estados = new ArrayList<>();
        if (setup != null) {
            this.setup = setup;
            criacao = new Criacao(setup);
        } else {
            criacao = new Criacao();
        }
    }

    public Registrador(Activity activity) {
        this(activity, null);
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public EstadoDispositivoUtil.EstadoAparelhoMovel getEstadoAparelhoMovel() {
        return estadoAparelhoMovel;
    }

    //TODO: Adicionar suporte ao método findElement
    public void registrar(Estado estado) {
        if (estados.contains(estado)) {
            estados.set(estados.indexOf(estado), estado);
        } else {
            estados.add(estado);
        }
    }

    public String getScript() {
        return script;
    }

    public void parar() {
        script = criacao.criar(estados, preferences, null);
    }

    public void parar(String nomeTeste) {
        script = criacao.criar(estados, preferences, nomeTeste);
    }

    public void registrarEstadoAparelho() {
        estadoAparelhoMovel = EstadoDispositivoUtil.getInfo(activity);
    }
}
