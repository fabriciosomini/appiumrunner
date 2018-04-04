package appiumrunner.unesc.net.appiumrunner.states;

import java.util.ArrayList;
import java.util.List;

import appiumrunner.unesc.net.appiumrunner.engine.Registrador;
import appiumrunner.unesc.net.appiumrunner.models.AcaoFinal;

/**
 * Created by fabri on 18/03/2018.
 */

public class Estado extends AcaoFinal {
    private final Registrador registrador;

    protected List<Estado.TipoAcao> acoes;
    private Foco estadoFoco;
    private String identificadorElemento;
    private String stateMessage;
    private StringBuilder estadoTexto;
    private StringBuilder estadoSelecao;
    private int estadoContagem;
    private int estadoProgresso;


    public Estado(Registrador registrador) {
        this.registrador = registrador;
        this.acoes = new ArrayList<>();
    }

    protected List<TipoAcao> getAcoes() {
        return acoes;
    }

    protected StringBuilder getEstadoSelecao() {
        return estadoSelecao;
    }

    public Estado setEstadoSelecao(String estadoSelecao) {
        this.estadoSelecao = new StringBuilder(estadoSelecao);

        return this;
    }


    protected Foco getEstadoFoco() {
        return estadoFoco;
    }

    public Estado setEstadoFoco(Foco estadoFoco) {
        this.estadoFoco = estadoFoco;
        acoes.add(TipoAcao.FOCUS);
        return this;
    }

    protected StringBuilder getEstadoTexto() {
        return estadoTexto;
    }

    public Estado setEstadoTexto(String estadoTexto) {
        this.estadoTexto = new StringBuilder(estadoTexto);
        acoes.add(TipoAcao.SEND_KEYS);
        return this;
    }

    protected String getStateMessage() {
        return stateMessage;
    }

    protected String getIdentificadorElemento() {
        return identificadorElemento;
    }

    protected Estado setIdentificadorElemento(String identificador) {

        this.identificadorElemento = identificador;
        return this;
    }

    public Estado setEstadoContagem(Integer estadoContagem) {
        this.estadoContagem = estadoContagem;
        return this;
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

    public enum TipoAcao {
        FOCUS,
        SEND_KEYS,
        VERIFICAR, REPRODUZIR, SELECT_SPINNER_ITEM
    }


}
