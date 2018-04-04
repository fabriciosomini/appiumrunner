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
    private Integer estadoContagem;

    public Integer getEstadoProgresso() {
        return estadoProgresso;
    }

    private int estadoProgresso;


    public Estado(Registrador registrador) {
        super(registrador);
        this.registrador = registrador;
        this.acoes = new ArrayList<>();
        estado = this;
    }

    public Estado selecionar(String estadoSelecao) {
        this.estadoSelecao = estadoSelecao == null ? null : new StringBuilder(estadoSelecao);
        acoes.add(TipoAcao.SELECIONAR);
        return this;
    }

    public Estado focar() {
        this.estadoFoco = Foco.FOCADO;
        acoes.add(TipoAcao.FOCAR);
        return this;
    }

    public Estado limpar() {
        this.estadoTexto = new StringBuilder("");
        acoes.add(TipoAcao.ESCREVER);
        return this;
    }

    public Estado escrever(String estadoTexto) {

        this.estadoTexto = estadoTexto == null ? null : new StringBuilder(estadoTexto);
        acoes.add(TipoAcao.ESCREVER);
        return this;
    }

    public Estado rolar() {
        acoes.add(TipoAcao.ROLAR);
        return this;
    }

    public Estado clicar() {
        this.estadoFoco = Foco.FOCADO;
        acoes.add(TipoAcao.CLICAR);
        return this;
    }

    public Estado desfocar() {
        this.estadoFoco = Foco.SEM_FOCO;
        acoes.add(TipoAcao.FOCAR);
        return this;
    }

    public Estado progredir(int estadoProgresso) {
        this.estadoProgresso = new Integer(estadoProgresso);
        acoes.add(TipoAcao.PROGREDIR);
        return this;
    }

    //-------------------------------------------
    public List<TipoAcao> getAcoes() {
        return acoes;
    }

    public StringBuilder getEstadoSelecao() {
        return estadoSelecao;
    }

    public Foco getEstadoFoco() {
        return estadoFoco;
    }

    public StringBuilder getEstadoTexto() {
        return estadoTexto;
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
        FOCAR,
        ESCREVER,
        VERIFICAR, REPRODUZIR, ROLAR, CLICAR, PROGREDIR, SELECIONAR
    }


}
