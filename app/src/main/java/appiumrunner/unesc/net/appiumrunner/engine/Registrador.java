package appiumrunner.unesc.net.appiumrunner.engine;


import java.util.ArrayList;

import appiumrunner.unesc.net.appiumrunner.states.Estado;

/**
 * Created by fabri on 18/03/2018.
 */


//TODO: Verificar se todos os metodos do utilitario de estados estão aqui (ex: Verificar Barra Progresso)
//TODO: toggle - reproduzir e verificar
//TODO: seekbar - reproduzir e verificar
//TODO: radiogroup - reproduzir e verificar
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

    public void parar() {
        criacao.criar(estados);
    }




}
