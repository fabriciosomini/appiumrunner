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

    private List<Estado.TipoAcao> acoes;
    private Foco estadoFoco;
    private String identificadorElemento;
    private String stateMessage;
    private StringBuilder estadoTexto;
    private StringBuilder estadoSelecao;
    private int estadoContagem;
    private int estadoProgresso;


    public Estado(Registrador registrador) {
        super(registrador);
        this.registrador = registrador;
        this.acoes = new ArrayList<>();
        estado = this;
    }

    public List<TipoAcao> getAcoes() {
        return acoes;
    }

    public StringBuilder getEstadoSelecao() {
        return estadoSelecao;
    }

    public Estado selecionar(String estadoSelecao) {
        this.estadoSelecao = estadoSelecao == null ? null : new StringBuilder(estadoSelecao);
        acoes.add(TipoAcao.SELECT_SPINNER_ITEM);
        return this;
    }


    public Foco getEstadoFoco() {
        return estadoFoco;
    }

    public Estado focar(Foco estadoFoco) {
        this.estadoFoco = estadoFoco;
        acoes.add(TipoAcao.FOCUS);
        return this;
    }

    public StringBuilder getEstadoTexto() {
        return estadoTexto;
    }

    public Estado limpar() {

        acoes.add(TipoAcao.SEND_KEYS);
        return this;
    }

    public Estado escrever(String estadoTexto) {

        this.estadoTexto = estadoTexto == null ? null : new StringBuilder(estadoTexto);
        acoes.add(TipoAcao.SEND_KEYS);
        return this;
    }

    protected String getStateMessage() {
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

    public Estado setEstadoProgresso(int estadoProgresso) {
        this.estadoProgresso = estadoProgresso;

        return this;
    }

    public Estado rolar() {
        acoes.add(TipoAcao.SCROLL_TO);
        return this;
    }

    public Estado clicar() {

        acoes.add(TipoAcao.CLICK);
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
        VERIFICAR, REPRODUZIR, SCROLL_TO, CLICK, SELECT_SPINNER_ITEM
    }


}
