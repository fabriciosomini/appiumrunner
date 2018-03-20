package appiumrunner.unesc.net.appiumrunner.states;

import java.io.Serializable;
import java.util.ArrayList;

import appiumrunner.unesc.net.appiumrunner.engine.Registro;

/**
 * Created by fabri on 18/03/2018.
 */

public class Estado implements Serializable {
    private static ArrayList<Estado> passos;
    private final Registro registro;
    private Verificao verificacao;
    private boolean estadoFoco;
    private String identificadorElemento;
    private String stateMessage;
    private String estadoTexto;
    private String estadoDica;
    private int estadoContagem;

    public Estado(Registro registro) {
        this.registro = registro;
    }

    public Verificao getVerificacao() {
        return verificacao;
    }

    public Boolean getEstadoFoco() {
        return estadoFoco;
    }

    public Estado setEstadoFoco(boolean estadoFoco) {
        this.estadoFoco = estadoFoco;
        return this;
    }

    public String getEstadoTexto() {
        return estadoTexto;
    }

    public Estado setEstadoTexto(String estadoTexto) {
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

    public Estado setEstadoDica(String estadoDica) {
        this.estadoDica = estadoDica;
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

    public boolean build() {

        if (identificadorElemento == null) {
            stateMessage = "É necessário especificar o identificador do elemento";
            return false;
        }

        registro.registrar(this);

        return true;
    }

    public enum Verificao {
        FINAL_ESTADO,
        POR_PROPRIEDA_ESTADO
    }


}
