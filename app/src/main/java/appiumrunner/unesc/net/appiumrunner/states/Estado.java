package appiumrunner.unesc.net.appiumrunner.states;

import java.io.Serializable;
import java.util.ArrayList;

import appiumrunner.unesc.net.appiumrunner.engine.Registrador;

/**
 * Created by fabri on 18/03/2018.
 */

public class Estado implements Serializable {
    private static ArrayList<Estado> passos;
    private final Registrador registrador;
    private Verificao verificacao;
    private Foco estadoFoco;
    private String identificadorElemento;
    private String stateMessage;
    private StringBuilder estadoTexto;
    private StringBuilder estadoSelecao;
    private int estadoContagem;
    private boolean reproduzirPassos;
    private int estadoProgresso;

    public Estado(Registrador registrador) {
        this.registrador = registrador;
    }


    public StringBuilder getEstadoSelecao() {
        return estadoSelecao;
    }

    public Estado setEstadoSelecao(StringBuilder estadoSelecao) {
        this.estadoSelecao = estadoSelecao;

        return this;
    }

    public Verificao getVerificacao() {
        return verificacao;
    }

    public Foco getEstadoFoco() {
        return estadoFoco;
    }

    public Estado setEstadoFoco(Foco estadoFoco) {
        this.estadoFoco = estadoFoco;
        return this;
    }

    public StringBuilder getEstadoTexto() {
        return estadoTexto;
    }

    public Estado setEstadoTexto(StringBuilder estadoTexto) {
        this.estadoTexto = estadoTexto;
        return this;
    }

    public String getStateMessage() {
        return stateMessage;
    }

    public String getIdentificadorElemento() {
        return identificadorElemento;
    }

    public Estado setIdentificadorElemento(String identificador) {

        this.identificadorElemento = identificador;
        return this;
    }

    public Estado setEstadoContagem(Integer estadoContagem) {
        this.estadoContagem = estadoContagem;
        return this;
    }

    public Estado verificar(Verificao verificao) {
        this.verificacao = verificao;
        return this;
    }

    public Estado reproduzirPassos() {

        reproduzirPassos = true;
        return this;
    }

    public Boolean build() {

        if (identificadorElemento == null) {
            stateMessage = "É necessário especificar o identificador do elemento";
            return false;
        }

        registrador.registrar(this);
        return true;

    }

    public boolean getReproduzirPassos() {
        return reproduzirPassos;
    }

    public Estado setEstadoProgresso(int estadoProgresso) {
        this.estadoProgresso = estadoProgresso;

        return this;
    }


    public enum Verificao {
        FINAL_ESTADO,
        POR_PROPRIEDADE
    }

    public enum Foco {
        FOCADO,
        SEM_FOCO,
        IGNORAR
    }


}
