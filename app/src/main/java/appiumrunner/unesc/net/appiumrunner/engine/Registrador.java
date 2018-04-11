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

    private Criacao criacao;


    private ArrayList<Estado> estados;



    public Registrador(Setup setup) {
        this.setup = setup;
        criacao = new Criacao(setup);
        estados = new ArrayList<>();

    }

    //TODO: Adicionar suporte ao método findElement
    public void registrar(Estado estado) {

        if (estados.contains(estado)) {
            estados.set(estados.indexOf(estado), estado);
        } else {
            estados.add(estado);
        }

    }

    public String parar() {
        return criacao.criar(estados);
    }




}
