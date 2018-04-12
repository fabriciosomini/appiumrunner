package appiumrunner.unesc.net.appiumrunner.engine;


import java.util.ArrayList;

import appiumrunner.unesc.net.appiumrunner.states.Estado;

/**
 * Created by fabri on 18/03/2018.
 */


//TODO: Verificar se todos os metodos do utilitario de estados estão aqui (ex: Verificar Barra Progresso)
//TODO: toggle - reproduzirAcoes e verificarValores
//TODO: seekbar - reproduzirAcoes e verificarValores
//TODO: radiogroup - reproduzirAcoes e verificarValores
public class Registrador {


    private final Setup setup;
    private String script;
    private Criacao criacao;
    private String estadoAparelhoMovel;
    private ArrayList<Estado> estados;


    public Registrador(Setup setup) {
        this.setup = setup;
        criacao = new Criacao(setup);
        estados = new ArrayList<>();

    }

    public String getEstadoAparelhoMovel() {
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
        script = criacao.criar(estados);
    }


    public void registrarEstadoAparelho() {

        estadoAparelhoMovel += "//TODO: Verificar no TCC os valores que serão coletados pelo método";
    }

    public void registrarTeclaDireta(Estado.Tecla voltar) {

    }
}
